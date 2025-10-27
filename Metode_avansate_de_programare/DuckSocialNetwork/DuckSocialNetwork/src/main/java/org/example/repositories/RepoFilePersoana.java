package org.example.repositories;

import org.example.domain.Persoana;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class RepoFilePersoana implements RepoFile<Persoana>{

    @Override
    public void save(Persoana entity, String file_name) {

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file_name));
            bw.write(entity.toString());
            bw.close();
        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Persoana entity, String file_name) {

        List<Persoana> persoane = (List<Persoana>) findAll(file_name);
        boolean updated = false;

        for (int i = 0; i < persoane.size(); i++) {
            if (persoane.get(i).getId().equals(entity.getId())) {
                persoane.set(i, entity);
                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new RuntimeException("Person with ID " + entity.getId() + " not found!");
        }

        writeAll(persoane, file_name);

    }

    @Override
    public void delete(Persoana entity, String file_name) {
        List<Persoana> persoane = (List<Persoana>) findAll(file_name);
        persoane.removeIf(p -> p.getId().equals(entity.getId()));
        writeAll(persoane, file_name);
    }

    @Override
    public Persoana findById(Long id, String file_name) {
        return StreamSupport.stream(findAll(file_name).spliterator(), false)
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Iterable<Persoana> findAll(String file_name) {
        List<Persoana> persoane = new ArrayList<>();
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

    private void writeAll(List<Persoana> persoane, String file_name) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file_name, false));
            for (Persoana p : persoane) {
                writer.write(toStringFile(p));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }
    }


}
