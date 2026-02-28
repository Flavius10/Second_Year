package grup.controller;

import grup.domain.Eveniment;
import grup.domain.Meci;
import grup.domain.User;
import grup.service.ServiceEveniment;
import grup.service.ServiceMeciuri;
import grup.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EvenimentController implements Observer {

    @FXML
    private TextField textRata;

    @FXML
    private ComboBox comboEchipa ;

    @FXML
    private ComboBox comboTip ;

    private ServiceEveniment serviceEveniment;

    private ObservableList<Meci> menuData = FXCollections.observableArrayList();

    public void setService(ServiceEveniment serviceEveniment) {
        this.serviceEveniment = serviceEveniment;

        this.serviceEveniment.addObserver(this);
    }

    @FXML
    public void initialize(){

        comboEchipa.setItems(FXCollections.observableArrayList("Gazda", "Oaspete"));
        comboEchipa.setValue("Gazda");

        comboTip.setItems(FXCollections.observableArrayList("Gol", "Cartonas Galben", "Cartonas Rosu"));
        comboTip.setValue("Gol");
    }


    public void handleAdd(){
        try{
            String echipa = this.comboEchipa.getValue().toString();
            Integer rataId = Integer.valueOf(this.textRata.getText().toString());
            String eveniment = this.comboTip.getValue().toString();

            Eveniment eveniment1 = new Eveniment(echipa, rataId, eveniment);

            this.serviceEveniment.save(eveniment1);
        } catch (Exception e){
            showMessage(Alert.AlertType.ERROR, "Nu a mers adaugarea", "Nu a mers");
        }

    }


    public void update(){
        showMessage(Alert.AlertType.INFORMATION, "S-a adaugat un nou eveniment", "S-a adaugat");
    }


    private void showMessage(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
