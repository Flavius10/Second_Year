package grup.controller;


import grup.domain.User;
import grup.service.ServiceEveniment;
import grup.service.ServiceMeciuri;
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
    private ServiceMeciuri serviceMeciuri;
    private ServiceEveniment serviceEveniment;

    public void setService(ServiceUser service, ServiceMeciuri serviceMeciuri,
                           ServiceEveniment serviceEveniment) {
        this.serviceMeciuri = serviceMeciuri;
        this.service = service;
        this.serviceEveniment = serviceEveniment;
    }

    @FXML
    public void handleLogin() {
        String user = textUsername.getText();
        String pass = textPassword.getText();

        Optional<User> result = service.findByUsernamePassword(user, pass);

        if (result.isPresent()) {
            User loggedUser = result.get();
            String tip = loggedUser.getType().toString();

            openWindow("/fxml/main_page.fxml", "Main Page", loggedUser);

        } else {
            showMessage(null, Alert.AlertType.INFORMATION, "User sau parola gresita!", "Eroare");
        }
    }

    private void openWindow(String fxml, String title, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Object ctrl = loader.getController();

            ((MainController) ctrl).setService(serviceMeciuri, serviceEveniment, user);

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