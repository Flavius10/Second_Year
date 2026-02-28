package grup.snipets.controller_example;

import grup.utils.Observer;
import grup.utils.Page;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class PaginationController { // implements Observer

//    // --- ELEMENTE UI TABEL ---
//    @FXML
//    private TableView<Car> tableView;
//
//    // --- ELEMENTE UI PAGINARE ---
//    @FXML
//    private Button btnPrevious;
//    @FXML
//    private Button btnNext;
//    @FXML
//    private Label lblPageInfo;
//
//    // ComboBox 1: Marimea Paginii (Functional)
//    @FXML
//    private ComboBox<Integer> comboPageSize;
//
//    // ComboBox 2: Random Stuff (Exemplu didactic)
//    @FXML
//    private ComboBox<String> comboActiuni;
//
//    // --- DATE SI SERVICE ---
//    private ServiceCar serviceCar;
//    private ObservableList<Car> model = FXCollections.observableArrayList();
//
//    // --- STARE PAGINARE ---
//    private int currentPage = 0;
//    private int pageSize = 5;
//    private int totalRecords = 0;
//
//    @FXML
//    public void initialize() {
//        // 1. Legam tabelul
//        tableView.setItems(model);
//
//        // 2. CONFIGURARE COMBO BOX 1 (Pagini)
//        comboPageSize.setItems(FXCollections.observableArrayList(5, 10, 20));
//        comboPageSize.setValue(pageSize); // Valoare initiala
//
//        // Listener: Cand schimbi marimea, resetam la pagina 0
//        comboPageSize.setOnAction(event -> {
//            this.pageSize = comboPageSize.getValue();
//            this.currentPage = 0;
//            loadData();
//        });
//
//        // 3. CONFIGURARE COMBO BOX 2 (Random / Filtre)
//        comboActiuni.setItems(FXCollections.observableArrayList(
//                "Arata Mesaj",
//                "Filtreaza Benzina",
//                "Reseteaza Tot"
//        ));
//
//        // Listener: Aici pui logica pentru cand userul alege ceva
//        comboActiuni.setOnAction(event -> {
//            String selectie = comboActiuni.getValue();
//            if (selectie != null) {
//                handleRandomAction(selectie);
//            }
//        });
//
//        // 4. CLICK SIMPLU PE TABEL
//        tableView.setOnMouseClicked(event -> {
//            Car selectedCar = tableView.getSelectionModel().getSelectedItem();
//            if (selectedCar != null) {
//                openWindow("/fxml/masina_view.fxml", "Detalii Masina", selectedCar);
//            }
//        });
//    }
//
//    public void setService(ServiceCar serviceCar) {
//        this.serviceCar = serviceCar;
//        this.serviceCar.addObserver(this);
//        loadData(); // Incarcam prima data
//    }
//
//    @Override
//    public void update() {
//        loadData(); // Reincarcam la modificari
//    }
//
//    // --- LOGICA PAGINARE ---
//    private void loadData() {
//        // 1. Luam data din DatePicker
//        LocalDate filterDate = datePicker.getValue();
//
//        // 2. O trimitem la Service
//        // ATENTIE: Am modificat semnatura metodei in Service sa accepte si data!
//        Page<Car> page = serviceCar.getCarsOnPage(currentPage, pageSize, filterDate);
//
//        // 3. Afisam rezultatele (care acum sunt filtrate)
//        this.totalRecords = page.getTotalElementCount();
//        model.setAll(page.getElementsOnPage());
//
//        // Debug ca sa vezi in consola ca merge
//        if (filterDate != null) {
//            System.out.println("Filtrare activa: Masini dupa " + filterDate + ". Total gasite: " + totalRecords);
//        }
//
//        updateControls();
//    }
//
//    private void updateControls() {
//        int totalPages = (totalRecords == 0) ? 1 : (int) Math.ceil((double) totalRecords / pageSize);
//        lblPageInfo.setText("Pag " + (currentPage + 1) + " / " + totalPages);
//
//        btnPrevious.setDisable(currentPage == 0);
//        btnNext.setDisable((currentPage + 1) * pageSize >= totalRecords);
//    }
//
//    // --- HANDLER PENTRU COMBO BOX RANDOM ---
//    private void handleRandomAction(String actiune) {
//        System.out.println("Userul a ales: " + actiune);
//
//        switch (actiune) {
//            case "Arata Mesaj":
//                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ai selectat o optiune random!");
//                alert.show();
//                break;
//            case "Filtreaza Benzina":
//                // Aici ai putea chema service.filterByFuel("Benzina")...
//                System.out.println("Simulam filtrarea...");
//                break;
//            case "Reseteaza Tot":
//                comboActiuni.getSelectionModel().clearSelection();
//                System.out.println("Selectie curatata.");
//                break;
//        }
//    }
//
//    // --- BUTOANE NAVIGARE ---
//    @FXML
//    public void onPrevious() {
//        if (currentPage > 0) {
//            currentPage--;
//            loadData();
//        }
//    }
//
//    @FXML
//    public void onNext() {
//        if ((currentPage + 1) * pageSize < totalRecords) {
//            currentPage++;
//            loadData();
//        }
//    }
//
//    // --- DESCHIDERE FEREASTRA ---
//    private void openWindow(String fxml, String title, Car car) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//            Parent root = loader.load();
//
//            CarController ctrl = loader.getController();
//            ctrl.setService(serviceCar, car);
//
//            Stage stage = new Stage();
//            stage.setTitle(title);
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}