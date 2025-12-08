package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.Main;
import org.example.domain.Message;
import org.example.domain.ReplyMessage;
import org.example.domain.User;
import org.example.network.NetworkService;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.services.MessageService;
import org.example.services.PersoanaService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MessageController {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;

    @FXML private TextArea resultArea;
    @FXML private TextArea messageArea;
    @FXML private Button sendMessageBtn;
    @FXML private TextField receiverField;
    @FXML private TextField senderField;
    @FXML private TextField idMessageField;

    @FXML
    public void initialize() {

        setupAllEventHandlers();

    }

    public void setServices(DuckService duckService, PersoanaService persoanaService, FriendshipService friendshipService,
                            NetworkService networkService, MessageService messageService) {

        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.messageService = messageService;
    }

    private void setupAllEventHandlers() {

        sendMessageBtn.setOnAction(e -> {
            try {
                String senderName = senderField.getText();
                String receiverName = receiverField.getText();
                String text = messageArea.getText();
                String idReplyText = idMessageField.getText();

                if (senderName.isEmpty() || receiverName.isEmpty() || text.isEmpty()) {
                    sendMessage("Toate campurile (expeditor, destinatar, mesaj) sunt obligatorii!", "Eroare", "Date incomplete");
                    return;
                }

                User sender = findUserByUsername(senderName);
                User receiver = findUserByUsername(receiverName);

                if (sender == null) {
                    sendMessage("Expeditorul '" + senderName + "' nu exista!", "Eroare", "User Inexistent");
                    return;
                }
                if (receiver == null) {
                    sendMessage("Destinatarul '" + receiverName + "' nu exista!", "Eroare", "User Inexistent");
                    return;
                }

                List<User> receivers = Collections.singletonList(receiver);
                Message messageToSend;

                if (idReplyText != null && !idReplyText.trim().isEmpty() && !idReplyText.equals("0")) {
                    try {
                        Long replyId = Long.parseLong(idReplyText);
                        Message parentMessage = messageService.findMessageById(replyId);

                        if (parentMessage != null) {
                            messageToSend = new ReplyMessage(null, sender, receivers, text, LocalDateTime.now(), parentMessage);
                        } else {
                            sendMessage("Mesajul la care incerci sa raspunzi (ID " + replyId + ") nu exista!", "Eroare", "ID Invalid");
                            return;
                        }
                    } catch (NumberFormatException nfe) {
                        sendMessage("ID-ul mesajului de raspuns trebuie sa fie un numar!", "Eroare", "Format Invalid");
                        return;
                    } catch (Exception ex) {
                        sendMessage("Mesajul parinte nu a fost gasit!", "Eroare", "ID Invalid");
                        return;
                    }
                } else {
                    messageToSend = new Message(null, sender, receivers, text, LocalDateTime.now());
                }

                messageService.sendMessage(messageToSend);

                messageArea.clear();
                idMessageField.setText("0");

                StringBuilder displayMessage = new StringBuilder();
                displayMessage.append(senderName).append(" ---> ").append(receiverName);
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
        });
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

}
