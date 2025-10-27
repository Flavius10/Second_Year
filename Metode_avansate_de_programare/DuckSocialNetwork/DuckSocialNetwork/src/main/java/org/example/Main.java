package org.example;

import org.example.repositories.RepoMessage;
import org.example.services.AuthService;
import org.example.services.MessageService;
import org.example.ui.Menu;
import org.example.ui.Ui;

public class Main {
    public static void main(String[] args) {

        AuthService authService = new AuthService();
        RepoMessage repoMessage = new RepoMessage();
        Menu menu = new Menu();
        MessageService messageService = new MessageService(repoMessage, authService);
        Ui ui = new Ui(authService, menu, messageService);

        ui.start();
    }
}