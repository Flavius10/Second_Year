package grup;

import grup.controller.LoginController;
import grup.repo.RepoDBCar;
import grup.repo.RepoDBUser;
import grup.service.ServiceCar;
import grup.service.ServiceUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {

        String url = "jdbc:postgresql://localhost:5432/masini_db";
        String user = "postgres";
        String pass = "Flavius10";

        RepoDBUser repoUser = new RepoDBUser(url,user, pass);
        RepoDBCar repoCar = new RepoDBCar(url, user, pass);
        ServiceUser serviceUser = new ServiceUser(repoUser);
        ServiceCar serviceCar = new ServiceCar(repoCar);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();

        controller.setService(serviceUser, serviceCar);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Aplicatie Examen");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}