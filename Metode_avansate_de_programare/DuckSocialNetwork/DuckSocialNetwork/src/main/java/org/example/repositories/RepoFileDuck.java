package org.example.repositories;

import org.example.domain.Card;
import org.example.domain.Duck;
import org.example.domain.TypeDuck;
import org.example.domain.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class RepoFileDuck implements RepoUser {

    @Override
    public void save(User entity, String file_name) {

        Duck d = (Duck) entity;

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file_name));
            bw.write(toStringFile(d));
            bw.close();
        }catch(IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(User entity, String file_name) {

        List<User> ducks = (List<User>) findAll(file_name);
        Duck updated = (Duck) entity;

        for (int i = 0; i < ducks.size(); i++) {
            if (ducks.get(i).getId().equals(updated.getId())) {
                ducks.set(i, updated);
                break;
            }
        }

        writeAll(ducks, file_name);
    }

    @Override
    public void delete(User entity, String file_name) {
        List<User> ducks = (List<User>) findAll(file_name);
        ducks.removeIf(d -> d.getId().equals(entity.getId()));
        writeAll(ducks, file_name);
    }

    @Override
    public User findById(Long id, String file_name) {
        return StreamSupport.stream(findAll(file_name).spliterator(), false)
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Iterable<User> findAll(String file_name) {

        List<User> ducks = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file_name));
            String line;
            while((line = reader.readLine()) != null)
            {
                Duck d = fromStringFile(line);
                ducks.add(d);
            }
        }catch(IOException e){
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }

        return ducks;
    }

    private Duck fromStringFile(String line){
        String[] parts = line.split(";");

        Long id = Long.parseLong(parts[0]);
        String username = parts[1];
        String email = parts[2];
        String password = parts[3];
        TypeDuck tip = TypeDuck.valueOf(parts[4]);  // Enum-ul tău
        double viteza = Double.parseDouble(parts[5]);
        double rezistenta = Double.parseDouble(parts[6]);

        Card card = null;
        if (parts.length > 7 && !parts[7].isEmpty()) {
            card = new Card();
        }

        return new Duck(id, username, email, password, tip, viteza, rezistenta, card);
    }

    private String toStringFile(Duck d) {
        return d.getId() + ";" +
                d.getUsername() + ";" +
                d.getEmail() + ";" +
                d.getPassword() + ";" +
                d.getTip() + ";" +
                d.getViteza() + ";" +
                d.getRezistenta() + ";" +
                d.getCard();
    }

    private void writeAll(List<User> users, String file_name) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_name, false));
            for (User d : users) {
                writer.write(toStringFile((Duck)d));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public User findByUsername(String username, String file_name){
        return StreamSupport.stream(findAll(file_name).spliterator(), false)
                .filter(d -> d.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }


}
