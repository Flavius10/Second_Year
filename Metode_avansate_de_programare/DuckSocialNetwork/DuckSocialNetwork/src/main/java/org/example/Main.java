package org.example;

import org.example.domain.Message;
import org.example.domain.Persoana;
import org.example.domain.User;
import org.example.repositories.RepoMessage;
import org.example.services.AuthService;
import org.example.services.MessageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        User persoana = new Persoana(1L, "john_doe", "flaviusa1010@gmail.com",
                "123456", "John", "Doe", "Student", null);

        User person_1 = new Persoana(2L, "jane_smith", "email", "password",
                "Jane", "Smith", "Student", null);

        RepoMessage repoMessage = new RepoMessage();
        AuthService authService = new AuthService();

        authService.login(persoana, "123456");
        authService.logout(persoana);
        System.out.println(authService.isLoggedIn(persoana));
    }
}