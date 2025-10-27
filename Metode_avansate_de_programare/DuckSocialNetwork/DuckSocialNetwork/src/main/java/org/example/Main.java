package org.example;

import org.example.repositories.RepoFileDuck;
import org.example.repositories.RepoFilePersoana;
import org.example.repositories.RepoMessage;
import org.example.services.AuthService;
import org.example.services.MessageService;
import org.example.ui.Menu;
import org.example.ui.Ui;

public class Main {
    public static void main(String[] args) {

        RepoFilePersoana persoanaRepo = new RepoFilePersoana();
        RepoFileDuck duckRepo = new RepoFileDuck();

        AuthService authService = new AuthService(persoanaRepo, duckRepo);
        RepoMessage repoMessage = new RepoMessage();
        Menu menu = new Menu();

        MessageService messageService = new MessageService(repoMessage, authService);
        Ui ui = new Ui(authService, menu, messageService);

        ui.menuBeforeSignUp();
    }
}