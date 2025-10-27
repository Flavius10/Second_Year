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
        Menu menu = new Menu();

        Ui ui = new Ui(authService, menu, duckRepo, persoanaRepo);

        ui.menuBeforeSignUp();
    }
}