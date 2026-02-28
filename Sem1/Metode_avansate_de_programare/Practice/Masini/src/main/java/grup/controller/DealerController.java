package grup.controller;

import grup.domain.Car;
import grup.domain.CarStatus;
import grup.domain.User;
import grup.service.ServiceCar;
import grup.service.ServiceUser;
import grup.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DealerController implements Observer {

    @FXML
    private TableView<Car> cars;
    @FXML
    private TextField comentarii;

    private ServiceCar serviceCar;
    private ServiceUser serviceUser;

    private ObservableList<Car> menuData = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        cars.setItems(menuData);


        cars.setOnMouseClicked(event -> {
            // Verificam daca avem o selectie valida
            Car selectedCar = cars.getSelectionModel().getSelectedItem();

            // Daca s-a dat click si exista un rand selectat, deschidem fereastra
            if (selectedCar != null) {
                openWindow("/fxml/masina_view.fxml", "Detalii Masina", selectedCar);
            }
        });
    }

    public void setService(ServiceUser service, ServiceCar serviceCar, User user){
        this.serviceCar = serviceCar;
        this.serviceUser = service;

        this.menuData.setAll(selectCars());
    }

    public List<Car> selectCars(){
        List<Car> cars = this.serviceCar.findAllCars();

        return cars;
    }

    public void update(){

        this.menuData.setAll(selectCars());
        this.cars.setItems(menuData);
        showMessage(null, Alert.AlertType.INFORMATION,
                "a fost modificata lista de masini", "a fost modificata");

    }

    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }

    public void trimiteMasina(){
        Car car_selected = cars.getSelectionModel().getSelectedItem();
        String comentariu = this.comentarii.getText();

        if (car_selected != null){
            try{
                this.serviceCar.update(car_selected.getDenumire(), comentariu, CarStatus.NEEDS_APPROVAL);
            } catch(Exception e){
                showMessage(null, Alert.AlertType.ERROR, "Nu am putut modifica masina", "da");
            }
        }
    }

    private void openWindow(String fxml, String title, Car car) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            // AICI E CHEIA: Verificam ce controller e si ii dam service-ul
            Object ctrl = loader.getController();

            // Exemplu de verificare (Replace cu numele claselor tale)
            if (ctrl instanceof CarController) {
                ((CarController) ctrl).setService(serviceCar, car);
            }



            Stage stage = new Stage();
            stage.setTitle(title + " " + car.getDenumire());
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showMessage(null, Alert.AlertType.WARNING,
                    "Atentie",
                    "Trebuie sa existe pagina");
        }
    }


}
