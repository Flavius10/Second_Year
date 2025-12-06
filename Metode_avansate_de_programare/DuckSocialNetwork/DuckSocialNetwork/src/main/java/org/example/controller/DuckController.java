package org.example.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.domain.TypeDuck;
import org.example.domain.card.FlyingCard;
import org.example.domain.card.SwimmingCard;
import org.example.domain.card.TypeCard;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.FlyingDuck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.services.DuckService;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DuckController {

    private DuckService duckService;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextArea resultArea;
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
    private TableColumn<Duck, Double> vitezaColumn;
    @FXML
    private TableColumn<Duck, Double> rezistentaColumn;

    @FXML
    private Button prevDuckBtn;
    @FXML
    private Button nextDuckBtn;
    @FXML
    private Button addDuckBtn;
    @FXML
    private Button deleteDuckBtn;
    @FXML
    private ComboBox<String> duckTypeComboFilter;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> duckTypeCombo;
    @FXML
    private TextField vitezaField;
    @FXML
    private TextField rezistentaField;


    private int pageSize = 5;
    private int currentPageDuck = 0;

    private ObservableList<Duck> duckModel = FXCollections.observableArrayList();

    public void initialize(){
        welcomeLabel.setText("Gestionare Rata");

        duckTypeCombo.setItems(FXCollections.observableArrayList("SWIMMING", "FLYING", "FLYING_AND_SWIMMING"));


        printLog("Aplicatia a fost incarcata. Asteptare utilizator...");
    }

    public void setDuckService(DuckService duckService) {

        this.duckService = duckService;

        initColumns();
        setupAllEventHandlers();

    }

    public void initColumns(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));
        vitezaColumn.setCellValueFactory(new PropertyValueFactory<>("viteza"));
        rezistentaColumn.setCellValueFactory(new PropertyValueFactory<>("rezistenta"));

        duckTableView.setItems(duckModel);
    }

    private void loadDuckPage(){
        Pageable duckPage = new Pageable(currentPageDuck, pageSize);
        Page<Duck> page = duckService.findAllOnPage(duckPage);
        List<Duck> ducks = new ArrayList<>();

        if(page != null && page.getElementsOnPage() != null)
            page.getElementsOnPage().forEach(ducks::add);

        duckModel.setAll(ducks);
        prevDuckBtn.setDisable(currentPageDuck == 0);
        nextDuckBtn.setDisable(ducks.size() < pageSize);

    }

    private void setupAllEventHandlers(){
        prevDuckBtn.setOnAction(e -> { currentPageDuck--; loadDuckPage(); });
        nextDuckBtn.setOnAction(e -> { currentPageDuck++; loadDuckPage(); });

        addDuckBtn.setOnAction(e -> {
            try{
                Long id = System.currentTimeMillis();
                String tipRataStr = duckTypeCombo.getValue();
                String username = usernameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                Double viteza = Double.parseDouble(vitezaField.getText());
                Double rezistenta = Double.parseDouble(rezistentaField.getText());

                if(tipRataStr == null)
                    printLog("Tipul rata nu a fost selectat!");

                TypeDuck tip = TypeDuck.valueOf(tipRataStr);

                Duck duck;
                if(tip == TypeDuck.FLYING){
                    FlyingCard flyingCard = new FlyingCard(id, "FlyingCard", new ArrayList<>(), TypeCard.FLYING);
                    duck = new FlyingDuck(id, username, email, password, tip, viteza, rezistenta, flyingCard);
                }
                else{
                    SwimmingCard swimmingCard = new SwimmingCard(id, "SwimmingCard", new ArrayList<>(), TypeCard.SWIMMING);
                    duck = new SwimmingDuck(id, username, email, password, tip, viteza, rezistenta, swimmingCard);
                }

                duckService.saveDuck(duck);
                loadDuckPage();

            } catch(Exception ex){
               printLog("Nu merge sa sterg rata!");
            }
        });


        deleteDuckBtn.setOnAction(e -> {
            Duck selectedDuck = duckTableView.getSelectionModel().getSelectedItem();
            if(selectedDuck != null) {
                try {
                    duckService.deleteDuck(selectedDuck);
                    loadDuckPage();
                } catch (Exception ex) {
                    printLog("Nu merge sa sterg rata!");
                }
            }
        });


    }

    private void printLog(String message){
        if (resultArea != null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            resultArea.appendText("[" + timestamp + "] " + message + "\n");
        }else{
            System.out.println("ResultArea nu este legata la FXML");
        }
    }

    private boolean checkUserExists(String username){
        return duckService.findByUsernameDuck(username) != null;
    }



}
