package grup;

import grup.controller.LoginController;
import grup.repo.RepoDBUser;
import grup.repo.RepoDbEveniment;
import grup.repo.RepoDbMeci;
import grup.service.ServiceEveniment;
import grup.service.ServiceMeciuri;
import grup.service.ServiceUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage ) throws Exception {

        String url = "jdbc:postgresql://localhost:5432/fotbal_db";
        String user = "postgres";
        String pass = "Flavius10";



        RepoDBUser repoUser = new RepoDBUser(url,user, pass);
        RepoDbMeci repoDbMeci = new RepoDbMeci(url, user, pass);
        ServiceUser serviceUser = new ServiceUser(repoUser);
        ServiceMeciuri serviceMeciuri = new ServiceMeciuri(repoDbMeci);
        RepoDbEveniment repoDbEveniment = new RepoDbEveniment(url,user, pass);
        ServiceEveniment serviceEveniment = new ServiceEveniment(repoDbEveniment);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();

        controller.setService(serviceUser, serviceMeciuri, serviceEveniment);

        Scene scene = new Scene(root);
        stage.setTitle("Aplicatie Examen");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}