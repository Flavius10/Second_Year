package grup;

import grup.controllers.ControllerPat;
import grup.controllers.WaitingListController;
import grup.repositories.RepoDBPacient;
import grup.repositories.RepoDBPat;
import grup.services.ServicePacient;
import grup.services.ServicePat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        String url = "jdbc:postgresql://localhost:5432/spitalati"; // Ai grija la numele bazei!
        String user = "postgres";
        String pass = "Flavius10";

        RepoDBPacient repoPacient = new RepoDBPacient(url, user, pass);
        RepoDBPat repoPat = new RepoDBPat(url, user, pass);

        ServicePacient servicePacient = new ServicePacient(repoPacient);
        ServicePat servicePat = new ServicePat(repoPat);

        FXMLLoader loaderWaiting = new FXMLLoader(getClass().getResource("/fxml/pacienti-view.fxml"));
        Stage stageWaiting = new Stage();
        stageWaiting.setTitle("ATI - Lista Asteptare");
        stageWaiting.setScene(new Scene(loaderWaiting.load()));

        WaitingListController ctrlWaiting = loaderWaiting.getController();
        ctrlWaiting.setService(servicePacient);

        // O afisam prima
        stageWaiting.show();

        // -----------------------------------------------------------
        // 4. FEREASTRA 2: PATURI & INTERNARE (Destinatia)
        // -----------------------------------------------------------
        FXMLLoader loaderBeds = new FXMLLoader(getClass().getResource("/fxml/pat-view.fxml"));
        Stage stageBeds = new Stage();
        stageBeds.setTitle("ATI - Situatie Paturi");
        stageBeds.setScene(new Scene(loaderBeds.load()));

        ControllerPat ctrlBeds = loaderBeds.getController();

        // AICI ESTE CHEIA: Ii dam Service-ul de Paturi SI Controller-ul de Pacienti
        // Ca sa poata lua pacientul selectat din cealalta fereastra[cite: 29].
        ctrlBeds.setService(servicePat, ctrlWaiting);

        // ABONAREA (OBSERVER): Cand se interneaza cineva, BedController afla primul[cite: 30].
        servicePat.addObserver(ctrlBeds);

        // O afisam si pe a doua
        stageBeds.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}