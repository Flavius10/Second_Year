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
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class AdminController implements Observer {

    @FXML
    private TableView<Car> cars;

    private ServiceCar serviceCar;
    private ServiceUser serviceUser;

    private ObservableList<Car> menuData = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        cars.setItems(menuData);
    }

    public void setService(ServiceUser service, ServiceCar serviceCar, User user){
        this.serviceCar = serviceCar;
        this.serviceUser = service;
        this.serviceCar.addObserver(this);

        this.menuData.setAll(selectCars());
    }

    public List<Car> selectCars(){
        List<Car> cars = this.serviceCar.findAllCars();

        var cars_admin = cars.stream().
                filter(x -> x.getStatus().toString().contains("NEEDS_APPROVAL")).
                collect(Collectors.toList());

        return cars_admin;
    }

    public void update(){

        this.menuData.setAll(selectCars());
        this.cars.setItems(menuData);
        showMessage(null, Alert.AlertType.INFORMATION,
                "a fost modificata lista de masini", "a fost modificata");
    };

    public void acceptCar(){
        updateCarStatus(CarStatus.APPROVED);
    }

    public void rejectCar(){
        updateCarStatus(CarStatus.REJECTED);
    }

    private void updateCarStatus(CarStatus status){
        Car selectedCar = cars.getSelectionModel().getSelectedItem();

        if(selectedCar == null){
            showMessage(null, Alert.AlertType.WARNING,
                    "Nicio masina selectata", "Va rugam selectati o masina din lista!");
            return;
        }

        try{
            this.serviceCar.update(selectedCar.getDenumire(), selectedCar.getComentarii(), status);
        } catch (Exception e) {
            showMessage(null, Alert.AlertType.ERROR,
                    "Eroare", "A aparut o eroare: " + e.getMessage());
        }
    }

    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }
}
