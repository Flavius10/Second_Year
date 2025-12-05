package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.domain.ducks.Duck;
import org.example.network.*;
import org.example.repositories.repo_db.RepoDBCard;
import org.example.repositories.repo_db.RepoDBDuck;
import org.example.repositories.repo_db.RepoDBFriendship;
import org.example.repositories.repo_db.RepoDBPersoana;
import org.example.repositories.repo_file.RepoFileCard;
import org.example.repositories.repo_file.RepoFileDuck;
import org.example.repositories.repo_file.RepoFilePersoana;
import org.example.repositories.repo_file.RepoFileFriendship;
import org.example.services.CardService;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.services.PersoanaService;
import org.example.ui.Menu;
import org.example.ui.Ui;
import org.example.utils.Constants;

/**
 * The type Main.
 */
public class Main extends Application {


    @Override
    public void start(Stage stage) {

        try {

            RepoDBDuck duckRepository = new RepoDBDuck("jdbc:postgresql://localhost:5432/duckSocialNetwork",
                    "postgres", "Flavius10");
            DuckService userService = new DuckService(duckRepository);

            RepoDBPersoana persoanaRepository = new RepoDBPersoana("jdbc:postgresql://localhost:5432/duckSocialNetwork",
                    "postgres", "Flavius10");
            PersoanaService persoanaService = new PersoanaService(persoanaRepository);

            RepoDBFriendship friendshipRepository = new RepoDBFriendship("jdbc:postgresql://localhost:5432/duckSocialNetwork",
                    "postgres", "Flavius10");
            FriendshipService friendshipService = new FriendshipService(friendshipRepository);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
            Scene scene = new Scene(loader.load(), 1200, 800);
            MainController controller = loader.getController();

            GraphAnalyzer graphAnalyzer = new GraphAnalyzer();
            GraphBuilder graphBuilder = new GraphBuilder();

            DataProvider dataProvider = new DataProvider(friendshipService, persoanaService, userService);
            GraphService graphService = new GraphService(graphAnalyzer, graphBuilder);

            NetworkService networkService = new NetworkService(dataProvider, graphService);

            controller.setServices(userService, persoanaService, friendshipService, networkService);

            stage.setTitle("Duck Social Network");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        launch(args);

//        // Partea de fisiere
//        RepoFilePersoana persoanaRepo = new RepoFilePersoana();
//        RepoFileDuck duckRepo = new RepoFileDuck();
//        RepoFileFriendship friendshipRepo = new RepoFileFriendship();
//        RepoFileCard repoFileCard = new RepoFileCard(duckRepo);
//
//        //partea de baze de date
//        RepoDBDuck repoDBDuck = new RepoDBDuck(Constants.PATH_DB, Constants.USERNAME, Constants.PASSWORD);
//        RepoDBPersoana persoanaRepoDB = new RepoDBPersoana(Constants.PATH_DB, Constants.USERNAME, Constants.PASSWORD);
//        RepoDBFriendship friendshipRepoDB = new RepoDBFriendship(Constants.PATH_DB, Constants.USERNAME, Constants.PASSWORD);
//        RepoDBCard<Duck> repoDBCard = new RepoDBCard<>(Constants.PATH_DB, Constants.USERNAME, Constants.PASSWORD);
//
//        PersoanaService persoanaService = new PersoanaService(persoanaRepoDB);
//        DuckService duckService = new DuckService(repoDBDuck);
//        FriendshipService friendshipService = new FriendshipService(friendshipRepoDB);
//        CardService cardService = new CardService(repoDBCard);
//
//        GraphAnalyzer graphAnalyzer = new GraphAnalyzer();
//        GraphBuilder graphBuilder = new GraphBuilder();
//
//        DataProvider dataProvider = new DataProvider(friendshipService, persoanaService, duckService);
//        GraphService graphService = new GraphService(graphAnalyzer, graphBuilder);
//
//        NetworkService networkService = new NetworkService(dataProvider, graphService);
//
//        Menu menu = new Menu();
//
//        Ui ui = new Ui(menu, persoanaService, duckService, friendshipService, networkService, cardService);
//
//        ui.startApp();
    }
}