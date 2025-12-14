package org.example.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.domain.Observer;
import org.example.domain.Signal;
import org.example.domain.User;
import org.example.domain.friendship.Friendship;
import org.example.domain.friendship.Request;
import org.example.network.NetworkService;
import org.example.services.*;

import java.io.IOException;
import java.util.Map;

public class RequestController implements Observer {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;
    private RequestService requestService;

    private User loggedInUser;

    @FXML
    private VBox requestsContainer;

    @FXML
    public void initialize() {
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
        if (requestService != null) {
            loadRequests();
        }
    }

    public void setServices(DuckService duckService, PersoanaService persoanaService, FriendshipService friendshipService,
                            NetworkService networkService, MessageService messageService, RequestService requestService) {
        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.messageService = messageService;
        this.requestService = requestService;

        this.requestService.addObserver(this);

        if (loggedInUser != null) {
            loadRequests();
        }
    }

    private void loadRequests() {
        if (requestsContainer == null || requestService == null || loggedInUser == null) {
            return;
        }

        requestsContainer.getChildren().clear();

        Iterable<Request> requests = requestService.findByUsername(loggedInUser.getUsername());

        for (Request request : requests) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/request.fxml"));
                HBox card = loader.load();

                Map<String, Object> namespace = loader.getNamespace();
                Label nameLabel = (Label) namespace.get("nameLabel");
                Label dateLabel = (Label) namespace.get("dateLabel");
                Button acceptBtn = (Button) namespace.get("acceptBtn");
                Button rejectBtn = (Button) namespace.get("rejectBtn");

                if (nameLabel != null) nameLabel.setText("Request from: " + request.getSenderUsername());
                if (dateLabel != null) dateLabel.setText("Status: " + request.getStatus());

                if (acceptBtn != null) acceptBtn.setOnAction(event -> handleAccept(request, card));
                if (rejectBtn != null) rejectBtn.setOnAction(event -> handleReject(request, card));

                requestsContainer.getChildren().add(card);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Signal signal) {
        if (requestsContainer.getScene() == null) {
            return;
        }

        Platform.runLater(this::loadRequests);

        if (signal.getData() instanceof Request) {
            Request req = (Request) signal.getData();
            String currentUsername = loggedInUser.getUsername();

            if ("REQUEST_ADDED".equals(signal.getType())) {
                if (currentUsername.equals(req.getReceiverUsername())) {
                    Platform.runLater(() ->
                            showAlert("Friend Request", "Ai primit o cerere nouă de la " + req.getSenderUsername() + "!")
                    );
                }
            } else if ("REQUEST_APPROVED".equals(signal.getType())) {
                if (currentUsername.equals(req.getSenderUsername())) {
                    Platform.runLater(() ->
                            showAlert("Yey!", "Utilizatorul " + req.getReceiverUsername() + " ți-a acceptat cererea de prietenie!")
                    );
                }
            } else if ("REQUEST_REJECTED".equals(signal.getType())) {
                if (currentUsername.equals(req.getSenderUsername())) {
                    Platform.runLater(() ->
                            showAlert("Nasol...", "Utilizatorul " + req.getReceiverUsername() + " ți-a respins cererea.")
                    );
                }
            }
        }
    }

    private void handleAccept(Request req, HBox card) {
        friendshipService.saveFriendship(new Friendship(null, loggedInUser.getUsername(), req.getSenderUsername()));
        requestService.approveRequest(req);
    }

    private void handleReject(Request req, HBox card) {
        requestService.rejectRequest(req);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void sendBackToMainPage(ActionEvent event) throws IOException {
        if (requestService != null) {
            requestService.removeObserver(this);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

         MainController mainCtrl = loader.getController();
         mainCtrl.setLoggedInUser(this.loggedInUser);
         mainCtrl.setServices(duckService, persoanaService, friendshipService, networkService, messageService, requestService);

        stage.show();
    }
}