package grup.controllers;

import grup.domain.Pacient;
import grup.domain.Pat;
import grup.services.ServicePat;
import grup.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.util.Map;

public class ControllerPat implements Observer {

    @FXML private Label lblTotal;
    @FXML private Label lblTic;
    @FXML private Label lblTim;
    @FXML private Label lblTiip;
    @FXML private TableView<Pat> tablePaturi;

    private ServicePat servicePat;

    private WaitingListController waitingController;

    private ObservableList<Pat> modelPaturi = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tablePaturi.setItems(modelPaturi);
    }

    public void setService(ServicePat service, WaitingListController waitingController) {
        this.servicePat = service;
        this.waitingController = waitingController;

        update();
    }

    @Override
    public void update() {
        // A. Actualizam Tabelul
        modelPaturi.setAll(servicePat.getAllPaturi());

        // B. Actualizam Statisticile (Cerinta 31)
        Map<String, Integer> stats = servicePat.getStatisticaPaturiLibere();

        lblTotal.setText("Paturi ocupate: " + stats.get("TOTAL_OCUPATE"));

        lblTic.setText("TIC " + stats.get("TIC") + " paturi libere");
        lblTim.setText("TIM " + stats.get("TIM") + " paturi libere");
        lblTiip.setText("TIIP " + stats.get("TIIP") + " paturi libere");

        // (Optional: Aici poti pune logica de culori Rosu/Verde pentru bonus)
    }

    public void handleInternare(){
        try {
            // A. Luam pacientul selectat din FEREASTRA CEALALTA
            Pacient pacient = waitingController.getSelectedPacient();
            String cnp = (pacient != null) ? pacient.getCnp() : null;

            // B. Luam patul selectat din FEREASTRA ASTA
            Pat pat = tablePaturi.getSelectionModel().getSelectedItem();

            // C. Apelam Service-ul (care face si validarile)
            servicePat.interneazaPacient(cnp, pat);

            showMessage(Alert.AlertType.INFORMATION, "Succes", "Pacientul a fost internat!");

        } catch (RuntimeException e) {
            // D. Prindem erorile de validare (Cerinta 37)
            showMessage(Alert.AlertType.ERROR, "Eroare", e.getMessage());
        }
    }

    private void showMessage(Alert.AlertType type, String header, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(type);
        alert.setTitle("Mesaj");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
