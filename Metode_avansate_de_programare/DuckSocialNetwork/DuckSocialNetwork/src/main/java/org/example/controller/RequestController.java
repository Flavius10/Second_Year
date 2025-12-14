package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.domain.User;
import org.example.domain.friendship.Friendship;
import org.example.domain.friendship.Request;
import org.example.network.NetworkService;
import org.example.services.*;

import java.io.IOException;
import java.util.Map;

public class RequestController {

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

                // Folosim getNamespace pentru a evita lookup-ul null
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

    private void handleAccept(Request req, HBox card) {
        try {
            friendshipService.saveFriendship(new Friendship(null, this.loggedInUser.getUsername(), req.getSenderUsername()));
            requestService.deleteRequest(req);

            requestsContainer.getChildren().remove(card);
            showAlert("Success", "Friend request accepted!");
        } catch (Exception e) {
            showAlert("Error", "Could not accept request: " + e.getMessage());
        }
    }

    private void handleReject(Request req, HBox card) {
        try {
            requestService.deleteRequest(req);

            requestsContainer.getChildren().remove(card);
            showAlert("Rejected", "Friend request rejected.");
        } catch (Exception e) {
            showAlert("Error", "Could not reject request: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}