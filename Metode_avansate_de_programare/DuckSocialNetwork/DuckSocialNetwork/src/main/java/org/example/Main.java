package org.example;

import org.example.repositories.RepoFileDuck;
import org.example.repositories.RepoFilePersoana;
import org.example.services.DuckService;
import org.example.services.PersoanaService;
import org.example.ui.Menu;
import org.example.ui.Ui;

/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        RepoFilePersoana persoanaRepo = new RepoFilePersoana();
        RepoFileDuck duckRepo = new RepoFileDuck();

        PersoanaService persoanaService = new PersoanaService(persoanaRepo);
        DuckService duckService = new DuckService(duckRepo);
        Menu menu = new Menu();

        Ui ui = new Ui(menu, persoanaService, duckService);

        ui.startApp();
    }
}