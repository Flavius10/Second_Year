package grup;

import grup.controllers.StaffController;
import grup.controllers.TableController;
import grup.domain.Table;
import grup.repository.RepoDBMenu;
import grup.repository.RepoDBOrder;
import grup.repository.RepoDBTable;
import grup.services.ServiceMenuItem;
import grup.services.ServiceOrder;
import grup.services.ServiceTable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        String url = "jdbc:postgresql://localhost:5432/restaurant_db";
        String user = "postgres";
        String pass = "Flavius10";

        RepoDBMenu repoMenuItem = new RepoDBMenu(url, user, pass);
        RepoDBOrder repoOrder = new RepoDBOrder(url, user, pass);
        RepoDBTable repoTable = new RepoDBTable(url, user, pass);

        ServiceMenuItem serviceMenu = new ServiceMenuItem(repoMenuItem);
        ServiceOrder serviceOrder = new ServiceOrder(repoOrder);
        ServiceTable serviceTable = new ServiceTable(repoTable);

        FXMLLoader loaderStaff = new FXMLLoader(getClass().getResource("/fxml/staff-view.fxml"));
        Stage stageStaff = new Stage();
        stageStaff.setTitle("Staff View");
        stageStaff.setScene(new Scene(loaderStaff.load()));

        StaffController staffCtrl = loaderStaff.getController();
        staffCtrl.setService(serviceOrder);

        serviceOrder.addObserver(staffCtrl);

        stageStaff.show();

        // -----------------------------------------------------------
        // 4. DESCHIDEM FEREASTRA MASA 1 (Client)
        // -----------------------------------------------------------
        for (Table table : serviceTable.getAllTables()) {

            // Cream o noua fereastra (Stage) si un nou Loader pentru fiecare masa
            FXMLLoader loaderTable = new FXMLLoader(getClass().getResource("/fxml/table-view.fxml"));
            Stage stageTable = new Stage();

            // Incarcam UI-ul
            stageTable.setScene(new Scene(loaderTable.load()));

            // Setam titlul dinamic (Masa 1, Masa 2, Masa 3...)
            stageTable.setTitle("Masa " + table.getId());

            // Luam controller-ul specific acestei ferestre
            TableController tableCtrl = loaderTable.getController();

            // Ii dam ID-ul REAL al mesei din baza de date
            tableCtrl.setService(serviceMenu, serviceOrder, table.getId());

            stageTable.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}