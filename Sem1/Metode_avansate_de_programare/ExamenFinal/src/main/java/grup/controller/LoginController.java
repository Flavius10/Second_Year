package grup.controller;


import grup.domain.User;
import grup.service.ServiceCoins;
import grup.service.ServiceUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField textUsername;
    @FXML private PasswordField textPassword;

    private ServiceUser service;
    private ServiceCoins serviceCoins;

    public void setService(ServiceUser service, ServiceCoins coins) {
        this.service = service;
    }

    @FXML
    public void handleLogin() {
        String user = textUsername.getText();
        String pass = textPassword.getText();

        Optional<User> result = service.findByUsernamePassword(user, pass);

        if (result.isPresent()) {
            User loggedUser = result.get();
            String tip = loggedUser.getType().toString();

            if ("ADMIN".equals(tip)) {
                openWindow("/fxml/admin_view.fxml", "Market Admin", loggedUser);
            } else {
                openWindow("/fxml/trader_view.fxml", "Trader: " + loggedUser.getUsername(), loggedUser);
            }

        } else {
            showMessage(null, Alert.AlertType.WARNING,
                    "Atentie",
                    "User sau parola gresite");
        }
    }

    private void openWindow(String fxml, String title, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Object ctrl = loader.getController();

            if (ctrl instanceof AdminController) {
                ((AdminController) ctrl).setService(service,  user);
            }
            else{
                ((TraderController) ctrl).setService(service, user);
            }

            Stage stage = new Stage();
            stage.setTitle(title + " - " + user.getUsername());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showMessage(null, Alert.AlertType.ERROR ,"Nu gasesc fisierul FXML: " + fxml, "Eroare");
        }
    }


    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }
}