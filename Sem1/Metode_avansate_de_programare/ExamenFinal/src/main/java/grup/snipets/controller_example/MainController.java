package grup.snipets.controller_example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.format.DateTimeFormatter;

public class MainController {

    // ==========================================================
    // ZONA 1: DECLARARE ELEMENTE UI (FXML)
    // ==========================================================
//    @FXML private TableView<Entitate> tableView;
//    @FXML private TableColumn<Entitate, String> colString;
//    @FXML private TableColumn<Entitate, Double> colNumber;
//    @FXML private TableColumn<Entitate, String> colDate; // Atentie la tip
//    @FXML private TableColumn<Entitate, String> colEnum;
//
//    @FXML private TextField textSearch;
//    @FXML private TextArea textDetalii;

    // ==========================================================
    // ZONA 2: DATE SI INITIALIZARE SERVICE
    // ==========================================================
//    private ServiceEntitate service;
//    private ObservableList<Entitate> model = FXCollections.observableArrayList();
//
//    public void setService(ServiceEntitate service) {
//        this.service = service;
//        loadData(); // Incarca datele cand primim service-ul
//    }

    // ==========================================================
    // ZONA 3: INITIALIZARE TABEL (FACTORY & LISTENERS)
    // ==========================================================
//    @FXML
//    public void initialize() {
//        // A. CONFIGURARE COLOANE
//        // String-ul din paranteza trebuie sa fie EXACT numele campului din clasa (getNume -> "nume")
//        colString.setCellValueFactory(new PropertyValueFactory<>("nume"));
//        colNumber.setCellValueFactory(new PropertyValueFactory<>("pret"));
//        colEnum.setCellValueFactory(new PropertyValueFactory<>("status"));
//
//        // B. CONFIGURARE DATA (HACK-UL PENTRU AFISARE FRUMOASA)
//        colDate.setCellValueFactory(cellData -> {
//            var obj = cellData.getValue();
//            if (obj.getData() != null) {
//                return new javafx.beans.property.SimpleStringProperty(
//                        obj.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
//                );
//            }
//            return null;
//        });
//
//        // C. SELECTIE RAND (MASTER-DETAIL)
//        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal != null) {
//                textDetalii.setText("Detalii: " + newVal.toString());
//            } else {
//                textDetalii.clear();
//            }
//        });
//
//        // D. LIVE SEARCH (FILTRARE AUTOMATA)
//        textSearch.textProperty().addListener((obs, oldVal, newVal) -> handleFilter());
//    }

    // ==========================================================
    // ZONA 4: LOGICA DE FILTRARE SI CULOARE
    // ==========================================================
//    private void handleFilter() {
//        String cautare = textSearch.getText().toLowerCase();
//
//        // Folosim stream() pentru filtrare rapida
//        var listaFiltrata = service.getAll().stream()
//                .filter(item -> {
//                    if (item.getNume().toLowerCase().contains(cautare)) return true;
//                    if (item.getStatus().toString().toLowerCase().contains(cautare)) return true;
//                    return false;
//                })
//                .toList();
//
//        tableView.setItems(FXCollections.observableArrayList(listaFiltrata));
//    }
//
//    private void colorRows() {
//        tableView.setRowFactory(row -> new TableRow<Entitate>() {
//            @Override
//            protected void updateItem(Entitate item, boolean empty) {
//                super.updateItem(item, empty);
//                if (item == null || empty) {
//                    setStyle("");
//                } else {
//                    if ("REJECTED".equals(item.getStatus())) {
//                        setStyle("-fx-background-color: #ffcccc;"); // Rosu
//                    } else if ("APPROVED".equals(item.getStatus())) {
//                        setStyle("-fx-background-color: #ccffcc;"); // Verde
//                    } else {
//                        setStyle("");
//                    }
//                }
//            }
//        });
//    }

    // ==========================================================
    // ZONA 5: METODE CRUD (INCARCARE / STERGERE)
    // ==========================================================
//    public void loadData() {
//        model.setAll(service.getAll());
//        tableView.setItems(model);
//        colorRows(); // Aplicam culorile dupa incarcare
//    }
//
//    @FXML public void handleDelete() {
//        Entitate selected = tableView.getSelectionModel().getSelectedItem();
//        if (selected != null) {
//            service.delete(selected.getId());
//            loadData(); // Refresh
//        } else {
//            // MessageAlert.showErrorMessage(null, "Selecteaza un rand!");
//        }
//    }
}