package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.LogInController;
import org.example.network.NetworkService;
import org.example.repositories.repo_db.*;
import org.example.services.*;
import org.example.network.DataProvider;
import org.example.network.GraphAnalyzer;
import org.example.network.GraphBuilder;
import org.example.network.GraphService;

import java.io.IOException;

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

            RepoDBMessage messageRepository = new RepoDBMessage("jdbc:postgresql://localhost:5432/duckSocialNetwork",
                    "postgres", "Flavius10");

            MessageService messageService = new MessageService(messageRepository);

            RepoDBRequest requestRepository = new RepoDBRequest("jdbc:postgresql://localhost:5432/duckSocialNetwork",
                    "postgres", "Flavius10");

            RequestService requestService = new RequestService(requestRepository);

            RepoDBEvent eventRepository = new RepoDBEvent("jdbc:postgresql://localhost:5432/duckSocialNetwork",
                    "postgres", "Flavius10");
            EventService eventService = new EventService(eventRepository);

            GraphAnalyzer graphAnalyzer = new GraphAnalyzer();
            GraphBuilder graphBuilder = new GraphBuilder();
            DataProvider dataProvider = new DataProvider(friendshipService, persoanaService, userService);
            GraphService graphService = new GraphService(graphAnalyzer, graphBuilder);
            NetworkService networkService = new NetworkService(dataProvider, graphService);

            openLoginWindow(stage, userService, persoanaService, friendshipService,
                    networkService, messageService, requestService, eventService);

            Stage stage2 = new Stage();
            openLoginWindow(stage2, userService, persoanaService, friendshipService,
                    networkService, messageService, requestService, eventService);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openLoginWindow(Stage stage, DuckService ds, PersoanaService ps,
                                 FriendshipService fs, NetworkService ns, MessageService ms,
                                 RequestService rs, EventService es) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
        Scene scene = new Scene(loader.load(), 400, 500);

        LogInController controller = loader.getController();

        controller.setServices(ds, ps, fs, ns, ms, rs);
        controller.setEventService(es);

        stage.setTitle("Duck Social Network");
        stage.setScene(scene);

        stage.setX(Math.random() * 100 + 50);
        stage.setY(Math.random() * 100 + 50);

        stage.show();
    }

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