package org.example.controller;

import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.*;
import org.example.domain.friendship.Request;
import org.example.domain.messaging.Message;
import org.example.services.*;
import org.example.network.NetworkService; // Asigura-te ca e importat corect

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class MainController {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;
    private RequestService requestService;
    private User loggedInUser;

    @FXML private Button requestsButton;
    @FXML private Label requestsLabel;

    @FXML
    private TextArea resultArea;

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

        getRequests();

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
                friendshipService, networkService, messageService, requestService);

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
                friendshipService, networkService, messageService, requestService);
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

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        MessageController messageController = loader.getController();
        messageController.setLoggedInUser(this.loggedInUser);
        messageController.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService, requestService);
        stage.centerOnScreen();
        stage.show();
    }

    public void getRequests(){
        List<Request> requests = (List<Request>)
                this.requestService.findByUsername(this.loggedInUser.getUsername());

        if (requests.isEmpty()){
            this.requestsLabel.setText("Nu ai cereri de prietenie!");
            this.requestsButton.setDisable(true);
        }
        else{
            this.requestsLabel.setText("Cereri de prietenie: " + requests.size());
            this.requestsButton.setDisable(false);
        }


    }


}