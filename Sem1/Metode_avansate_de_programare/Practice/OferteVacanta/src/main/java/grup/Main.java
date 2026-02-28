package grup;

import grup.controllers.ClientController;
import grup.domain.Client;
import grup.services.ServiceHotel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import grup.repos.*;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String url = "jdbc:postgresql://localhost:5432/ofertedb"; // Verifica numele bazei!
        String user = "postgres";
        String pass = "Flavius10";

        RepoDBLocation repoLocation = new RepoDBLocation(url, user, pass);
        RepoDBHotel repoHotel = new RepoDBHotel(url, user, pass);
        RepoDBSpecialOffer repoOffer = new RepoDBSpecialOffer(url, user, pass);
        RepoDBClient repoClient = new RepoDBClient(url, user, pass);
        RepoDBReservation repoReservation = new RepoDBReservation(url, user, pass);

        ServiceHotel service = new ServiceHotel(repoLocation, repoHotel, repoOffer, repoClient, repoReservation);

        List<String> args = getParameters().getRaw();

        if (args.isEmpty()) {
            System.out.println("Nu ati introdus niciun ID de client in argumente!");
            // Putem deschide o fereastra de test sau inchide aplicatia
            // return;
        }

        for (String arg : args) {
            try {
                Long clientId = Long.parseLong(arg);
                Client client = service.findClient(clientId);

                if (client != null) {
                    createClientWindow(service, client);
                } else {
                    System.out.println("Clientul cu ID " + clientId + " nu exista in baza de date!");
                }
            } catch (NumberFormatException e) {
                System.out.println("ID invalid: " + arg);
            }
        }
    }

    private void createClientWindow(ServiceHotel service, Client client) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/client-view.fxml"));
        Stage stage = new Stage();

        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Vacanta - " + client.getName()); // Titlul ferestrei

        ClientController ctrl = loader.getController();
        ctrl.setService(service, client);

        // FOARTE IMPORTANT: Abonam fereastra la notificari (Observer)
        service.addObserver(ctrl);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}