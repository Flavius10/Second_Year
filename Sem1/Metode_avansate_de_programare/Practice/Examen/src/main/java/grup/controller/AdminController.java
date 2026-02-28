package grup.controller;

import grup.domain.User;
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


    private ServiceUser serviceUser;

    //private ObservableList<Car> menuData = FXCollections.observableArrayList();

    @FXML
    public void initialize(){

    }

    public void setService(ServiceUser service, User user){
        this.serviceUser = service;
    }


    public void update(){
    };

    public void acceptCar(){
    }

    public void rejectCar(){
    }


    public static void showMessage(Stage owner, Alert.AlertType type, String header, String text){
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.initOwner(owner);
        message.showAndWait();
    }
}
