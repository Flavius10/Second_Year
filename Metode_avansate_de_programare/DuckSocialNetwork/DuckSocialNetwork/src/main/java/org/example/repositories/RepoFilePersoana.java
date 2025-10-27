package org.example.repositories;

import org.example.domain.Persoana;
import org.example.domain.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class RepoFilePersoana implements RepoUser{

    @Override
    public void save(User entity, String file_name) {

        Persoana p = (Persoana) entity;

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file_name));
            bw.write(toStringFile(p));
            bw.close();
        }catch(IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(User entity, String file_name) {

        List<User> persoane = (List<User>) findAll(file_name);
        Persoana updated = (Persoana) entity;

        for (int i = 0; i < persoane.size(); i++) {
            if (persoane.get(i).getId().equals(updated.getId())) {
                persoane.set(i, updated);
                break;
            }
        }

        writeAll(persoane, file_name);
    }

    @Override
    public void delete(User entity, String file_name) {
        List<User> persoane = (List<User>) findAll(file_name);
        persoane.removeIf(p -> p.getId().equals(entity.getId()));
        writeAll(persoane, file_name);
    }

    @Override
    public User findById(Long id, String file_name) {
        return StreamSupport.stream(findAll(file_name).spliterator(), false)
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Iterable<User> findAll(String file_name) {

        List<User> persoane = new ArrayList<>();

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file_name));
            String line;
            while((line = reader.readLine()) != null)
            {
                Persoana p = fromStringFile(line);
                persoane.add(p);
            }
        }catch(IOException e){
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }

        return persoane;
    }

    private Persoana fromStringFile(String line){
        String[] parts = line.split(";");
        return new Persoana(
                Long.parseLong(parts[0]),
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5],
                parts[6],
                LocalDate.parse(parts[7])
        );
    }

    private String toStringFile(Persoana p) {
        return p.getId() + ";" +
                p.getUsername() + ";" +
                p.getEmail() + ";" +
                p.getPassword() + ";" +
                p.getNume() + ";" +
                p.getPrenume() + ";" +
                p.getOcupatie() + ";" +
                p.getDataNastere();
    }

    private void writeAll(List<User> users, String file_name) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_name, false));
            for (User p : users) {
                writer.write(toStringFile((Persoana)p));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public User findByUsername(String username, String file_name){
        return StreamSupport.stream(findAll(file_name).spliterator(), false)
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }


}
