package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.domain.Persoana;
import org.example.domain.TypeDuck;
import org.example.domain.card.FlyingCard;
import org.example.domain.card.SwimmingCard;
import org.example.domain.card.TypeCard;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.FlyingDuck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.network.NetworkService;
import org.example.services.*;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.awt.desktop.AppReopenedEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PersoanaController {


    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;
    private RequestService requestService;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TextArea resultArea;
    @FXML
    private TableView<Persoana> persoanaTableView;

    @FXML
    private TableColumn<Persoana, Long> idColumn;
    @FXML
    private TableColumn<Persoana, String> usernameColumn;
    @FXML
    private TableColumn<Persoana, String> emailColumn;
    @FXML
    private TableColumn<Persoana, String> numeColumn;
    @FXML
    private TableColumn<Persoana, String> prenumeColumn;
    @FXML
    private TableColumn<Persoana, Integer> ocupatieColumn;
    @FXML
    private TableColumn<Persoana, LocalDate> dataNastereColumn;

    @FXML
    private Button prevPersoanaBtn;
    @FXML
    private Button nextPersoanaBtn;
    @FXML
    private Button addPersoanaBtn;
    @FXML
    private Button deletePersoanaBtn;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField numeField;
    @FXML
    private TextField prenumeField;
    @FXML
    private TextField ocupatieField;
    @FXML
    private DatePicker datePicker;


    private int pageSize = 5;
    private int currentPageDuck = 0;

    private ObservableList<Persoana> persoanaModel = FXCollections.observableArrayList();

    public void initialize(){
        welcomeLabel.setText("Gestionare Persoane");


        printLog("Aplicatia a fost incarcata. Asteptare utilizator...");
    }

    public void setPersoanaService(DuckService duckService, PersoanaService persoanaService,
                               FriendshipService friendshipService, NetworkService networkService
                                , MessageService messageService, RequestService requestService) {

        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.messageService = messageService;
        this.requestService = requestService;

        initColumns();
        setupAllEventHandlers();

        loadPersonPage();

    }

    public void initColumns(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumeColumn.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        ocupatieColumn.setCellValueFactory(new PropertyValueFactory<>("ocupatie"));
        dataNastereColumn.setCellValueFactory(new PropertyValueFactory<>("dataNastere"));

        persoanaTableView.setItems(persoanaModel);
    }

    private void loadPersonPage(){
        Pageable personPage = new Pageable(currentPageDuck, pageSize);
        Page<Persoana> page = persoanaService.findAllOnPage(personPage);
        List<Persoana> persons = new ArrayList<>();

        if(page != null && page.getElementsOnPage() != null)
            page.getElementsOnPage().forEach(persons::add);

        persoanaModel.setAll(persons);
        prevPersoanaBtn.setDisable(currentPageDuck == 0);
        nextPersoanaBtn.setDisable(persons.size() < pageSize);

    }

    private void setupAllEventHandlers(){
        prevPersoanaBtn.setOnAction(e -> { currentPageDuck--; loadPersonPage(); });
        nextPersoanaBtn.setOnAction(e -> { currentPageDuck++; loadPersonPage(); });

        addPersoanaBtn.setOnAction(e -> {
            try{
               String username = usernameField.getText();
               String email = emailField.getText();
               String password = passwordField.getText();
               Long id = System.currentTimeMillis();

               String nume = numeField.getText();
               String prenume = prenumeField.getText();
               String ocupatie = ocupatieField.getText();
               LocalDate dataNastere = datePicker.getValue();
               if (dataNastere == null) { printLog("Eroare: Alege data nasterii!"); return; }
               Persoana person = new Persoana(id, username, email, password, nume, prenume, ocupatie, dataNastere);


                persoanaService.savePerson(person);
                loadPersonPage();

            } catch(Exception ex){
                printLog("Nu merge sa adaug persoana!");
            }
        });


        deletePersoanaBtn.setOnAction(e -> {
            Persoana selectedPersoana = persoanaTableView.getSelectionModel().getSelectedItem();
            if(selectedPersoana != null) {
                try {
                    persoanaService.deletePerson(selectedPersoana);
                    loadPersonPage();
                } catch (Exception ex) {
                    printLog("Nu merge sa sterg persoana!");
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

    @FXML
    public void switchToMainTab(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        MainController controller = loader.getController();
        controller.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService, null);

        stage.show();
    }

}
