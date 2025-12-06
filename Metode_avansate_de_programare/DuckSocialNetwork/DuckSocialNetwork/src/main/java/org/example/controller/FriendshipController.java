package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.domain.Friendship;
import org.example.domain.ducks.Duck;
import org.example.network.NetworkService;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.services.PersoanaService;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FriendshipController {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;

    @FXML private TextArea resultArea;

    @FXML private TableView<Friendship> friendshipTableView;
    @FXML private TableColumn<Friendship, Long> frIdColumn;
    @FXML private TableColumn<Friendship, String> frUser1Column;
    @FXML private TableColumn<Friendship, String> frUser2Column;
    @FXML private Button prevFriendBtn;
    @FXML private Button nextFriendBtn;

    @FXML private TextField friendshipId1;
    @FXML private TextField friendshipId2;
    @FXML private Button addFriendshipBtn;
    @FXML private Button removeFriendshipBtn;

    private int pageSize = 5;
    private int currentPageFriend = 0;

    private ObservableList<Friendship> friendsModel = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        printLog("Aplicatia a pornit. Astept actiuni...");
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

    private void setupAllEventHandlers() {
        prevFriendBtn.setOnAction(e -> { currentPageFriend--; loadFriendshipPage(); });
        nextFriendBtn.setOnAction(e -> { currentPageFriend++; loadFriendshipPage(); });

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
    public void switchToMainTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        MainController controller = loader.getController();
        controller.setServices(duckService, persoanaService, friendshipService, networkService);

        stage.centerOnScreen();
        stage.show();

    }

}
