package org.example.repositories.repo_file;

import org.example.domain.TypeDuck;
import org.example.domain.ducks.Card;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.FlyingDuck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RepoFileCard {

    private RepoFileDuck repoFileDuck;

    public RepoFileCard(RepoFileDuck repoFileDuck) {
        this.repoFileDuck = repoFileDuck;
    }

    public void save(Card<Duck> card, String file_path) {
        Iterable<Card<Duck>> all = findAll(file_path);
        boolean exists = StreamSupport.stream(all.spliterator(), false)
                .anyMatch(e -> e.getId().equals(card.getId()));

        if (exists) {
            throw new UserAlreadyExists("User already exists!");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_path, true))) {
            writer.write(toStringFile(card));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }

    }

    public Card<Duck> findById(Long id, String file){
        return StreamSupport.stream(findAll(file).spliterator(), false)
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Card<Duck>> findAll(String fileName){
        List<Card<Duck>> entities = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                entities.add(fromStringFile(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
        return entities;
    }

    protected Card<Duck> fromStringFile(String line) {
        String[] parts = line.split(";", -1);

        Long id = Long.parseLong(parts[0]);
        String numeCard = parts[1];
        List<Duck> membri = new ArrayList<>();

        if (parts.length > 2 && !parts[2].isEmpty()) {
            String[] idMembri = parts[2].split(";");

            for (String idStr : idMembri) {
                try {
                    Long idDuck = Long.parseLong(idStr);

                    Duck membruGasit = repoFileDuck.findById(idDuck, "ducks.txt");

                    if (membruGasit != null) {
                        membri.add(membruGasit);
                    } else {
                        throw new UserNotFound("UserNotFound: ID membru invalid '" + idStr + "'");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("RepoFileCard Eroare: ID membru invalid '" + idStr + "'");
                }
            }

        }

        return new Card<>(id, numeCard, membri);

    }

    protected String toStringFile(Card<Duck> d) {
        String idMembri = d.getMembri().stream()
                .map(duck -> duck.getClass().toString())
                .collect(Collectors.joining(";"));

        return d.getId() + ";" +
                d.getNumeCard() + ";" + idMembri;

    }

    protected String getUsername(Card<Duck> entity) {
        return entity.getNumeCard();
    }

    protected void writeAll(List<Card<Duck>> entities, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            for (Card<Duck> entity : entities) {
                writer.write(toStringFile(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }
    }
}
