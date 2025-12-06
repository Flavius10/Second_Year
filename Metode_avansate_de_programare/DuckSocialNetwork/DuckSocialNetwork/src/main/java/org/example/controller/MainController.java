package org.example.controller;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.domain.Persoana;
import org.example.domain.Friendship;
import org.example.domain.TypeDuck;
import org.example.domain.card.FlyingCard;
import org.example.domain.card.SwimmingCard;
import org.example.domain.card.TypeCard;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.FlyingDuck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.services.DuckService;
import org.example.services.PersoanaService;
import org.example.services.FriendshipService;
import org.example.network.NetworkService; // Asigura-te ca e importat corect
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import javax.swing.*;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;

    @FXML private Label welcomeLabel;
    @FXML private TextArea resultArea;

    private int pageSize = 5;
    private int currentPageFriend = 0;

    private ObservableList<Friendship> friendsModel = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        printLog("Aplicatia a pornit. Astept actiuni...");
    }

    public void setServices(DuckService duckService, PersoanaService persoanaService,
                            FriendshipService friendshipService, NetworkService networkService) {
        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;

        setupAllEventHandlers();
    }



    private void setupAllEventHandlers() {

//        communityCountBtn.setOnAction(e -> {
//            if (networkService == null) {
//                printLog("Eroare: NetworkService nu este initializat!");
//                return;
//            }
//            try {
//                int nr = networkService.connectedCommunities();
//                printLog("--------------------------------------------------");
//                printLog("Numar Comunitati in retea: " + nr);
//                printLog("--------------------------------------------------");
//            } catch (Exception ex) {
//                printLog("Eroare la calcul comunitati: " + ex.getMessage());
//            }
//        });
//
//        mostSociableBtn.setOnAction(e -> {
//            if (networkService == null) {
//                printLog("Eroare: NetworkService nu este initializat!");
//                return;
//            }
//            try {
//                List<String> members = networkService.mostSociableCommunity();
//                printLog("--------------------------------------------------");
//                if (members.isEmpty()) {
//                    printLog("Info: Nu exista comunitati sociabile.");
//                } else {
//                    printLog("Cea mai sociabila comunitate (ID-uri): " + members.toString());
//                }
//                printLog("--------------------------------------------------");
//            } catch (Exception ex) {
//                printLog("Eroare la calcul cea mai sociabila: " + ex.getMessage());
//            }
//        });
    }

    private void printLog(String message) {
        if (resultArea != null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            resultArea.appendText("[" + timestamp + "] " + message + "\n");
        } else {
            System.out.println("ResultArea nu este legat la FXML!");
        }
    }

    private boolean checkUserExists(String username) {
        if (duckService.findByUsernameDuck(username) != null) return true;
        if (persoanaService.findByUsernamePerson(username) != null) return true;
        return false;
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
                friendshipService, networkService);

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
                friendshipService, networkService);
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
        friendshipController.setServices(duckService, persoanaService,
                friendshipService, networkService);
        stage.centerOnScreen();
        stage.show();
    }


}