package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*; // Important pentru Background
import org.example.domain.Persoana;
import org.example.domain.TypeDuck;
import org.example.domain.ducks.Duck;
import org.example.services.CardService;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.services.PersoanaService;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private CardService cardService;

    @FXML
    private Label welcomeLabel;


    //Info pentru duck
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
    private Button prevDuckBtn;
    @FXML
    private Button nextDuckBtn;

    //Info pentru persoana
    @FXML
    private TableView<Persoana> persoanaTableView;
    @FXML
    private TableColumn<Persoana, Long> idColumnPersoana;
    @FXML
    private TableColumn<Persoana, String> usernameColumnPersoana;
    @FXML
    private TableColumn<Persoana, String> emailColumnPersoana;
    @FXML
    private TableColumn<Persoana, String> numeColumn;
    @FXML
    private TableColumn<Persoana, String> prenumeColumn;
    @FXML
    private TableColumn<Persoana, String> ocupatieColumn;
    @FXML
    private TableColumn<Persoana, LocalDate> dataNastereColumn;

    @FXML
    private Button prevPersonBtn;
    @FXML
    private Button nextPersonBtn;


    private ObservableList<Duck> ducks = FXCollections.observableArrayList();
    private ObservableList<Persoana> persoane = FXCollections.observableArrayList();

    public void setServices(DuckService duckService, PersoanaService persoanaService) {
        this.duckService = duckService;
        this.persoanaService = persoanaService;

        welcomeLabel.setText("DUCK SOCIAL NETWORK");
        welcomeLabel.setStyle("-fx-font-size: 40px; -fx-font-family:'Science Gothic'; -fx-font-weight: bold; -fx-text-fill: black");
        duckTableView.setStyle("-fx-font-size: 16px;-fx-font-family:'Science Gothic'");
        persoanaTableView.setStyle("-fx-font-size: 16px;-fx-font-family:'Science Gothic'");
        typeComboBox.setStyle("-fx-font-size: 16px;-fx-font-family:'Science Gothic'");

        duckTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        persoanaTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));
        rezistentaColumn.setCellValueFactory(new PropertyValueFactory<>("rezistenta"));
        vitezaColumn.setCellValueFactory(new PropertyValueFactory<>("viteza"));


        idColumnPersoana.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumnPersoana.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumnPersoana.setCellValueFactory(new PropertyValueFactory<>("email"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumeColumn.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        ocupatieColumn.setCellValueFactory(new PropertyValueFactory<>("ocupatie"));
        dataNastereColumn.setCellValueFactory(new PropertyValueFactory<>("dataNastere"));

        loadDuckPage();
        loadPersonPage();
        setupPaginationButtons();
        setupComboBox();
    }

    private void loadTableDataDucks() {
        List<Duck> ds = (List<Duck>) duckService.findAllDucks();
        ducks = FXCollections.observableArrayList(ds);
        duckTableView.setItems(ducks);
    }

    private void loadTableDataPersoane() {
        List<Persoana> ps = (List<Persoana>) persoanaService.findAllPersons();
        persoane = FXCollections.observableArrayList(ps);
        persoanaTableView.setItems(persoane);
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

    private int pageSize = 5;
    private int currentPageDuck = 0;
    private int currentPagePerson = 0;

    private void loadDuckPage() {
        Pageable pageable = new Pageable(currentPageDuck, pageSize);
        Page<Duck> page = duckService.findAllOnPage(pageable);

        List<Duck> list = new ArrayList<>();
        for (Duck d : page.getElementsOnPage()) {
            list.add(d);
        }

        duckTableView.setItems(FXCollections.observableArrayList(list));

        prevDuckBtn.setDisable(currentPageDuck == 0);
        nextDuckBtn.setDisable(list.size() < pageSize);
    }

    private void loadPersonPage() {
        Pageable pageable = new Pageable(currentPagePerson, pageSize);
        Page<Persoana> page = persoanaService.findAllOnPage(pageable);

        List<Persoana> list = new ArrayList<>();
        for (Persoana p : page.getElementsOnPage()) {
            list.add(p);
        }

        persoanaTableView.setItems(FXCollections.observableArrayList(list));

        prevPersonBtn.setDisable(currentPagePerson == 0);
        nextPersonBtn.setDisable(list.size() < pageSize);
    }

    private void setupPaginationButtons() {

        prevDuckBtn.setOnAction(e -> {
            if (currentPageDuck > 0) {
                currentPageDuck--;
                loadDuckPage();
            }
        });

        nextDuckBtn.setOnAction(e -> {
            currentPageDuck++;
            loadDuckPage();
        });

        prevPersonBtn.setOnAction(e -> {
            if (currentPagePerson > 0) {
                currentPagePerson--;
                loadPersonPage();
            }
        });

        nextPersonBtn.setOnAction(e -> {
            currentPagePerson++;
            loadPersonPage();
        });
    }
}