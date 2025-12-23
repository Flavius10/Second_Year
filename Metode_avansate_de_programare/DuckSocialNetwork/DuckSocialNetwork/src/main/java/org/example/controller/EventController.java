package org.example.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.domain.*;
import org.example.domain.Signal;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.domain.events.RaceEvent;
import org.example.network.NetworkService;
import org.example.services.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EventController implements Observer {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;
    private RequestService requestService;
    private EventService eventService;

    private User loggedInUser;

    @FXML private Button createEventButton;
    @FXML private ListView<RaceEvent> activeEventsListView;
    @FXML private VBox detailsBox;
    @FXML private Label selectedEventNameLabel;
    @FXML private Label statusLabel;
    @FXML private Button joinRaceButton;
    @FXML private Button startRaceButton;

    private ObservableList<RaceEvent> eventsModel = FXCollections.observableArrayList();

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
        updateUIBasedOnRole();
    }

    public void setServices(DuckService duckService, PersoanaService persoanaService,
                            FriendshipService friendshipService, NetworkService networkService,
                            MessageService messageService, RequestService requestService,
                            EventService eventService) {
        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.messageService = messageService;
        this.requestService = requestService;
        this.eventService = eventService;

        if (this.eventService != null)
            this.eventService.addObserver(this);
    }

    private void updateUIBasedOnRole() {
        if (createEventButton != null) { createEventButton.setVisible(false); createEventButton.setManaged(false); }
        if (joinRaceButton != null) { joinRaceButton.setVisible(false); joinRaceButton.setManaged(false); }
        if (startRaceButton != null) { startRaceButton.setVisible(false); startRaceButton.setManaged(false); }

        if (loggedInUser instanceof Persoana) {
            if (createEventButton != null) {
                createEventButton.setVisible(true);
                createEventButton.setManaged(true);
            }
        } else if (loggedInUser instanceof Duck) {
            if (joinRaceButton != null) {
                joinRaceButton.setVisible(true);
                joinRaceButton.setManaged(true);
            }
        }
        loadEvents();
    }

    private void loadEvents() {
        if (eventService == null) return;

        Iterable<RaceEvent> events = eventService.getAllEvents();
        List<RaceEvent> list = StreamSupport.stream(events.spliterator(), false).collect(Collectors.toList());
        eventsModel.setAll(list);
        activeEventsListView.setItems(eventsModel);
    }

    @FXML
    public void initialize() {
        activeEventsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                showEventDetails(newVal);
            } else {
                if (detailsBox != null) detailsBox.setVisible(false);
            }
        });

        activeEventsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(RaceEvent item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    int participanti = (item.getDucks_final() != null) ? item.getDucks_final().size() : 0;
                    setText(item.getName() + " [Participanți: " + participanti + "]");
                }
            }
        });
    }

    private void showEventDetails(RaceEvent event) {
        if (detailsBox != null) detailsBox.setVisible(true);
        if (selectedEventNameLabel != null) selectedEventNameLabel.setText(event.getName());

        int currentParticipants = event.getDucks_final() != null ? event.getDucks_final().size() : 0;

        int maxParticipants = (event.getLanes() != null && !event.getLanes().isEmpty()) ? event.getLanes().size() : 3;

        if (statusLabel != null) {
            statusLabel.setText("Status: " + currentParticipants + " / " + maxParticipants + " rațe înscrise.");
        }

        if (loggedInUser instanceof Persoana) {
            boolean isReady = currentParticipants >= maxParticipants;
            if (startRaceButton != null) {
                startRaceButton.setVisible(isReady);
                startRaceButton.setManaged(isReady);
                if (!isReady) {
                    startRaceButton.setVisible(true);
                    startRaceButton.setManaged(true);
                    startRaceButton.setDisable(true);
                    startRaceButton.setText("Așteptare înscrieri...");
                } else {
                    startRaceButton.setDisable(false);
                    startRaceButton.setText("🚀 START CURSĂ");
                }
            }
        }
    }

    @FXML
    public void handleCreateEventAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add-event-view.fxml"));
            Parent root = loader.load();

            AddEventController ctrl = loader.getController();
            ctrl.setServices(duckService, eventService, loggedInUser);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            loadEvents();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Eroare", "Nu s-a putut deschide fereastra: " + e.getMessage());
        }
    }

    @FXML
    public void handleJoinRace() {
        RaceEvent selected = activeEventsListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Atenție", "Selectează un eveniment din listă!");
            return;
        }

        if (!(loggedInUser instanceof SwimmingDuck)) {
            showAlert(Alert.AlertType.ERROR, "Eroare", "Doar rațele înotătoare pot participa la curse!");
            return;
        }

        SwimmingDuck currentDuck = (SwimmingDuck) loggedInUser;

        try {
            if (selected.getDucks_final() == null) {
                selected.setDucks_final(new ArrayList<>());
            }

            boolean alreadyJoined = selected.getDucks_final().stream()
                    .anyMatch(d -> d.getId().equals(currentDuck.getId()));

            if (alreadyJoined) {
                showAlert(Alert.AlertType.INFORMATION, "Info", "Ești deja înscris la acest eveniment!");
                return;
            }

            int maxSlots = (selected.getLanes() != null && !selected.getLanes().isEmpty()) ? selected.getLanes().size() : 3;
            if (selected.getDucks_final().size() >= maxSlots) {
                showAlert(Alert.AlertType.WARNING, "Ne pare rău", "Nu mai sunt locuri disponibile la această cursă!");
                return;
            }

            selected.getDucks_final().add(currentDuck);

            eventService.updateEvent(selected);

            showAlert(Alert.AlertType.INFORMATION, "Succes", "Te-ai înscris cu succes la cursă!");

            loadEvents();
            showEventDetails(selected);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Eroare Critică", "Nu s-a putut realiza înscrierea: " + e.getMessage());
        }
    }

    @FXML
    public void handleStartRace() {
        RaceEvent selected = activeEventsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Pornire cursă pentru evenimentul ID: " + selected.getId());
            showAlert(Alert.AlertType.INFORMATION, "Start", "Cursa a început! (Urmează fereastra de simulare)");
        }
    }

    @FXML
    public void sendBackToMainPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

        MainController mainCtrl = loader.getController();
        mainCtrl.setLoggedInUser(this.loggedInUser);
        mainCtrl.setEvent(this.eventService);
        mainCtrl.setServices(duckService, persoanaService, friendshipService, networkService, messageService, requestService);

        stage.show();
    }

    @Override
    public void update(Signal signal) {
        Platform.runLater(() -> {
            RaceEvent previousSelection = activeEventsListView.getSelectionModel().getSelectedItem();
            Long selectedId = (previousSelection != null) ? previousSelection.getId() : null;

            loadEvents();

            if (selectedId != null) {
                for (RaceEvent freshEvent : activeEventsListView.getItems()) {
                    if (freshEvent.getId().equals(selectedId)) {
                        activeEventsListView.getSelectionModel().select(freshEvent);

                        showEventDetails(freshEvent);
                        break;
                    }
                }
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}