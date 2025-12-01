package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*; // Important pentru Background
import org.example.domain.TypeDuck;
import org.example.domain.ducks.Duck;
import org.example.services.DuckService;

import java.net.URL;
import java.util.List;

public class MainController {

    private DuckService duckService;

    // Legatura cu VBox-ul din FXML
    @FXML
    private VBox mainLayout;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<Duck> duckTableView;

    @FXML
    private TableColumn<Duck, Long> idColumn;
    @FXML
    private TableColumn<Duck, String> usernameColumn;
    @FXML
    private TableColumn<Duck, String> emailColumn;
    @FXML
    private TableColumn<Duck, String> typeColumn;
    @FXML
    private TableColumn<Duck, Long> flockIdColumn;
    @FXML
    private TableColumn<Duck, Double> vitezaColumn;
    @FXML
    private TableColumn<Duck, Double> rezistentaColumn;
    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ObservableList<Duck> ducks = FXCollections.observableArrayList();

    public void setServices(DuckService duckService) {
        this.duckService = duckService;


        welcomeLabel.setText("DUCK SOCIAL NETWORK");
        welcomeLabel.setStyle("-fx-font-size: 40px; -fx-font-family:'Science Gothic'; -fx-font-weight: bold; -fx-text-fill: black");
        duckTableView.setStyle("-fx-font-size: 16px;-fx-font-family:'Science Gothic'");
        typeComboBox.setStyle("-fx-font-size: 16px;-fx-font-family:'Science Gothic'");

        duckTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));
        rezistentaColumn.setCellValueFactory(new PropertyValueFactory<>("rezistenta"));
        vitezaColumn.setCellValueFactory(new PropertyValueFactory<>("viteza"));

        loadTableData(); // Am extras incarcarea datelor intr-o metoda separata ca sa fie mai curat
        setupComboBox(); // La fel si pentru ComboBox
    }

    private void loadTableData() {
        List<Duck> ds = (List<Duck>) duckService.findAllDucks();
        ducks = FXCollections.observableArrayList(ds);
        duckTableView.setItems(ducks);
    }

    private void setupComboBox() {
        ObservableList<String> typeOptions = FXCollections.observableArrayList();
        typeOptions.add("ALL DUCKS");
        typeOptions.add("SWIMMING");
        typeOptions.add("FLYING");
        typeOptions.add("FLYING AND SWIMMING");
        typeComboBox.setItems(typeOptions);
        typeComboBox.getSelectionModel().select(0);

        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;

            List<Duck> result;
            switch (newValue) {
                case "SWIMMING":
                    result = (List<Duck>) duckService.findByType(TypeDuck.SWIMMING);
                    break;
                case "FLYING":
                    result = (List<Duck>) duckService.findByType(TypeDuck.FLYING);
                    break;
                case "FLYING AND SWIMMING":
                    result = (List<Duck>) duckService.findByType(TypeDuck.FLYING_AND_SWIMMING);
                    break;
                default:
                    result = (List<Duck>) duckService.findAllDucks();
                    break;
            }
            ducks = FXCollections.observableArrayList(result);
            duckTableView.setItems(ducks);
        });
    }
}