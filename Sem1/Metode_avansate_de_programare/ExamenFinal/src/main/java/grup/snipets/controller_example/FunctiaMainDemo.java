package grup.snipets.controller_example;

//// IMPORTS OBLIGATORII
//import grup.controller.LoginController; // Sau MainController
//import grup.repo.RepoMasina;
//import grup.service.ServiceMasina;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;

public class FunctiaMainDemo { // extends Application

//    @Override
//    public void start(Stage stage) throws Exception {
//
//        // ====================================================
//        // ZONA 1: CONFIGURARE BAZA DE DATE (CREDENTIALE)
//        // ====================================================
//        String url = "jdbc:postgresql://localhost:5432/nume_baza_ta";
//        String user = "postgres";
//        String pass = "parola_ta";
//
//        // ====================================================
//        // ZONA 2: ASAMBLARE ARHITECTURA (REPO -> SERVICE)
//        // ====================================================
//        // Cream obiectele din spate inainte sa porneasca grafica
//        RepoMasina repo = new RepoMasina(url, user, pass);
//        ServiceMasina service = new ServiceMasina(repo);
//
//        // ====================================================
//        // ZONA 3: INCARCARE FXML (LOADER)
//        // ====================================================
//        // Atentie la calea fisierului! (/grup/views/login.fxml)
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/grup/login.fxml"));
//        Parent root = loader.load();
//
//        // ====================================================
//        // ZONA 4: INJECTARE SERVICE IN CONTROLLER (CRITIC!)
//        // ====================================================
//        // Fara pasul asta, controllerul nu are acces la date
//        LoginController controller = loader.getController();
//        controller.setService(service);
//
//        // ====================================================
//        // ZONA 5: AFISARE FEREASTRA (STAGE)
//        // ====================================================
//        Scene scene = new Scene(root);
//        stage.setTitle("Titlu Aplicatie Examen");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
}