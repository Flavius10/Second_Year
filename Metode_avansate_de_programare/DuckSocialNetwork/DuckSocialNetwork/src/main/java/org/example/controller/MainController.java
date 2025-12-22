package org.example.controller;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.*;
import org.example.domain.friendship.Request;
import org.example.services.*;
import org.example.network.NetworkService;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;

public class MainController implements Observer {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;
    private RequestService requestService;
    private User loggedInUser;

    @FXML private Button requestsButton;
    @FXML private Label requestsLabel;
    @FXML private TextArea resultArea;

    @FXML
    public void initialize() {
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }
    public void setServices(DuckService duckService, PersoanaService persoanaService,
                            FriendshipService friendshipService, NetworkService networkService,
                            MessageService messageService, RequestService requestService) {

        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.messageService = messageService;

        this.requestService = requestService;

        if (this.requestService != null) {
            this.requestService.addObserver(this);
        }

        if (loggedInUser != null) {
            getRequests();
        }
    }

    @Override
    public void update(Signal signal) {
        if (signal.getData() instanceof Request) {
            Platform.runLater(this::getRequests);
        }
    }

    public void getRequests() {
        if (requestService == null || loggedInUser == null) return;

        Iterable<Request> allRequests = this.requestService.findByUsername(this.loggedInUser.getUsername());
        List<Request> requests = StreamSupport.stream(allRequests.spliterator(), false)
                .collect(Collectors.toList());


        this.requestsLabel.setVisible(true);
        this.requestsLabel.toFront();

        if (requests.isEmpty()) {

            this.requestsLabel.setText("Nu ai cereri");
            this.requestsButton.setDisable(true);

            this.requestsLabel.setStyle(
                    "-fx-background-color: #ef4444; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 10px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 4 8; " +
                            "-fx-background-radius: 12; " +
                            "-fx-translate-y: -12;"
            );
        } else {
            this.requestsLabel.setText(String.valueOf(requests.size()));
            this.requestsButton.setDisable(false);


            this.requestsLabel.setStyle(
                    "-fx-background-color: #ef4444; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 11px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 3 7; " +
                            "-fx-background-radius: 15; " +
                            "-fx-translate-x: 5; " +
                            "-fx-translate-y: -10;"
            );
        }
    }

    @FXML
    public void switchToDuckTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/duck-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        DuckController controller = loader.getController();
        controller.setDuckService(duckService, persoanaService,
                friendshipService, networkService, messageService, requestService);

        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void switchToPersonTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/persoana-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        PersoanaController persoanaController = loader.getController();
        persoanaController.setPersoanaService(duckService, persoanaService,
                friendshipService, networkService, messageService, requestService);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void switchToFriendshipTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/friendship-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        FriendshipController friendshipController = loader.getController();
        friendshipController.setLoggedInUser(this.loggedInUser);
        friendshipController.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService, requestService);

        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void switchToMessageTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/message-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        MessageController messageController = loader.getController();
        messageController.setLoggedInUser(this.loggedInUser);
        messageController.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService, requestService);
        stage.centerOnScreen();
        stage.show();
    }

    public void switchToRequestsTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/request-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        RequestController requestController = loader.getController();
        requestController.setLoggedInUser(this.loggedInUser);
        requestController.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService, requestService);
        stage.centerOnScreen();
        stage.show();
    }

    public void switchToProfileTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene =  new Scene(root);
        stage.setScene(scene);



        stage.centerOnScreen();
        stage.show();
    }
}