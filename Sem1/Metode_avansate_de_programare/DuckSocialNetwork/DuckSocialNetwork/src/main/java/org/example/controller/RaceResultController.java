package org.example.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.domain.events.RaceEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class RaceResultController {

    @FXML private Label raceTitleLabel;
    @FXML private VBox tracksContainer;
    @FXML private Label statusLabel;
    @FXML private Button startAnimationButton;

    private RaceEvent raceEvent;
    private ExecutorService executorService;

    public void setRaceEvent(RaceEvent event) {
        this.raceEvent = event;
        raceTitleLabel.setText("Cursa: " + event.getName());
        initializeTracks();
    }

    private void initializeTracks() {
        tracksContainer.getChildren().clear();
        if (raceEvent.getDucks_final() == null) return;

        for (Duck duck : raceEvent.getDucks_final()) {
            HBox lane = new HBox(10);
            lane.setAlignment(Pos.CENTER_LEFT);
            lane.setStyle("-fx-border-color: #ccc; -fx-padding: 10; -fx-background-color: #f0f8ff;");

            double speed = (duck instanceof SwimmingDuck) ? ((SwimmingDuck) duck).getViteza() : 0;
            Label nameLabel = new Label(duck.getUsername() + " (Speed: " + speed + ")");
            nameLabel.setPrefWidth(150);

            ProgressBar progressBar = new ProgressBar(0);
            progressBar.setPrefWidth(500);
            progressBar.setId("progress_" + duck.getId());

            lane.getChildren().addAll(nameLabel, progressBar);
            tracksContainer.getChildren().add(lane);
        }
    }

    @FXML
    public void handleStartAnimation() {
        startAnimationButton.setDisable(true);
        statusLabel.setText("Status: Cursa a început! 🏁");

        int participantCount = raceEvent.getDucks_final().size();
        executorService = Executors.newFixedThreadPool(participantCount);

        List<CompletableFuture<String>> futures = new ArrayList<>();

        for (Duck duck : raceEvent.getDucks_final()) {
            ProgressBar bar = (ProgressBar) tracksContainer.lookup("#progress_" + duck.getId());

            CompletableFuture<String> duckTask = CompletableFuture.supplyAsync(() -> {
                return runDuckRace((SwimmingDuck) duck, bar);
            }, executorService);

            futures.add(duckTask);
        }

        CompletableFuture<Void> allDucksFinished = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0])
        );

        allDucksFinished.thenRunAsync(() -> {
            Platform.runLater(() -> {
                statusLabel.setText("Status: Cursa finalizată! Se trimit notificările...");
                startAnimationButton.setText("Cursă Încheiată");
            });

            sendNotificationsAsync();
            executorService.shutdown();
        });
    }

    private String runDuckRace(SwimmingDuck duck, ProgressBar bar) {
        double progress = 0;
        Random random = new Random();
        double speed = duck.getViteza();

        while (progress < 1.0) {
            try {
                long sleepTime = (long) (100 + (1000 / (speed > 0 ? speed : 1)) + random.nextInt(50));
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            double advance = 0.05 + (speed * 0.001);
            progress += advance;

            if (progress > 1.0) progress = 1.0;

            final double currentProgress = progress;
            Platform.runLater(() -> bar.setProgress(currentProgress));
        }

        return duck.getUsername();
    }

    private void sendNotificationsAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(() -> statusLabel.setText("Status: Rezultate salvate si notificari trimise!"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}