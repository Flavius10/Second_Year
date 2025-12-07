package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.Persoana;
import org.example.domain.ducks.Duck;
import org.example.network.NetworkService;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.services.MessageService;
import org.example.services.PersoanaService;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class LogInController {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginBtn;

    @FXML
    private Button registerBtn;

    ObservableList<Duck> ducks = FXCollections.observableArrayList();
    ObservableList<Persoana> persoane = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

    }

    public void setServices(DuckService duckService, PersoanaService persoanaService,
                            FriendshipService friendshipService, NetworkService networkService,
                            MessageService messageService){

        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.messageService = messageService;

        loadDucks();
        loadPersoane();

        setupAllEventHandlers();
    }

    public void loadDucks(){
        try{
            Iterable<Duck> rate_preluate = duckService.findAllDucks();
            rate_preluate.forEach(ducks::add);
        } catch (Exception e){
            sendMessage("Eroare la preluarea datelor din baza de date", "Eroare", "Eroare");
        }
    }

    public void loadPersoane(){
        try{
            Iterable<Persoana> persoane_preluate = persoanaService.findAllPersons();
            persoane_preluate.forEach(persoane::add);
        } catch (Exception e){
            sendMessage("Eroare la preluarea datelor din baza de date", "Eroare", "Eroare");
        }
    }

    public boolean connectedPersoana(String username, String password){
        Persoana persoana = (Persoana) this.persoanaService.findByUsernamePerson(username);
        if (persoana != null && persoana.getPassword().equals(password)) return true;
        return false;
    }

    public boolean connectedDuck(String username, String password){
        Duck duck = (Duck) this.duckService.findByUsernameDuck(username);
        if (duck != null && duck.getPassword().equals(password)) return true;
        return false;
    }

    public boolean connected(String username, String password){
        if(connectedPersoana(username, password) || connectedDuck(username, password))
            return true;
        return false;
    }

    private void sendMessage(String text, String title, String header){
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle(title);
        message.setHeaderText(null);
        message.setContentText(text);
        message.showAndWait();
    }

    private void setupAllEventHandlers(){
        loginBtn.setOnAction(e ->{

            String username = usernameField.getText();
            String password = passwordField.getText();

            if (connected(username, password)) {
                try{
                    switchToMainPage(e);
                } catch (IOException ex){
                    sendMessage("Eroare la deschiderea paginii principala", "Eroare", "Eroare");
                }
            } else {
                sendMessage("Username sau parola incorecte", "Eroare", "Eroare");
            }
        });

        registerBtn.setOnAction(
                e -> {
                    try{
                        switchToRegisterPage(e);
                    } catch (IOException ex){
                        sendMessage("Eroare la deschiderea paginii de registrare", "Eroare", "Eroare");
                    }
                }
        );
    }

    private void switchToMainPage(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        MainController controller = loader.getController();
        controller.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService);

        stage.show();

    }

    private void switchToRegisterPage(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/register-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        RegisterController controller = loader.getController();
        controller.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService);

        stage.show();
    }
}
