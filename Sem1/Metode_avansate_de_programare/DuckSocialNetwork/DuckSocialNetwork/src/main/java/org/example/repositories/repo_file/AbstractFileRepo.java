package org.example.repositories.repo_file;

import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.repositories.RepoFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public abstract class AbstractFileRepo<T> implements RepoFile<T> {

    /**
     * Transformă un obiect într-o linie de fișier.
     */
    protected abstract String toStringFile(T entity);

    /**
     * Creează un obiect dintr-o linie de fișier.
     */
    protected abstract T fromStringFile(String line);

    /**
     * Returnează username-ul (identificator unic) pentru entitate.
     */
    protected abstract String getUsername(T entity);

    @Override
    public void save(T entity, String fileName) {
        Iterable<T> all = findAll(fileName);
        boolean exists = StreamSupport.stream(all.spliterator(), false)
                .anyMatch(e -> getUsername(e).equals(getUsername(entity)));

        if (exists) {
            throw new UserAlreadyExists("User already exists!");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(toStringFile(entity));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }
    }

    @Override
    public void update(T entity, String fileName) {
        if (findByUsername(getUsername(entity), fileName) == null) {
            throw new UserNotFound("User not found!");
        }

        List<T> all = new ArrayList<>();
        findAll(fileName).forEach(all::add);

        for (int i = 0; i < all.size(); i++) {
            if (getUsername(all.get(i)).equals(getUsername(entity))) {
                all.set(i, entity);
                break;
            }
        }
        writeAll(all, fileName);
    }

    @Override
    public void delete(T entity, String fileName) {
        if (findByUsername(getUsername(entity), fileName) == null) {
            throw new UserNotFound("User not found!");
        }

        List<T> all = new ArrayList<>();
        findAll(fileName).forEach(all::add);

        all.removeIf(e -> getUsername(e).equals(getUsername(entity)));
        writeAll(all, fileName);
    }

    @Override
    public T findById(Long id, String fileName) {
        throw new UnsupportedOperationException("findById is not supported. Use findByUsername instead.");
    }

    @Override
    public Iterable<T> findAll(String fileName) {
        List<T> entities = new ArrayList<>();
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

    public T findByUsername(String username, String fileName) {
        return StreamSupport.stream(findAll(fileName).spliterator(), false)
                .filter(e -> getUsername(e).equals(username))
                .findFirst()
                .orElse(null);
    }

    protected void writeAll(List<T> entities, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            for (T entity : entities) {
                writer.write(toStringFile(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing file: " + e.getMessage());
        }
    }
}
