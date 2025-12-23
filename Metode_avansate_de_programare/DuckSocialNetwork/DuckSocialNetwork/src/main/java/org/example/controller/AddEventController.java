package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.*;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.Lane;
import org.example.domain.ducks.SwimmingDuck;
import org.example.domain.events.RaceEvent;
import org.example.services.DuckService;
import org.example.services.EventService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AddEventController {

    private DuckService duckService;
    private EventService eventService;
    private User loggedInUser;

    @FXML private TextField eventNameField;
    @FXML private Spinner<Integer> duckCountSpinner;
    @FXML private TextField laneLengthField;
    @FXML private ListView<Lane> lanesListView;
    @FXML private Label errorLabel;

    private ObservableList<Lane> lanesObservableList = FXCollections.observableArrayList();
    private long laneIdCounter = 1;

    @FXML
    public void initialize() {
        lanesListView.setItems(lanesObservableList);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 3);
        duckCountSpinner.setValueFactory(valueFactory);
    }

    public void setServices(DuckService duckService, EventService eventService, User loggedInUser) {
        this.duckService = duckService;
        this.eventService = eventService;
        this.loggedInUser = loggedInUser;
    }

    @FXML
    public void handleAddLane() {
        try {
            String text = laneLengthField.getText();
            if (text.isEmpty()) return;

            int length = Integer.parseInt(text);
            if (length <= 0) {
                errorLabel.setText("Lungimea trebuie să fie pozitivă!");
                return;
            }

            Lane lane = new Lane(laneIdCounter++, length);
            lanesObservableList.add(lane);

            laneLengthField.clear();
            errorLabel.setText("");
        } catch (NumberFormatException e) {
            errorLabel.setText("Introdu un număr valid pentru lungime!");
        }
    }

    @FXML
    public void handleCreateEvent() {
        String numeEvent = eventNameField.getText();
        int nrRateDorite = duckCountSpinner.getValue();
        List<Lane> lanes = new ArrayList<>(lanesObservableList);

        errorLabel.setText("");

        if (numeEvent.isEmpty()) {
            errorLabel.setText("Numele evenimentului este obligatoriu!");
            return;
        }
        if (lanes.isEmpty()) {
            errorLabel.setText("Adaugă cel puțin un culoar (Lane)!");
            return;
        }
        if (nrRateDorite > lanes.size()) {
            errorLabel.setText("Eroare: Vrei " + nrRateDorite + " rațe, dar ai doar " + lanes.size() + " culoare!");
            return;
        }

        try {
            Iterable<Duck> allDucks = duckService.findAllDucks();

            List<SwimmingDuck> swimmingDucks = StreamSupport.stream(allDucks.spliterator(), false)
                    .filter(d -> d instanceof SwimmingDuck)
                    .map(d -> (SwimmingDuck) d)
                    .collect(Collectors.toList());

            if (swimmingDucks.size() < nrRateDorite) {
                errorLabel.setText("Nu sunt suficiente rațe înotătoare în baza de date! (Disponibile: " + swimmingDucks.size() + ")");
                return;
            }

            List<SwimmingDuck> participatingDucks = swimmingDucks.stream()
                    .sorted(Comparator.comparingDouble(SwimmingDuck::getViteza).reversed())
                    .limit(nrRateDorite)
                    .collect(Collectors.toList());

            RaceEvent raceEvent = new RaceEvent(0L, numeEvent);
            raceEvent.setLanes(lanes);
            raceEvent.setDucks_final(participatingDucks);
            raceEvent.setMessage("Eveniment creat de " + loggedInUser.getUsername());

            eventService.add(raceEvent);

            closeWindow();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText("Eveniment Creat!");
            alert.setContentText("Evenimentul '" + numeEvent + "' a fost salvat cu succes.");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Eroare la salvare: " + e.getMessage());
        }
    }

    @FXML
    public void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) eventNameField.getScene().getWindow();
        stage.close();
    }
}