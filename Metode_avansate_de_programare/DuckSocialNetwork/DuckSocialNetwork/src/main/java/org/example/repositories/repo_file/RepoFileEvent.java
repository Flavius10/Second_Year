package org.example.repositories.repo_file;

import org.example.domain.Event;
import org.example.domain.ducks.Card;
import org.example.domain.ducks.Duck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepoFileEvent{

    public void save(Event event){
        try
    }

    public List<Event> findAll(String fileName){
        List<Event> entities = new ArrayList<>();
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


}
