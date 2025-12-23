package org.example.controller;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import org.example.domain.ducks.Lane;
import org.example.domain.ducks.SwimmingDuck;
import org.example.domain.events.RaceEvaluator;
import org.example.domain.events.RaceEvent;

import java.util.ArrayList;
import java.util.List;

public class RaceResultController {

    @FXML private Label raceTitleLabel;
    @FXML private VBox tracksContainer;
    @FXML private Button startAnimationButton;
    @FXML private Label statusLabel;

    private RaceEvent raceEvent;
    private List<TranslateTransition> allTransitions = new ArrayList<>();

    private static final double TIME_SCALE = 0.1;

    public void setRaceEvent(RaceEvent event) {
        this.raceEvent = event;
        if (raceEvent != null) {
            raceTitleLabel.setText("Cursa: " + raceEvent.getName());
            setupRaceTracks();
        }
    }

    private void setupRaceTracks() {
        tracksContainer.getChildren().clear();
        allTransitions.clear();

        List<SwimmingDuck> participants = raceEvent.getDucks_final();
        List<Lane> lanes = raceEvent.getLanes();

        if (participants == null) participants = new ArrayList<>();
        if (lanes == null) lanes = new ArrayList<>();

        System.out.println("DEBUG: Lanes: " + lanes.size() + ", Rate: " + participants.size());

        if (participants.isEmpty()) {
            statusLabel.setText("Eroare: Nu sunt rațe înscrise în această cursă!");
            startAnimationButton.setDisable(true);
            return;
        }

        RaceEvaluator evaluator = new RaceEvaluator(0.0);

        for (int i = 0; i < lanes.size(); i++) {
            if (i >= participants.size()) break;

            Lane lane = lanes.get(i);
            SwimmingDuck duck = participants.get(i);

            HBox laneRow = new HBox(10);
            laneRow.setAlignment(Pos.CENTER_LEFT);
            laneRow.setPrefHeight(60);

            Label laneInfo = new Label("Lane " + lane.getId() + "\n(" + lane.getLength() + "m)");
            laneInfo.setMinWidth(80);
            laneInfo.setStyle("-fx-font-weight: bold;");

            Pane trackPane = new Pane();
            trackPane.setPrefHeight(50);
            trackPane.setMinWidth(600);
            trackPane.setMaxWidth(600);
            trackPane.setStyle("-fx-border-color: black; -fx-background-color: #87CEEB;");


            Line finishLine = new Line(580, 0, 580, 50);
            finishLine.setStroke(Color.RED);
            finishLine.setStrokeWidth(3);

            StackPane duckVisual = createDuckVisual(duck);
            duckVisual.setLayoutX(0);
            duckVisual.setLayoutY(5);

            trackPane.getChildren().addAll(finishLine, duckVisual);

            laneRow.getChildren().addAll(laneInfo, trackPane);
            tracksContainer.getChildren().add(laneRow);

            double realTimeSeconds = evaluator.computeRace(duck, lane);

            TranslateTransition transition = new TranslateTransition();
            transition.setNode(duckVisual);
            transition.setToX(580 - 40);
            transition.setDuration(Duration.seconds(realTimeSeconds * TIME_SCALE));

            transition.setOnFinished(e -> {
                duckVisual.setOpacity(0.5);
                System.out.println(duck.getUsername() + " a terminat!");
            });

            allTransitions.add(transition);
        }

        statusLabel.setText("Start pregătit! Rațe la start: " + participants.size());
    }

    private StackPane createDuckVisual(SwimmingDuck duck) {
        StackPane pane = new StackPane();

        pane.setPrefSize(40, 40);
        pane.setMaxSize(50, 50);

        Circle body = new Circle(30);
        body.setFill(Color.YELLOW);
        body.setStroke(Color.BLACK);

        String nameText = duck.getUsername().length() > 3 ? duck.getUsername().substring(0, 4) : duck.getUsername();
        Label nameLabel = new Label(nameText);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");

        pane.getChildren().addAll(body, nameLabel);
        return pane;
    }

    @FXML
    public void handleStartAnimation() {
        if (allTransitions.isEmpty()) return;

        statusLabel.setText("Cursa a început! Vizionare plăcută...");
        startAnimationButton.setDisable(true);

        ParallelTransition raceAnimation = new ParallelTransition();
        raceAnimation.getChildren().addAll(allTransitions);

        raceAnimation.setOnFinished(e -> {
            statusLabel.setText("Cursa s-a încheiat!");
            startAnimationButton.setDisable(false);
            Platform.runLater(() -> {
                showAlert(Alert.AlertType.INFORMATION, "Final", "Toate rațele au ajuns la finish!");
            });
        });

        raceAnimation.play();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}