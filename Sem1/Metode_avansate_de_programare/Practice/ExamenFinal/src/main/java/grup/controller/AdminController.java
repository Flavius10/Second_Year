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

import java.util.List;

public class AdminController implements Observer {

    @FXML
    private TableView<Coins> tableCoins;
    @FXML
    private TableView<Transaction> tableTranzactie;
    @FXML
    private Label bugetUser;

    private ServiceUser serviceUser;
    private ServiceCoins serviceCoins;
    private ServiceTransaction serviceTransaction;
    private User loggedIn;

    private ObservableList<Coins> menuData = FXCollections.observableArrayList();
    private ObservableList<Transaction> menuDataTransaction = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        tableCoins.setItems(menuData);
    }

    public void setService(ServiceUser service, ServiceCoins serviceCoins,
                           ServiceTransaction serviceTransaction, User user){
        this.serviceUser = service;
        this.serviceCoins = serviceCoins;
        this.serviceTransaction = serviceTransaction;
        this.loggedIn = user;

        this.bugetUser.setText(this.loggedIn.getBuget().toString());

        this.menuData.setAll(selectCoins());
    }

    private List<Coins> selectCoins(){
        List<Coins> coins = this.serviceCoins.findAllCoins();

        return coins;
    }

    private List<Transaction> selectTransaction(){
        List<Transaction> transaction = this.serviceTransaction.findAllTransaction();

        return transaction;
    }

    public void update(){
        this.menuDataTransaction.setAll(selectTransaction());
        this.tableTranzactie.setItems(menuDataTransaction);
    };


    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }
}
