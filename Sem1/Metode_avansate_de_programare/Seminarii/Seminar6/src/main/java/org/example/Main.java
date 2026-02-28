package org.example;

import org.example.domain.Movie;
import org.example.repository.MovieDBRepository;
import org.example.repository.Repository;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Repository<Long, Movie> repo =
                new MovieDBRepository("jdbc:postgresql://localhost:5432/Cinema", "postgres", "Flavius10");

        repo.findAll().forEach(System.out::println);
    }
}