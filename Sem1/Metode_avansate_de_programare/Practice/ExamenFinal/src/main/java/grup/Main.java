package grup;

import grup.controller.LoginController;
import grup.repo.RepoDBCoins;
import grup.repo.RepoDBTransaction;
import grup.repo.RepoDBUser;
import grup.service.ServiceCoins;
import grup.service.ServiceTransaction;
import grup.service.ServiceUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage ) throws Exception {

        String url = "jdbc:postgresql://localhost:5432/examen_final_db";
        String user = "postgres";
        String pass = "Flavius10";

        RepoDBUser repoUser = new RepoDBUser(url,user, pass);
        RepoDBCoins repoDBCoins = new RepoDBCoins(url,user, pass);
        ServiceUser serviceUser = new ServiceUser(repoUser);
        ServiceCoins serviceCoins = new ServiceCoins(repoDBCoins);
        RepoDBTransaction repoDBTransaction = new RepoDBTransaction(url,user, pass);
        ServiceTransaction serviceTransaction = new ServiceTransaction(repoDBTransaction);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();

        controller.setService(serviceUser, serviceCoins, serviceTransaction);

        Scene scene = new Scene(root);
        stage.setTitle("Aplicatie Examen");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}