package org.example;

import org.example.network.*;
import org.example.repositories.repo_file.RepoFileDuck;
import org.example.repositories.repo_file.RepoFilePersoana;
import org.example.repositories.repo_file.RepoFileFriendship;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
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
        RepoFileFriendship friendshipRepo = new RepoFileFriendship();

        PersoanaService persoanaService = new PersoanaService(persoanaRepo);
        DuckService duckService = new DuckService(duckRepo);
        FriendshipService friendshipService = new FriendshipService(friendshipRepo);

        GraphAnalyzer graphAnalyzer = new GraphAnalyzer();
        GraphBuilder graphBuilder = new GraphBuilder();

        DataProvider dataProvider = new DataProvider(friendshipService, persoanaService, duckService);
        GraphService graphService = new GraphService(graphAnalyzer, graphBuilder);

        NetworkService networkService = new NetworkService(dataProvider, graphService);

        Menu menu = new Menu();

        Ui ui = new Ui(menu, persoanaService, duckService, friendshipService, networkService);

        ui.startApp();
    }
}