package grup.snipets.controller_example;
//
//import grup.domain.Utilizator;
//import grup.service.ServiceUtilizator;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import grup.utils.MessageAlert; // Folosim clasa de mai sus
//
//import java.io.IOException;
//import java.util.Optional;
//
public class LoginController {
//
//    @FXML private TextField textUsername;
//    @FXML private PasswordField textPassword;
//
//    private ServiceUtilizator service;
//
//    public void setService(ServiceUtilizator service) {
//        this.service = service;
//    }
//
//    @FXML
//    public void handleLogin() {
//        String user = textUsername.getText();
//        String pass = textPassword.getText();
//
//        Optional<Utilizator> result = service.getUser(user, pass);
//
//        if (result.isPresent()) {
//            Utilizator loggedUser = result.get();
//            String tip = loggedUser.getTip(); // Ex: "ADMIN" sau "CLIENT"
//
//            if ("ADMIN".equals(tip)) {
//                openWindow("/grup/admin_view.fxml", "Admin Panel", loggedUser);
//            } else {
//                openWindow("/grup/client_view.fxml", "Client Panel", loggedUser);
//            }
//            // Inchide login
//            ((Stage) textUsername.getScene().getWindow()).close();
//
//        } else {
//            MessageAlert.showErrorMessage(null, "User sau parola gresita!");
//        }
//    }
//
//    private void openWindow(String fxml, String title, Utilizator user) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//            Parent root = loader.load();
//
//            // AICI E CHEIA: Verificam ce controller e si ii dam service-ul
//            Object ctrl = loader.getController();
//
//            // Exemplu de verificare (Replace cu numele claselor tale)
//            if (ctrl instanceof MainController) {
//                ((MainController) ctrl).setService(service, user);
//            }
//            // else if (ctrl instanceof AltController) ...
//
//            Stage stage = new Stage();
//            stage.setTitle(title + " - " + user.getUsername());
//            stage.setScene(new Scene(root));
//            stage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            MessageAlert.showErrorMessage(null, "Nu gasesc fisierul FXML: " + fxml);
//        }
//    }
}
