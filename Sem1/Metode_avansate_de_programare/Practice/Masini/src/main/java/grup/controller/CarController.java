package grup.controller;

import grup.domain.Car;
import grup.domain.User;
import grup.service.ServiceCar;
import grup.service.ServiceUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class CarController {

    @FXML
    private Label denumireMasina;
    @FXML
    private Label desciereMasina;
    @FXML
    private Label statusMasina;
    @FXML
    private Label pretMasina;

    @FXML
    private Label comentariiMasina;

    private Car car;
    private ServiceCar serviceCar;

    @FXML
    public void initialize(){
    }

    public void setService(ServiceCar serviceCar, Car car){
        this.serviceCar = serviceCar;
        this.car = car;


        denumireMasina.setText(car.getDenumire());
        desciereMasina.setText(car.getDescriere());
        pretMasina.setText(car.getPret().toString());
        statusMasina.setText(car.getStatus().toString());

        comentariiMasina.setText(car.getComentarii());
    }

}
