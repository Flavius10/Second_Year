package org.example.repositories;

import org.example.domain.Friendship;
import org.example.domain.Persoana;
import org.example.domain.User;
import org.example.exceptions.FriendshipAlreadyExists;
import org.example.exceptions.FriendshipNotFound;
import org.example.exceptions.UserNotFound;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class RepoFriendship implements RepoFile<Friendship> {


    @Override
    public void save(Friendship entity, String file_name){

        Iterable<Friendship> all = findAll(file_name);

        boolean exists = StreamSupport.stream(all.spliterator(), false)
                .anyMatch(f -> f.getId().equals(entity.getId()));

        if (exists) {
            throw new FriendshipAlreadyExists("Friendship already exists!");
        }

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_name));
            writer.write(entity.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }

    }

    @Override
    public void update(Friendship entity, String file_name){

        if (this.findById(entity.getId(), file_name) == null) {
            throw new FriendshipNotFound("Friendship not found!");
        }

        List<Friendship> friends = (List<Friendship>) findAll(file_name);
        Friendship updated = (Friendship) entity;

        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).getId().equals(updated.getId())) {
                friends.set(i, updated);
                break;
            }
        }

        writeAll(friends, file_name);

    }

    @Override
    public void delete(Friendship entity, String file_name){

        if (this.findById(entity.getId(), file_name) == null) {
            throw new UserNotFound("User not found!");
        }

        List<Friendship> persoane = (List<Friendship>) findAll(file_name);
        persoane.removeIf(p -> p.getId().equals(entity.getId()));
        writeAll(persoane, file_name);

    }

    @Override
    public Friendship findById(Long id, String file_name){
        return StreamSupport.stream(findAll(file_name).spliterator(), false)
                .filter(f -> f.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Iterable<Friendship> findAll(String file_name){
        List<Friendship> friends = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file_name));
            String line;
            while((line = reader.readLine()) != null)
            {
                Friendship f = fromStringFile(line);
                friends.add(f);
            }
        }catch(IOException e){
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }

        return friends;
    }

    private void writeAll(List<Friendship> friends, String file_name) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_name, false));
            for (Friendship f : friends) {
                writer.write(toStringFile((Friendship)f));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }
    }

    private Friendship fromStringFile(String line){
        String[] parts = line.split(";");
        return new Friendship(
                Long.parseLong(parts[0]),
                parts[1],
                parts[2]
        );
    }

    private String toStringFile(Friendship f) {
        return f.getId() + ";" +
                f.getFirst_friend_username() + ";" +
                f.getSecond_friend_username();
    }
}
