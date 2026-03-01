package grup.controller;

import grup.domain.Drone;
import grup.domain.Producator;
import grup.service.ServiceDrone;
import grup.service.ServiceProducator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ControllerMain {

    @FXML
    private TableView<Producator> producatorTable;
    @FXML
    private TableColumn<Producator, Integer> colProdId;
    @FXML
    private TableColumn<Producator, String> colProdNume;
    @FXML
    private TableColumn<Producator, String> colProdTara;

    @FXML
    private TableView<Drone> dronaTable;
    @FXML
    private TableColumn<Drone, Integer> colDronaId;
    @FXML
    private TableColumn<Drone, String> colDronaModel;
    @FXML
    private TableColumn<Drone, Integer> colDronaGreutate;
    @FXML
    private TableColumn<Drone, String> colDronaCadru;

    @FXML
    private TextField txtModel;
    @FXML
    private TextField txtGreutate;
    @FXML
    private TextField txtCadru;

    private ServiceProducator serviceProducator;
    private ServiceDrone serviceDrone;

    private ObservableList<Producator> producatorModel = FXCollections.observableArrayList();
    private ObservableList<Drone> droneModel = FXCollections.observableArrayList();

    public void setService(ServiceProducator serviceP, ServiceDrone serviceD) {
        this.serviceProducator = serviceP;
        this.serviceDrone = serviceD;

        initTable();
        loadProducatoriData();
        selectareParinte();
        selectareCopil();
    }

    private void loadProducatoriData(){
        try{
            List<Producator> producatori = serviceProducator.getAllProducatori();
            producatorModel.setAll(producatori);
        } catch (Exception e){
            showMessage(Alert.AlertType.ERROR, "Error", "Could not load producatori data: " + e.getMessage());
        }
    }

    private void initTable() {
        colProdId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProdNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        colProdTara.setCellValueFactory(new PropertyValueFactory<>("tara_origine"));

        colDronaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDronaModel.setCellValueFactory(new PropertyValueFactory<>("nume_model"));
        colDronaGreutate.setCellValueFactory(new PropertyValueFactory<>("greutate_grame"));
        colDronaCadru.setCellValueFactory(new PropertyValueFactory<>("tip_cadru"));

        dronaTable.setItems(droneModel);
        producatorTable.setItems(producatorModel);
    }

    // Functie care sa incarce dronele pentru producatorul selectat in tabelul de producatori
    private void selectareParinte(){
        producatorTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadDronaData(newSelection.getId());
            } else {
                droneModel.clear();
            }
        });
    }

    // Functie care sa incarce dronele pentru profucatorul selectat
    private void loadDronaData(int producatorId){
        try{
            List<Drone> drone = serviceDrone.getDronesByProducator(producatorId);
            droneModel.setAll(drone);
        } catch (Exception e){
            showMessage(Alert.AlertType.ERROR, "Error", "Could not load drone data: " + e.getMessage());
        }
    }

    // Functie care arata un mesaj de alerta cu diferite tipuri
    private void showMessage(Alert.AlertType type, String header, String content) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Functie care sa incarce datele dronei selectate in campurile de text pentru editare
    private void selectareCopil(){
        dronaTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtModel.setText(newSelection.getNume_model());
                txtGreutate.setText(String.valueOf(newSelection.getGreutate_grame()));
                txtCadru.setText(newSelection.getTip_cadru());
            } else {
                txtModel.clear();
                txtGreutate.clear();
                txtCadru.clear();
            }
        });
    }

    // Functie care sa goleasca campurile de text dupa adaugare/actualizare/stergere
    private void clearDronaFields() {
        txtModel.clear();
        txtGreutate.clear();
        txtCadru.clear();
    }

    // Functie care sa adauge o drona noua cu valorile din campurile de text, pentru producatorul selectat in tabel, cu validare si mesaje de eroare/succes
    @FXML
    private void handleAddDrona(){
        Producator selectedProducator = producatorTable.getSelectionModel().getSelectedItem();
        if (selectedProducator == null) {
            showMessage(Alert.AlertType.WARNING, "No Producator Selected", "Please select a producator to add a drone.");
            return;
        }

        try {
            String model = txtModel.getText();
            int greutate = Integer.parseInt(txtGreutate.getText());
            String cadru = txtCadru.getText();

            Drone newDrona = new Drone(0, model, greutate, cadru, selectedProducator.getId());
            serviceDrone.addDrone(newDrona);
            loadDronaData(selectedProducator.getId());
            clearDronaFields();
            showMessage(Alert.AlertType.INFORMATION, "Success", "Drone added successfully.");
        } catch (NumberFormatException e) {
            showMessage(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid number for weight.");
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Error", "Could not add drone: " + e.getMessage());
        }
    }

    // Functie care sa stearga drona selectata din tabel, cu confirmare
    @FXML
    private void handleDeleteDrona() {
        Drone selectedDrona = dronaTable.getSelectionModel().getSelectedItem();
        if (selectedDrona == null) {
            showMessage(Alert.AlertType.WARNING, "No Drone Selected", "Please select a drone to delete.");
            return;
        }


        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setContentText("Are you sure you want to delete this drone?");

        confirmationAlert.showAndWait().ifPresent(response -> {

            try {
                if (response == ButtonType.OK) {
                    serviceDrone.deleteDrone(selectedDrona.getId());
                    loadDronaData(selectedDrona.getProducator_id());
                    clearDronaFields();
                    showMessage(Alert.AlertType.INFORMATION, "Success", "Drone deleted successfully.");
                }
            } catch (Exception e) {
                showMessage(Alert.AlertType.ERROR, "Error", "Could not delete drone: " + e.getMessage());
            }
        });
    }


    // Functie care sa actualizeze drona selectata cu valorile din campurile de text
    @FXML
    private void handleUpdateDrona(){
        Drone drone = dronaTable.getSelectionModel().getSelectedItem();
        if(drone == null) {
            showMessage(Alert.AlertType.WARNING, "No Drone Selected", "Please select a drone to update.");
            return;
        }

        try{
            String model = txtModel.getText();
            int greutate = Integer.parseInt(txtGreutate.getText());
            String cadru = txtCadru.getText();

            drone.setNume_model(model);
            drone.setGreutate_grame(greutate);
            drone.setTip_cadru(cadru);

            serviceDrone.updateDrone(drone);
            loadDronaData(drone.getProducator_id());
            clearDronaFields();
            showMessage(Alert.AlertType.INFORMATION, "Success", "Drone updated successfully.");
        } catch (NumberFormatException e) {
            showMessage(Alert.AlertType.ERROR, "Invalid Input", "Please enter a valid number for weight.");
        } catch (Exception e) {
            showMessage(Alert.AlertType.ERROR, "Error", "Could not update drone: " + e.getMessage());
        }
    }

    // Functie pentru butonul de refresh
    @FXML
    private void handleRefresh() {
        producatorTable.getSelectionModel().clearSelection();
        dronaTable.getSelectionModel().clearSelection();

        loadProducatoriData();

        droneModel.clear();
        clearDronaFields();

        showMessage(Alert.AlertType.INFORMATION, "Refresh", "Datele au fost reîncărcate cu succes din baza de date.");
    }


}
