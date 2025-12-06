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
    private int currentPageDuck = 0;
    private int currentPagePerson = 0;
    private int currentPageFriend = 0;

    private ObservableList<Duck> ducksModel = FXCollections.observableArrayList();
    private ObservableList<Persoana> personsModel = FXCollections.observableArrayList();
    private ObservableList<Friendship> friendsModel = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        userTypeComboBox.setItems(FXCollections.observableArrayList("Persoana", "Rata"));
        duckTypeCombo.setItems(FXCollections.observableArrayList("SWIMMING", "FLYING", "FLYING_AND_SWIMMING"));

        userTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;
            if (newVal.equals("Persoana")) {
                personFieldsContainer.setVisible(true); personFieldsContainer.setManaged(true);
                duckFieldsContainer.setVisible(false); duckFieldsContainer.setManaged(false);
            } else if (newVal.equals("Rata")) {
                personFieldsContainer.setVisible(false); personFieldsContainer.setManaged(false);
                duckFieldsContainer.setVisible(true); duckFieldsContainer.setManaged(true);
            }
        });

        userTypeComboBox.getSelectionModel().select("Persoana");
        duckTypeCombo.getSelectionModel().select("SWIMMING");

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

        //loadDuckPage();
        loadPersonPage();
        loadFriendshipPage();
    }

    private void initColumns() {
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
//        typeColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));
//        rezistentaColumn.setCellValueFactory(new PropertyValueFactory<>("rezistenta"));
//        vitezaColumn.setCellValueFactory(new PropertyValueFactory<>("viteza"));
//        duckTableView.setItems(ducksModel);

        idColumnPersoana.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumnPersoana.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumnPersoana.setCellValueFactory(new PropertyValueFactory<>("email"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumeColumn.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        ocupatieColumn.setCellValueFactory(new PropertyValueFactory<>("ocupatie"));
        dataNastereColumn.setCellValueFactory(new PropertyValueFactory<>("dataNastere"));
        persoanaTableView.setItems(personsModel);

        frIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        frUser1Column.setCellValueFactory(new PropertyValueFactory<>("first_friend_username"));
        frUser2Column.setCellValueFactory(new PropertyValueFactory<>("second_friend_username"));
        friendshipTableView.setItems(friendsModel);
    }

//    private void loadDuckPage() {
//        Pageable pageable = new Pageable(currentPageDuck, pageSize);
//        Page<Duck> page = duckService.findAllOnPage(pageable);
//        List<Duck> list = new ArrayList<>();
//        if (page != null && page.getElementsOnPage() != null) page.getElementsOnPage().forEach(list::add);
//        ducksModel.setAll(list);
//        prevDuckBtn.setDisable(currentPageDuck == 0);
//        nextDuckBtn.setDisable(list.size() < pageSize);
//    }

    private void loadPersonPage() {
        Pageable pageable = new Pageable(currentPagePerson, pageSize);
        Page<Persoana> page = persoanaService.findAllOnPage(pageable);
        List<Persoana> list = new ArrayList<>();
        if (page != null && page.getElementsOnPage() != null) page.getElementsOnPage().forEach(list::add);
        personsModel.setAll(list);
        prevPersonBtn.setDisable(currentPagePerson == 0);
        nextPersonBtn.setDisable(list.size() < pageSize);
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
        // Navigare
        //prevDuckBtn.setOnAction(e -> { if (currentPageDuck > 0) { currentPageDuck--; loadDuckPage(); }});
        //nextDuckBtn.setOnAction(e -> { currentPageDuck++; loadDuckPage(); });
        prevPersonBtn.setOnAction(e -> { if (currentPagePerson > 0) { currentPagePerson--; loadPersonPage(); }});
        nextPersonBtn.setOnAction(e -> { currentPagePerson++; loadPersonPage(); });
        prevFriendBtn.setOnAction(e -> { if (currentPageFriend > 0) { currentPageFriend--; loadFriendshipPage(); }});
        nextFriendBtn.setOnAction(e -> { currentPageFriend++; loadFriendshipPage(); });

        addUserBtn.setOnAction(e -> {
            try {
                String selectedType = userTypeComboBox.getValue();
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                Long id = System.currentTimeMillis();

                if (selectedType == null || username.isEmpty() || email.isEmpty()) {
                    printLog("Eroare: Completeaza tipul, username si email!"); return;
                }

                if (selectedType.equals("Persoana")) {
                    String nume = numeField.getText();
                    String prenume = prenumeField.getText();
                    String ocupatie = ocupatieField.getText();
                    LocalDate dataNastere = datePicker.getValue();
                    if (dataNastere == null) { printLog("Eroare: Alege data nasterii!"); return; }

                    Persoana p = new Persoana(id, username, email, password, nume, prenume, ocupatie, dataNastere);
                    persoanaService.savePerson(p);
                    loadPersonPage();

                } else if (selectedType.equals("Rata")) {
                    String tipRataStr = duckTypeCombo.getValue();
                    if (tipRataStr == null) { printLog("Eroare: Alege tipul ratei!"); return; }
                    TypeDuck tip = TypeDuck.valueOf(tipRataStr);
                    double viteza = Double.parseDouble(vitezaField.getText());
                    double rezistenta = Double.parseDouble(rezistentaField.getText());

                    Duck duck;
                    if (tip == TypeDuck.FLYING) {
                        FlyingCard card = new FlyingCard(id, "FlyingCard", new ArrayList<>(), TypeCard.FLYING);
                        duck = new FlyingDuck(id, username, email, password, tip, viteza, rezistenta, card);
                    } else {
                        SwimmingCard card = new SwimmingCard(id, "SwimmingCard", new ArrayList<>(), TypeCard.SWIMMING);
                        duck = new SwimmingDuck(id, username, email, password, tip, viteza, rezistenta, card);
                    }
                    duckService.saveDuck(duck);
                    //loadDuckPage();
                }
                usernameField.clear(); emailField.clear(); passwordField.clear();
                numeField.clear(); prenumeField.clear(); vitezaField.clear(); rezistentaField.clear();

                printLog("Succes: Utilizator adaugat!");

            } catch (Exception ex) {
                printLog("Eroare la adaugare user: " + ex.getMessage());
            }
        });

        deleteUserBtn.setOnAction(e -> {
            Duck selectedDuck = duckTableView.getSelectionModel().getSelectedItem();
            if (selectedDuck != null) {
                try {
                    duckService.deleteDuck(selectedDuck);
                    //loadDuckPage();
                    printLog("Succes: Rata stearsa!");
                } catch (Exception ex) {
                    printLog("Eroare la stergere rata: " + ex.getMessage());
                }
                return;
            }
            Persoana selectedPerson = persoanaTableView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                try {
                    persoanaService.deletePerson(selectedPerson);
                    loadPersonPage();
                    printLog("Succes: Persoana stearsa!");
                } catch (Exception ex) {
                    printLog("Eroare la stergere persoana: " + ex.getMessage());
                }
                return;
            }
            printLog("Atentie: Selecteaza un utilizator pentru stergere!");
        });

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
        controller.setDuckService(duckService);

        stage.centerOnScreen();
        stage.show();

    }
}