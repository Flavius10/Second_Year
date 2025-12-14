package org.example.controller;

import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.*;
import org.example.domain.messaging.Message;
import org.example.services.*;
import org.example.network.NetworkService; // Asigura-te ca e importat corect

import javafx.event.ActionEvent;
import java.io.IOException;

public class MainController {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;
    private RequestService requestService;
    private User loggedInUser;

    @FXML
    private TextArea resultArea;

    @FXML
    public void initialize() {
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }

    @FXML
    public void refreshMessages(ActionEvent event) {
        this.resultArea.clear();

        if (loggedInUser == null) {
            this.resultArea.setText("Eroare: Nu e nimeni logat!");
            return;
        }

        Iterable<Message> messages = messageService.findMessagesToUser(loggedInUser.getId());

        messages.forEach(m -> {
            String text = "De la: " + m.getSender().getUsername() +
                    " | Mesaj: " + m.getMessage() +
                    " | Data: " + m.getData() + "\n\n";
            this.resultArea.appendText(text);
        });
    }

    public void setServices(DuckService duckService, PersoanaService persoanaService,
                            FriendshipService friendshipService, NetworkService networkService,
                            MessageService messageService) {
        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.messageService = messageService;

    }

    @FXML
    public void switchToDuckTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/duck-view.fxml"));
        Parent root = loader.load();


        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

//        Stage stage = new Stage();
//        stage.setTitle("DuckTab");
//        stage.setScene(new Scene(root));

        DuckController controller = loader.getController();
        controller.setDuckService(duckService, persoanaService,
                friendshipService, networkService, messageService);

        stage.centerOnScreen();
        stage.show();

    }

    @FXML
    public void switchToPersonTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/persoana-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        PersoanaController persoanaController = loader.getController();
        persoanaController.setPersoanaService(duckService, persoanaService,
                friendshipService, networkService, messageService);
        stage.centerOnScreen();
        stage.show();
    }


    @FXML
    public void switchToFriendshipTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/friendship-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        FriendshipController friendshipController = loader.getController();

        friendshipController.setRequestService(requestService);

        friendshipController.setLoggedInUser(this.loggedInUser);

        friendshipController.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService);

        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void switchToMessageTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/message-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        MessageController messageController = loader.getController();
        messageController.setLoggedInUser(this.loggedInUser);
        messageController.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService);
        stage.centerOnScreen();
        stage.show();
    }


}