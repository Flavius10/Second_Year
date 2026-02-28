package grup.controller;

import grup.domain.Meci;
import grup.domain.User;
import grup.service.ServiceEveniment;
import grup.service.ServiceMeciuri;
import grup.service.ServiceUser;
import grup.utils.Observable;
import grup.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.skin.CellSkinBase;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class MainController implements Observer {

    @FXML
    private Label labelTableNumber;
    @FXML
    private TableView<Meci> tableMenu;
    @FXML
    private Button adminButton;
    @FXML
    private Button spectatorButton;


    private ServiceMeciuri serviceMeciuri;
    private User loggedInUser;
    private ServiceEveniment serviceEveniment;

    private ObservableList<Meci> menuData = FXCollections.observableArrayList();

    public void setService(ServiceMeciuri service, ServiceEveniment serviceEveniment, User user) {
        this.serviceMeciuri = service;
        this.loggedInUser = user;
        this.serviceEveniment = serviceEveniment;

        String role = loggedInUser.getType().toString();

        if (role.equals("ADMIN")){
            this.spectatorButton.setDisable(true);
            this.adminButton.setDisable(false);
        }
        else
        {
            this.spectatorButton.setDisable(false);
            this.adminButton.setDisable(true);
        }

        menuData.setAll(selectMeciuri());
    }


    @FXML
    public void initialize(){
        tableMenu.setItems(menuData);

        tableMenu.setOnMouseClicked(event -> {
            Meci selectedMeci = tableMenu.getSelectionModel().getSelectedItem();
            if (selectedMeci != null) {
                openWindow("/fxml/afisare_view.fxml", "Detalii Meci", selectedMeci);
            }
        });
    }


    public List<Meci> selectMeciuri(){
        List<Meci> meciuri = this.serviceMeciuri.findAllUsers();
        return meciuri;
    }

    public void handleActionAdmin(){
        try{
            openWindow("/fxml/adauga_eveniment.fxml", "Adauga eveniment", null);
        } catch (Exception e){
            showMessage(Alert.AlertType.ERROR, "Nu merge", "Nu merge");
        }

    }

    public void handleActionSpectator(){

    }

    public void update(){
        menuData.setAll(selectMeciuri());
        tableMenu.setItems(menuData);
    }

    private void showMessage(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void openWindow(String fxml, String title, Meci meci) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            Object ctrl = loader.getController();

            if (ctrl instanceof EvenimentController) {
                ((EvenimentController) ctrl).setService(serviceEveniment);
            } else{
                ((AfisareController) ctrl).setService(serviceEveniment, meci);
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
