package grup.controllers;

import grup.domain.*;
import grup.domain.SpecialOffer;
import grup.services.ServiceHotel;
import grup.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ClientController implements Observer {

    @FXML private Label lblClientName;
    @FXML private ComboBox<Location> comboLocations;
    @FXML private TableView<Hotel> tableHotels;
    @FXML private DatePicker dateStart;
    @FXML private DatePicker dateEnd;
    @FXML private TableView<SpecialOffer> tableOffers;

    private ServiceHotel service;
    private Client currentClient;
    private ObservableList<SpecialOffer> modelOffers = FXCollections.observableArrayList();
    private ObservableList<Hotel> modelHotels = FXCollections.observableArrayList();

    public void setService(ServiceHotel service, Client client){
        this.service = service;
        this.currentClient = client;

        lblClientName.setText("Client: " + client.getName() + " (Hobby: " + client.getHobby() + ")");
        comboLocations.setItems(FXCollections.observableArrayList(service.getAllLocations()));
    }

    @FXML
    public void initialize() {
        tableHotels.setItems(modelHotels);
        tableOffers.setItems(modelOffers);


        tableHotels.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            modelOffers.clear();
        });
    }

    @FXML
    public void handleLocationSelect() {
        Location loc = comboLocations.getValue();
        if (loc != null) {
            modelHotels.setAll(service.getHotelByLocation(loc.getLocationId()));
        }
    }

    @Override
    public void update(Hobby hobbyClientRezervare, String hotelName) {

        if (this.currentClient.getHobby() == hobbyClientRezervare) {
            showMessage("Hobby Alert!",
                    "Inca un utilizator care iubeste " + hobbyClientRezervare +
                            " a facut o rezervare la hotelul " + hotelName + "!");
        }
    }

    @FXML
    public void handleSearchOffers() {
        Hotel selectedHotel = tableHotels.getSelectionModel().getSelectedItem();
        LocalDate start = dateStart.getValue();
        LocalDate end = dateEnd.getValue();

        if (selectedHotel != null && start != null && end != null) {
            if (end.isBefore(start)) {
                showMessage("Eroare", "Data de final trebuie sa fie dupa data de start!");
                return;
            }
            // Apelam service-ul filtrat
            List<SpecialOffer> offers = service.getOffersByHotelInPeriod(selectedHotel.getHotelId(), start, end);
            modelOffers.setAll(offers);

            if (offers.isEmpty()) {
                showMessage("Info", "Nu exista oferte speciale in aceasta perioada, dar puteti rezerva la pret intreg.");
            }
        } else {
            showMessage("Eroare", "Selectati un hotel si perioada!");
        }
    }

    @FXML
    public void handleReservation() {
        Hotel hotel = tableHotels.getSelectionModel().getSelectedItem();
        LocalDate start = dateStart.getValue();
        LocalDate end = dateEnd.getValue();

        if (hotel != null && start != null && end != null) {
            // Calculam numarul de nopti automat
            long noNights = ChronoUnit.DAYS.between(start, end);
            if (noNights < 1) noNights = 1;

            // Apelam service-ul care face salvarea si notifica Observerii
            service.makeReservation(currentClient, hotel, start, (int) noNights);

            showMessage("Succes", "Rezervare efectuata cu succes la " + hotel.getHotelName());
        } else {
            showMessage("Eroare", "Selectati hotelul si perioada!");
        }
    }

    private void showMessage(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
