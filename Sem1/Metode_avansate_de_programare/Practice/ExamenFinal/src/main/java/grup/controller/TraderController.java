package grup.controller;

import grup.domain.Coins;
import grup.domain.Transaction;
import grup.domain.User;
import grup.service.ServiceCoins;
import grup.service.ServiceTransaction;
import grup.service.ServiceUser;
import grup.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class TraderController implements Observer {


    @FXML
    private TableView<Coins> tableCoins;
    @FXML
    private Label bugetUser;

    private ServiceUser serviceUser;
    private ServiceCoins serviceCoins;
    private ServiceTransaction serviceTransaction;
    static int cnt = 11;

    private User loggedIn;

    private ObservableList<Coins> menuData = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        tableCoins.setItems(menuData);
    }

    public void setService(ServiceUser service, ServiceCoins serviceCoins,
                           ServiceTransaction transaction, User user){
        this.serviceUser = service;
        this.serviceCoins = serviceCoins;
        this.serviceTransaction = transaction;
        this.loggedIn = user;

        this.bugetUser.setText(this.loggedIn.getBuget().toString());

        this.menuData.setAll(this.serviceCoins.findAllCoins());
    }

    private List<Coins> selectCoins(){
        List<Coins> coins = this.serviceCoins.findAllCoins();

        return coins;
    }

    @FXML
    public void handleBuy(){
        tableCoins.setOnMouseClicked(event -> {
            Coins selectedItem = tableCoins.getSelectionModel().getSelectedItem();
            LocalDateTime dateTime = LocalDateTime.now();
            Integer id = cnt++;

            if (loggedIn.getBuget() < selectedItem.getPret())
            {
                showMessage(null, Alert.AlertType.ERROR, "Nu ai buget sa cumperi", "Lipsa buget");
                return;
            }

            try{
                Transaction transaction = new Transaction(id, loggedIn.getUsername(),
                        selectedItem.getNume(), "BUY" ,selectedItem.getPret(), dateTime);
                this.serviceTransaction.addAsync(transaction);

                showMessage(null, Alert.AlertType.INFORMATION, "Tranzatie adaugata", "Tranzatie adaugata");

            } catch (Exception e) {
                showMessage(null, Alert.AlertType.INFORMATION, "Tranzatie neadaugata", "Tranzatie neadaugata");
            }

        });
    }

    public void handleSell(){
        tableCoins.setOnMouseClicked(event -> {
            Coins selectedMeci = tableCoins.getSelectionModel().getSelectedItem();

        });
    }

    public void update(){

    }

    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }
}
