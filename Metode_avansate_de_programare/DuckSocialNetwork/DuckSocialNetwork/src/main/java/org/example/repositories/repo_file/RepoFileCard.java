package org.example.repositories.repo_file;

import org.example.domain.ducks.FlyingDuck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.domain.ducks.card.Card;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.card.FlyingCard;
import org.example.domain.ducks.card.SwimmingCard;
import org.example.domain.ducks.card.TypeCard;
import org.example.exceptions.CardAlreadyExist;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RepoFileCard {

    private RepoFileDuck repoFileDuck;

    public RepoFileCard(RepoFileDuck repoFileDuck) {
        this.repoFileDuck = repoFileDuck;
    }

    public void save(Card<? extends Duck> card, String file_path) {
        Iterable<Card<? extends Duck>> all = findAll(file_path);
        boolean exists = StreamSupport.stream(all.spliterator(), false)
                .anyMatch(e -> e.getId().equals(card.getId()));

        if (exists) {
            throw new CardAlreadyExist("Card already exists!");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_path, true))) {
            writer.write(toStringFile(card));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }

    }

    public Card<? extends Duck> findById(Long id, String file){
        return StreamSupport.stream(findAll(file).spliterator(), false)
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Card<? extends Duck>> findAll(String fileName){
        List<Card<? extends Duck>> entities = new ArrayList<>();
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

    protected Card<? extends Duck> fromStringFile(String line) {
        String[] parts = line.split(";", -1);

        Long id = Long.parseLong(parts[0]);
        String numeCard = parts[1];

        String type = parts[2];
        TypeCard typeCard = TypeCard.valueOf(type);

        List<Duck> membri = new ArrayList<>();

        if (parts.length > 3) {
            for (int i = 3; i < parts.length; i++) {
                String idStr = parts[i];
                if (idStr.isEmpty()) continue;

                try {
                    Duck membruGasit = repoFileDuck.findByUsername(idStr, "ducks.txt");

                    if (membruGasit != null) {
                        membri.add(membruGasit);
                    } else {
                        throw new UserNotFound("UserNotFound: ID membru invalid '" + idStr + "' not found");
                    }
                } catch (NumberFormatException e) {
                    System.err.println("RepoFileCard Eroare: ID membru invalid '" + idStr + "'");
                }
            }
        }

        if (type.equals("SWIMMING")){
            List<SwimmingDuck> general_list = membri.stream()
                    .filter(duck -> duck instanceof SwimmingDuck)
                    .map(duck -> (SwimmingDuck) duck)
                    .collect(Collectors.toList());

            return new SwimmingCard(id, numeCard, general_list, typeCard);
        } else {
            List<FlyingDuck> general_list = membri.stream()
                    .filter(duck -> duck instanceof FlyingDuck)
                    .map(duck -> (FlyingDuck) duck)
                    .collect(Collectors.toList());

            return new FlyingCard(id, numeCard, general_list, typeCard);
        }
    }

    protected String toStringFile(Card<? extends Duck> d) {
        String idMembri = d.getMembri().stream()
                .map(duck -> duck.getUsername().toString())
                .collect(Collectors.joining(";"));

        return d.getId() + ";" +
                d.getNumeCard() + ";" +
                d.getTypeCard().toString() + ";" +
                idMembri;
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
