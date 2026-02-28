package grup.controller;

import grup.domain.User;
import grup.service.ServiceCar;
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
    private ServiceCar serviceCar;

    public void setService(ServiceUser service, ServiceCar serviceCar) {
        this.service = service;
        this.serviceCar = serviceCar;
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
                openWindow("/fxml/admin_view.fxml", "Admin Panel", loggedUser);
            } else {
                openWindow("/fxml/dealer_view.fxml", "Dealer Panel", loggedUser);
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

            // AICI E CHEIA: Verificam ce controller e si ii dam service-ul
            Object ctrl = loader.getController();

            // Exemplu de verificare (Replace cu numele claselor tale)
            if (ctrl instanceof AdminController) {
                ((AdminController) ctrl).setService(service, serviceCar,  user);
                serviceCar.addObserver((AdminController) ctrl);
            }
            else{
                ((DealerController) ctrl).setService(service, serviceCar, user);
                serviceCar.addObserver((DealerController) ctrl);
            }


            Stage stage = new Stage();
            stage.setTitle(title + " - " + user.getUsername());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showMessage(null, Alert.AlertType.WARNING,
                    "Atentie",
                    "Trebuie sa existe pagina");
        }
    }

    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }

    public static void showErrorMessage(Stage owner, String text){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.initOwner(owner);
        message.setTitle("Eroare");
        message.setContentText(text);
        message.showAndWait();
    }

}
