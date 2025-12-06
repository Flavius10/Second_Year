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

    // --- ELEMENTUL NOU PENTRU AFISARE ---
    @FXML private TextArea resultArea;

    // --- TABELE ---
    @FXML private TableView<Duck> duckTableView;
    @FXML private TableColumn<Duck, Long> idColumn;
    @FXML private TableColumn<Duck, String> usernameColumn;
    @FXML private TableColumn<Duck, String> emailColumn;
    @FXML private TableColumn<Duck, String> typeColumn;
    @FXML private TableColumn<Duck, Double> vitezaColumn;
    @FXML private TableColumn<Duck, Double> rezistentaColumn;
    @FXML private Button prevDuckBtn;
    @FXML private Button nextDuckBtn;
    @FXML private Button duckTabBtn;

    @FXML private TableView<Persoana> persoanaTableView;
    @FXML private TableColumn<Persoana, Long> idColumnPersoana;
    @FXML private TableColumn<Persoana, String> usernameColumnPersoana;
    @FXML private TableColumn<Persoana, String> emailColumnPersoana;
    @FXML private TableColumn<Persoana, String> numeColumn;
    @FXML private TableColumn<Persoana, String> prenumeColumn;
    @FXML private TableColumn<Persoana, String> ocupatieColumn;
    @FXML private TableColumn<Persoana, LocalDate> dataNastereColumn;
    @FXML private Button prevPersonBtn;
    @FXML private Button nextPersonBtn;

    @FXML private TableView<Friendship> friendshipTableView;
    @FXML private TableColumn<Friendship, Long> frIdColumn;
    @FXML private TableColumn<Friendship, String> frUser1Column;
    @FXML private TableColumn<Friendship, String> frUser2Column;
    @FXML private Button prevFriendBtn;
    @FXML private Button nextFriendBtn;

    // --- FORMULAR ---
    @FXML private ComboBox<String> userTypeComboBox;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private VBox personFieldsContainer;
    @FXML private VBox duckFieldsContainer;
    @FXML private TextField numeField;
    @FXML private TextField prenumeField;
    @FXML private DatePicker datePicker;
    @FXML private TextField ocupatieField;
    @FXML private ComboBox<String> duckTypeCombo;
    @FXML private TextField vitezaField;
    @FXML private TextField rezistentaField;
    @FXML private Button addUserBtn;
    @FXML private Button deleteUserBtn;

    @FXML private TextField friendshipId1;
    @FXML private TextField friendshipId2;
    @FXML private Button addFriendshipBtn;
    @FXML private Button removeFriendshipBtn;
    @FXML private Button communityCountBtn;
    @FXML private Button mostSociableBtn;

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

        initColumns();
        setupAllEventHandlers();

        loadFriendshipPage();
    }

    private void initColumns() {

        frIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        frUser1Column.setCellValueFactory(new PropertyValueFactory<>("first_friend_username"));
        frUser2Column.setCellValueFactory(new PropertyValueFactory<>("second_friend_username"));
        friendshipTableView.setItems(friendsModel);
    }

    private void loadFriendshipPage() {
        if (friendshipService == null) return;
        Pageable pageable = new Pageable(currentPageFriend, pageSize);
        Page<Friendship> page = friendshipService.findAllOnPage(pageable);
        List<Friendship> list = new ArrayList<>();
        if (page != null && page.getElementsOnPage() != null) page.getElementsOnPage().forEach(list::add);
        friendsModel.setAll(list);
        prevFriendBtn.setDisable(currentPageFriend == 0);
        nextFriendBtn.setDisable(list.size() < pageSize);
    }

    private void setupAllEventHandlers() {

        addFriendshipBtn.setOnAction(e -> {
            try {
                String user1 = friendshipId1.getText().trim();
                String user2 = friendshipId2.getText().trim();

                if (user1.isEmpty() || user2.isEmpty()) {
                    printLog("Eroare: Completeaza ambele username-uri!");
                    return;
                }
                if (user1.equals(user2)) {
                    printLog("Eroare: Nu poti fi prieten cu tine insuti!");
                    return;
                }
                if (!checkUserExists(user1) || !checkUserExists(user2)) {
                    printLog("Eroare: Unul dintre username-uri nu exista in sistem!");
                    return;
                }

                Long fId = System.currentTimeMillis();
                Friendship f = new Friendship(fId, user1, user2);
                friendshipService.saveFriendship(f);

                loadFriendshipPage();
                printLog("Succes: Prietenie adaugata intre " + user1 + " si " + user2);
                friendshipId1.clear();
                friendshipId2.clear();

            } catch (Exception ex) {
                printLog("Eroare la adaugare prietenie: " + ex.getMessage());
            }
        });

        removeFriendshipBtn.setOnAction(e -> {
            Friendship selected = friendshipTableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                try {
                    friendshipService.deleteFriendship(selected);
                    loadFriendshipPage();
                    printLog("Succes: Prietenia stearsa!");
                } catch (Exception ex) {
                    printLog("Eroare la stergere prietenie: " + ex.getMessage());
                }
            } else {
                printLog("Atentie: Selecteaza o prietenie din tabel!");
            }
        });

        communityCountBtn.setOnAction(e -> {
            if (networkService == null) {
                printLog("Eroare: NetworkService nu este initializat!");
                return;
            }
            try {
                int nr = networkService.connectedCommunities();
                printLog("--------------------------------------------------");
                printLog("Numar Comunitati in retea: " + nr);
                printLog("--------------------------------------------------");
            } catch (Exception ex) {
                printLog("Eroare la calcul comunitati: " + ex.getMessage());
            }
        });

        mostSociableBtn.setOnAction(e -> {
            if (networkService == null) {
                printLog("Eroare: NetworkService nu este initializat!");
                return;
            }
            try {
                List<String> members = networkService.mostSociableCommunity();
                printLog("--------------------------------------------------");
                if (members.isEmpty()) {
                    printLog("Info: Nu exista comunitati sociabile.");
                } else {
                    printLog("Cea mai sociabila comunitate (ID-uri): " + members.toString());
                }
                printLog("--------------------------------------------------");
            } catch (Exception ex) {
                printLog("Eroare la calcul cea mai sociabila: " + ex.getMessage());
            }
        });
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


}