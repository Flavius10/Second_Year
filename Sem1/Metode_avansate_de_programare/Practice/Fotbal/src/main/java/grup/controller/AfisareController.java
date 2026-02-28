package grup.controller;

import grup.domain.Eveniment;
import grup.domain.Meci;
import grup.service.ServiceEveniment;
import grup.utils.Observer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class AfisareController implements Observer {

    // --- ELEMENTE UI ---
    @FXML private Label numeEchipaGazda;
    @FXML private Label numeEchipaVenita;

    // Etichetele pentru SCOR (acestea se vor actualiza singure)
    @FXML private Label scorEchipaGazda;
    @FXML private Label scorEchipaVenita;

    // Listele pentru evenimente
    @FXML private ListView<String> listGazda;
    @FXML private ListView<String> listOaspete;

    // --- DATE ---
    private ServiceEveniment serviceEveniment;
    private Meci meciCurent;

    // Modelele pentru liste (ObservableList leaga datele de UI)
    private ObservableList<String> eventsGazda = FXCollections.observableArrayList();
    private ObservableList<String> eventsOaspete = FXCollections.observableArrayList();

    /**
     * Metoda principala de initializare primita din MainController
     */
    public void setService(ServiceEveniment serviceEveniment, Meci meci) {
        this.serviceEveniment = serviceEveniment;
        this.meciCurent = meci;

        // 1. Ne ABONAM la service (Observer Pattern)
        // Cand adminul adauga un gol, service-ul ne va notifica si se va apela update()
        this.serviceEveniment.addObserver(this);

        // 2. Setam datele statice (Numele echipelor)
        // (Scorul il lasam sa fie calculat de functia refresh)
        numeEchipaGazda.setText(meci.getNumeEchipaGazda());
        numeEchipaVenita.setText(meci.getNumeEchipaOaspeti());

        // 3. Legam listele de UI
        listGazda.setItems(eventsGazda);
        listOaspete.setItems(eventsOaspete);

        // 4. Incarcam datele initiale
        refreshData();
    }

    /**
     * Metoda apelata automat de Service cand se intampla ceva (Observer)
     */
    @Override
    public void update() {
        // Rulam pe thread-ul de UI pentru siguranta, desi Platform.runLater e deja in Service
        Platform.runLater(() -> {
            refreshData();
        });
    }

    /**
     * Logica de recalculare a listelor si a SCORULUI
     */
    private void refreshData() {
        // 1. Luam evenimentele
        List<Eveniment> toateEvenimentele = serviceEveniment.findAll();

        // 2. Curatam listele
        eventsGazda.clear();
        eventsOaspete.clear();

        int calculScorGazda = 0;
        int calculScorOaspete = 0;

        for (Eveniment e : toateEvenimentele) {

            // Formatul textului: "Gol - Rața 10"
            String descriere = e.getTipeEveniment() + " - Rața " + e.getRataId();

            // --- AICI E MODIFICAREA CRITICA ---
            // Verificam daca in baza de date scrie fix cuvantul "Gazda"
            if ("Gazda".equals(e.getNumeEchipa())) {

                // Il punem in lista din STANGA (a gazdelor)
                eventsGazda.add(descriere);

                // Daca e Gol, crestem scorul gazdelor
                if ("Gol".equals(e.getTipeEveniment())) {
                    calculScorGazda++;
                }
            }
            // Verificam daca scrie fix cuvantul "Oaspete"
            else if ("Oaspete".equals(e.getNumeEchipa())) {

                // Il punem in lista din DREAPTA (a oaspetilor)
                eventsOaspete.add(descriere);

                // Daca e Gol, crestem scorul oaspetilor
                if ("Gol".equals(e.getTipeEveniment())) {
                    calculScorOaspete++;
                }
            }
        }

        // 3. Afisam scorul calculat
        scorEchipaGazda.setText(String.valueOf(calculScorGazda));
        scorEchipaVenita.setText(String.valueOf(calculScorOaspete));
    }
}