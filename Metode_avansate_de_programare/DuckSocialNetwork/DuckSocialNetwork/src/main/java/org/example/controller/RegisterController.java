package org.example.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.domain.Persoana;
import org.example.domain.TypeDuck;
import org.example.domain.card.FlyingCard;
import org.example.domain.card.SwimmingCard;
import org.example.domain.card.TypeCard;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.FlyingDuck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.network.NetworkService;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.services.MessageService;
import org.example.services.PersoanaService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class RegisterController {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;

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

    @FXML
    public void initialize() {
        userTypeComboBox.setItems(FXCollections.observableArrayList("Persoana", "Rata"));
        duckTypeCombo.setItems(FXCollections.observableArrayList("SWIMMING", "FLYING", "FLYING_AND_SWIMMING"));

        userTypeComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;

            if (newVal.equals("Persoana")) {
                showPersonFields(true);
            } else if (newVal.equals("Rata")) {
                showPersonFields(false);
            }
        });

        userTypeComboBox.getSelectionModel().select("Persoana");
        duckTypeCombo.getSelectionModel().selectFirst();
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

    private void showPersonFields(boolean showPerson) {
        personFieldsContainer.setVisible(showPerson);
        personFieldsContainer.setManaged(showPerson);

        duckFieldsContainer.setVisible(!showPerson);
        duckFieldsContainer.setManaged(!showPerson);
    }

    @FXML
    public void handleRegister(ActionEvent event) {
        try {
            String type = userTypeComboBox.getValue();
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            Long id = System.currentTimeMillis();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showAlert("Eroare", "Toate campurile comune sunt obligatorii!");
                return;
            }

            if ("Persoana".equals(type)) {
                registerPerson(id, username, email, password);
            } else if ("Rata".equals(type)) {
                registerDuck(id, username, email, password);
            }

            showAlert("Succes", "Utilizator creat cu succes!");
            switchToLoginTab(event);

        } catch (Exception e) {
            showAlert("Eroare", "Nu s-a putut crea utilizatorul: " + e.getMessage());
        }
    }

    private void registerPerson(Long id, String user, String email, String pass) {
        String nume = numeField.getText();
        String prenume = prenumeField.getText();
        String ocupatie = ocupatieField.getText();
        LocalDate dob = datePicker.getValue();

        if (dob == null) throw new IllegalArgumentException("Data nasterii este obligatorie!");

        Persoana p = new Persoana(id, user, email, pass, nume, prenume, ocupatie, dob);
        try{
            persoanaService.savePerson(p);
        } catch (RuntimeException e){
            showAlert("Eroare", "Acest utilizator exista deja!");
        }

    }

    private void registerDuck(Long id, String user, String email, String pass) {
        String duckTypeStr = duckTypeCombo.getValue();
        if (duckTypeStr == null) throw new IllegalArgumentException("Alege tipul ratei!");

        TypeDuck tip = TypeDuck.valueOf(duckTypeStr);
        double viteza = Double.parseDouble(vitezaField.getText());
        double rezistenta = Double.parseDouble(rezistentaField.getText());

        Duck duck;
        if (tip == TypeDuck.FLYING) {
            FlyingCard card = new FlyingCard(id, "FlyingCard", new ArrayList<>(), TypeCard.FLYING);
            duck = new FlyingDuck(id, user, email, pass, tip, viteza, rezistenta, card);
        } else {
            SwimmingCard card = new SwimmingCard(id, "SwimmingCard", new ArrayList<>(), TypeCard.SWIMMING);
            duck = new SwimmingDuck(id, user, email, pass, tip, viteza, rezistenta, card);
        }
        try{
            duckService.saveDuck(duck);
        } catch (RuntimeException e){
            showAlert("Eroare", "Aceasta rata exista deja!");
        }

    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        switchToLoginTab(event);
    }

    private void switchToLoginTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

        LogInController logInController = loader.getController();
        logInController.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService);

        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}