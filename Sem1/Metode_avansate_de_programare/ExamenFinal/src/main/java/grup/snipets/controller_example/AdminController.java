package grup.snipets.controller_example;

import grup.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AdminController {

    // ==========================================================
    // ZONA 1: CAMPURI (FXML, SERVICE, LISTE)
    // ==========================================================
//    @FXML
//    private TableView<Car> cars;
//
//    private ServiceCar serviceCar;
//    private ServiceUser serviceUser;
//
//    private ObservableList<Car> menuData = FXCollections.observableArrayList();

    // ==========================================================
    // ZONA 2: INITIALIZARE SI SETARE SERVICE
    // ==========================================================
//    public void setService(ServiceUser service, ServiceCar serviceCar, User user){
//        this.serviceCar = serviceCar;
//        this.serviceUser = service;
//        this.serviceCar.addObserver(this);
//
//        this.menuData.setAll(selectCars());
//    }
//
//    @FXML
//    public void initialize(){
//        cars.setItems(menuData);
//    }

    // ==========================================================
    // ZONA 3: OBSERVER SI INCARCARE DATE (REFRESH)
    // ==========================================================
//    public void update(){
//        this.menuData.setAll(selectCars());
//        this.cars.setItems(menuData);
//        showMessage(null, Alert.AlertType.INFORMATION,
//                "a fost modificata lista de masini", "a fost modificata");
//    };
//
//    public List<Car> selectCars(){
//        List<Car> cars = this.serviceCar.findAllCars();
//
//        var cars_admin = cars.stream().
//                filter(x -> x.getStatus().toString().contains("NEEDS_APPROVAL")).
//                collect(Collectors.toList());
//
//        return cars_admin;
//    }

    // ==========================================================
    // ZONA 4: HANDLERE PENTRU BUTOANE (ACTIONS)
    // ==========================================================
//    public void acceptCar(){
//        updateCarStatus(CarStatus.APPROVED);
//    }
//
//    public void rejectCar(){
//        updateCarStatus(CarStatus.REJECTED);
//    }

    // ==========================================================
    // ZONA 5: LOGICA INTERNA (PRIVATE METHODS)
    // ==========================================================
//    private void updateCarStatus(CarStatus status){
//        Car selectedCar = cars.getSelectionModel().getSelectedItem();
//
//        if(selectedCar == null){
//            showMessage(null, Alert.AlertType.WARNING,
//                    "Nicio masina selectata", "Va rugam selectati o masina din lista!");
//            return;
//        }
//
//        try{
//            this.serviceCar.update(selectedCar.getDenumire(), selectedCar.getComentarii(), status);
//        } catch (Exception e) {
//            showMessage(null, Alert.AlertType.ERROR,
//                    "Eroare", "A aparut o eroare: " + e.getMessage());
//        }
//    }

    // ==========================================================
    // ZONA 6: NAVIGARE (OPEN WINDOW) SI ALERTE
    // ==========================================================
//    private void openWindow(String fxml, String title, User user) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
//            Parent root = loader.load();
//
//            // AICI E CHEIA: Verificam ce controller e si ii dam service-ul
//            Object ctrl = loader.getController();
//
//            // Exemplu de verificare (Replace cu numele claselor tale)
//            if (ctrl instanceof AdminController) {
//                ((AdminController) ctrl).setService(service, serviceCar,  user);
//                serviceCar.addObserver((AdminController) ctrl);
//            }
//            else{
//                ((DealerController) ctrl).setService(service, serviceCar, user);
//                serviceCar.addObserver((DealerController) ctrl);
//            }
//
//            Stage stage = new Stage();
//            stage.setTitle(title + " - " + user.getUsername());
//            stage.setScene(new Scene(root));
//            stage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            showMessage(null, Alert.AlertType.WARNING,
//                    "Atentie",
//                    "Trebuie sa existe pagina");
//        }
//    }
//
//    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
//        Alert message = new Alert(type);
//        message.setHeaderText(header);
//        message.setContentText(text);
//        message.initOwner(owner);
//        message.showAndWait();
//    }
}