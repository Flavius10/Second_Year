package grup;

import grup.controller.ControllerMain;
import grup.repo.RepoDrone;
import grup.repo.RepoProducator;
import grup.service.ServiceDrone;
import grup.service.ServiceProducator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();

        RepoDrone repoDrone = new RepoDrone();
        ServiceDrone serviceDrone = new ServiceDrone(repoDrone);
        RepoProducator repoProducator = new RepoProducator();
        ServiceProducator serviceProducator = new ServiceProducator(repoProducator);

        ControllerMain controllerMain = loader.getController();
        controllerMain.setService(serviceProducator, serviceDrone);

        Scene scene = new Scene(root);
        stage.setTitle("FPVHangar");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}