package grup.controllers;

import grup.domain.Pacient;
import grup.services.ServicePacient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class WaitingListController {

    @FXML
    private TableView<Pacient> tablePacienti;

    private ServicePacient service;

    // Lista observabila care tine datele tabelului
    private ObservableList<Pacient> model = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Legam tabelul de lista noastra
        tablePacienti.setItems(model);
    }

    // Metoda apelata din Main pentru a injecta Service-ul
    public void setService(ServicePacient service) {
        this.service = service;
        loadData();
    }

    private void loadData() {
        if (service != null) {
            // Service-ul returneaza lista deja sortata descrescator (din SQL)
            // Cerinta 28: "vor fi ordonati descrescator... in functie de gravitate" [cite: 28]
            model.setAll(service.getPacientInAsteptare());
        }
    }

    /**
     * Aceasta metoda este CRITICA.
     * Este folosita de BedController pentru a afla pe cine ai dat click in aceasta fereastra.
     * [cite_start]Cerinta 29: "Cadrul medical poate selecta din lista un copil" [cite: 29]
     */
    public Pacient getSelectedPacient() {
        return tablePacienti.getSelectionModel().getSelectedItem();
    }
}