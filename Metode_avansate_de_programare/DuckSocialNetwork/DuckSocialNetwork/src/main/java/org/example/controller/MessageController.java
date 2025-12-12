package org.example.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.domain.Message;
import org.example.domain.Persoana;
import org.example.domain.ReplyMessage;
import org.example.domain.User;
import org.example.domain.ducks.Duck;
import org.example.network.NetworkService;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.services.MessageService;
import org.example.services.PersoanaService;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageController {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;
    private User loggedInUser;

    @FXML private TextArea resultArea;
    @FXML private TextArea messageArea;
    @FXML private Button sendMessageBtn;
    @FXML private Button sendToAllBtn;
    @FXML private TextField receiverField;
    @FXML private TextField senderField;
    @FXML private TextField idMessageField;
    @FXML private ComboBox<String> namesComboBox;

    @FXML
    public void initialize() {
        setupAllEventHandlers();
        setupMessageRefresh();
    }

    public void setServices(DuckService duckService, PersoanaService persoanaService, FriendshipService friendshipService,
                            NetworkService networkService, MessageService messageService) {
        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.messageService = messageService;

        completeNamesComboBox();
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    private void setupAllEventHandlers() {

        sendMessageBtn.setOnAction(e -> handleSendMessage(false));
        sendToAllBtn.setOnAction(e -> handleSendMessage(true));
    }

    public void completeNamesComboBox() {
        List<String> usernames = new ArrayList<>();

        Iterable<Duck> allDucks = duckService.findAllDucks();
        Iterable<Persoana> allPersons = persoanaService.findAllPersons();

        allDucks.forEach(duck -> usernames.add(duck.getUsername()));
        allPersons.forEach(person -> usernames.add(person.getUsername()));

        Collections.sort(usernames);
        namesComboBox.getItems().clear();
        namesComboBox.getItems().addAll(usernames);

        namesComboBox.getSelectionModel().selectFirst();
    }

    private void handleSendMessage(boolean sendToAll) {
        try {
            String senderName = loggedInUser.getUsername();
            String text = messageArea.getText();
            String idReplyText = idMessageField.getText();

            if (senderName.isEmpty() || text.isEmpty()) {
                sendMessage("Expeditorul si mesajul sunt obligatorii!", "Eroare", "Date incomplete");
                return;
            }

            String receiverName = namesComboBox.getValue();
            if (!sendToAll && receiverName.isEmpty()) {
                sendMessage("Destinatarul este obligatoriu!", "Eroare", "Date incomplete");
                return;
            }

            User sender = findUserByUsername(senderName);
            if (sender == null) {
                sendMessage("Expeditorul '" + senderName + "' nu exista!", "Eroare", "User Inexistent");
                return;
            }

            List<User> receivers = new ArrayList<>();

            if (sendToAll) {
                Iterable<Duck> allDucks = duckService.findAllDucks();
                Iterable<Persoana> allPersons = persoanaService.findAllPersons();

                allDucks.forEach(receivers::add);
                allPersons.forEach(receivers::add);

                receivers.removeIf(u -> u.getId().equals(sender.getId()));

                if (receivers.isEmpty()) {
                    sendMessage("Nu exista alti utilizatori in retea!", "Info", "Nimeni de notificat");
                    return;
                }
            } else {
                User receiver = findUserByUsername(receiverName);
                if (receiver == null) {
                    sendMessage("Destinatarul '" + receiverName + "' nu exista!", "Eroare", "User Inexistent");
                    return;
                }
                receivers.add(receiver);
            }

            Message messageToSend;
            Message parentMessage = null;

            if (idReplyText != null && !idReplyText.trim().isEmpty() && !idReplyText.equals("0")) {
                try {
                    Long replyId = Long.parseLong(idReplyText);
                    parentMessage = messageService.findMessageById(replyId);
                    if (parentMessage == null) {
                        sendMessage("Mesajul parinte nu exista!", "Eroare", "ID Invalid");
                        return;
                    }
                } catch (NumberFormatException nfe) {
                    sendMessage("ID-ul mesajului de raspuns trebuie sa fie numar!", "Eroare", "Format Invalid");
                    return;
                }
            }

            if (parentMessage != null) {
                messageToSend = new ReplyMessage(null, sender, receivers, text, LocalDateTime.now(), parentMessage);
            } else {
                messageToSend = new Message(null, sender, receivers, text, LocalDateTime.now());
            }

            messageService.sendMessage(messageToSend);

            messageArea.clear();
            idMessageField.setText("0");

            StringBuilder displayMessage = new StringBuilder();
            if (sendToAll) {
                displayMessage.append(senderName).append(" ---> [TOATA LUMEA]");
            } else {
                displayMessage.append(senderName).append(" ---> ").append(receiverName);
            }

            displayMessage.append(" (ID: ").append(messageToSend.getId()).append(")");

            if (messageToSend instanceof ReplyMessage) {
                displayMessage.append(" [Reply to: ").append(idReplyText).append("]");
            }

            displayMessage.append(":\n").append(text).append("\n\n");
            this.resultArea.appendText(displayMessage.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
            sendMessage("Eroare interna: " + ex.getMessage(), "Eroare", "Eroare la trimitere");
        }
    }

    public void setupMessageRefresh() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event ->{
            refreshMessages(null);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private User findUserByUsername(String username) {
        User user = duckService.findByUsernameDuck(username);
        if (user == null) {
            user = (User) persoanaService.findByUsernamePerson(username);
        }
        return user;
    }

    private void sendMessage(String message, String title, String header ){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (title.equals("Eroare")) {
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void sendBackToMainPage(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        MainController mainController = loader.getController();
        mainController.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    public void refreshMessages(ActionEvent event) {

        if (loggedInUser == null || messageService == null) {
            this.resultArea.setText("Eroare: Datele (User sau Service) nu au fost transmise corect!");
            return;
        }

        try {
            resultArea.clear();

            Iterable<Message> messages = messageService.findMessagesToUser(loggedInUser.getId());

            if (!messages.iterator().hasNext()) {
                this.resultArea.appendText("Nu ai primit niciun mesaj încă.");
                return;
            }

            messages.forEach(m -> {
                String expeditor = (m.getSender() != null) ? m.getSender().getUsername() : "Necunoscut";
                String formatat = "De la: " + expeditor + " ID: " + m.getId() + "\n" +
                        "Mesaj: " + m.getMessage() + "\n" +
                        "Data: " + m.getData() + "\n" +
                        "-----------------------------------\n";
                this.resultArea.appendText(formatat);
            });

        } catch (Exception e) {
            e.printStackTrace();
            this.resultArea.appendText("Eroare la încărcare.");
        }
    }
}