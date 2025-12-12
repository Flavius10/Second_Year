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
import org.example.domain.User;
import org.example.domain.ducks.Duck;
import org.example.network.NetworkService;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.services.MessageService;
import org.example.services.PersoanaService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    private User currentUser;

    ObservableList<Duck> ducks = FXCollections.observableArrayList();
    ObservableList<Persoana> persoane = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
    }

    public void setServices(DuckService duckService, PersoanaService persoanaService,
                            FriendshipService friendshipService, NetworkService networkService,
                            MessageService messageService) {

        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.messageService = messageService;

        loadDucks();
        loadPersoane();

        setupAllEventHandlers();
    }


    private String hashPassword(String plainPassword) {
        if (plainPassword == null) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Această metodă caută userul (Persoana sau Duck) și verifică parola.
     * Returnează obiectul User dacă e corect, sau null dacă nu.
     */
    private User authenticate(String username, String rawPassword) {
        String hashedPassword = hashPassword(rawPassword);

        Persoana p = persoanaService.findByUsernamePerson(username);
        if (p != null) {
            if (hashedPassword != null && hashedPassword.equals(p.getPassword())) {
                return p;
            }
        }

        Duck d = duckService.findByUsernameDuck(username);
        if (d != null) {
            if (hashedPassword != null && hashedPassword.equals(d.getPassword())) {
                return d;
            }
        }

        return null;
    }


    private void setupAllEventHandlers() {

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            User foundUser = authenticate(username, password);

            if (foundUser != null) {
                this.currentUser = foundUser; // Salvăm userul curent!
                try {
                    switchToMainPage(e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    sendMessage("Eroare la deschiderea paginii principale", "Eroare", "Eroare IO");
                }
            } else {
                sendMessage("Username sau parolă incorecte!", "Login Failed", "Eroare");
            }
        });

        registerBtn.setOnAction(e -> {
            try {
                switchToRegisterPage(e);
            } catch (IOException ex) {
                ex.printStackTrace();
                sendMessage("Eroare la deschiderea paginii de înregistrare", "Eroare", "Eroare IO");
            }
        });
    }

    // --- NAVIGARE ---

    private void switchToMainPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        MainController controller = loader.getController();


        controller.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService);

        controller.setLoggedInUser(this.currentUser);

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

    public void loadDucks() {
        try {
            Iterable<Duck> rate_preluate = duckService.findAllDucks();
            rate_preluate.forEach(ducks::add);
        } catch (Exception e) {
            System.out.println("Nu s-au putut încărca rațele (poate DB e gol sau conexiunea eșuată).");
        }
    }

    public void loadPersoane() {
        try {
            Iterable<Persoana> persoane_preluate = persoanaService.findAllPersons();
            persoane_preluate.forEach(persoane::add);
        } catch (Exception e) {
            System.out.println("Nu s-au putut încărca persoanele.");
        }
    }

    private void sendMessage(String text, String title, String header) {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle(title);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }
}