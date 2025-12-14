package org.example.controller;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.domain.Observer;
import org.example.domain.Signal;
import org.example.domain.User;
import org.example.domain.ducks.Duck;
import org.example.domain.messaging.Message;
import org.example.domain.messaging.ReplyMessage;
import org.example.domain.Persoana;
import org.example.network.NetworkService;
import org.example.services.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MessageController implements Observer {

    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;
    private RequestService requestService;

    private User loggedInUser;

    @FXML private TextArea resultArea;
    @FXML private TextArea messageArea;
    @FXML private Button sendMessageBtn;
    @FXML private Button sendToAllBtn;
    @FXML private TextField idMessageField;
    @FXML private ComboBox<String> namesComboBox;
    @FXML private Label typingLabel;

    private PauseTransition typingTimer;


    @FXML
    public void initialize() {

        typingTimer = new PauseTransition(Duration.seconds(1.5));
        typingTimer.setOnFinished(e -> {
            if (typingLabel != null) typingLabel.setText("");
        });

        if (typingLabel != null) typingLabel.setText("");

        setupButtons();

        setupTypingDetector();
    }


    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public void setServices(DuckService duckService, PersoanaService persoanaService, FriendshipService friendshipService,
                            NetworkService networkService, MessageService messageService,
                            RequestService requestService) {
        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.messageService = messageService;
        this.requestService = requestService;

        this.messageService.addObserver(this);

        completeNamesComboBox();

        if (loggedInUser != null) {
            refreshMessages(null);
        }
    }

    private void setupButtons() {
        sendMessageBtn.setOnAction(e -> handleSendMessage(false));
        sendToAllBtn.setOnAction(e -> handleSendMessage(true));
    }


    @Override
    public void update(Signal signal) {

        Platform.runLater(() -> {
            switch (signal.getType()) {
                case "MESSAGE_NEW":
                    refreshMessages(null);
                    break;
                case "TYPING":
                    handleTypingSignal(signal);
                    break;
            }
        });
    }

    private void setupTypingDetector() {
        messageArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (loggedInUser != null && !newValue.isEmpty()) {

                String receiverName = namesComboBox.getValue();
                User receiver = findUserByUsername(receiverName);
                List<User> receivers = (receiver != null) ? List.of(receiver) : new ArrayList<>();

                messageService.notifyTyping(loggedInUser, receivers);
            }
        });
    }


    private void handleTypingSignal(Signal signal) {
        User sender = signal.getFrom();

        if (sender == null || loggedInUser == null || sender.getId().equals(loggedInUser.getId())) {
            return;
        }

        boolean isForMe = signal.getTo() == null || signal.getTo().isEmpty() ||
                signal.getTo().stream().anyMatch(u -> u.getId().equals(loggedInUser.getId()));

        if (isForMe && typingLabel != null) {
            typingLabel.setText(sender.getUsername() + " scrie...");
            typingTimer.playFromStart();
        }
    }

    @FXML
    public void refreshMessages(ActionEvent event) {
        if (loggedInUser == null || messageService == null) {
            return;
        }

        try {
            Iterable<Message> messages = messageService.findMessagesToUser(loggedInUser.getId());
            List<Message> messageList = new ArrayList<>();
            messages.forEach(messageList::add);

            messageList.sort(Comparator.comparing(Message::getData));

            StringBuilder sb = new StringBuilder();
            if (messageList.isEmpty()) {
                sb.append("Nu ai niciun mesaj.");
            } else {
                for (Message m : messageList) {
                    String senderName = (m.getSender() != null) ? m.getSender().getUsername() : "System";
                    String time = m.getData().getHour() + ":" + m.getData().getMinute();

                    sb.append("[").append(time).append("] ");
                    sb.append(senderName).append(" (ID: ").append(m.getId()).append(")");

                    if (m instanceof ReplyMessage) {
                        Message parent = ((ReplyMessage) m).getReplyMessage();
                        if (parent != null) {
                            sb.append(" -> Reply la #").append(parent.getId());
                        }
                    }
                    sb.append(":\n").append(m.getMessage()).append("\n\n");
                }
            }

            resultArea.setText(sb.toString());
            resultArea.positionCaret(resultArea.getText().length());

        } catch (Exception e) {
            e.printStackTrace();
            resultArea.setText("Eroare la incarcarea mesajelor.");
        }
    }
    private void handleSendMessage(boolean sendToAll) {
        try {
            String text = messageArea.getText();
            String idReplyText = idMessageField.getText();

            if (text.isEmpty()) {
                showAlert("Eroare", "Mesajul nu poate fi gol!");
                return;
            }

            String receiverName = namesComboBox.getValue();
            User sender = loggedInUser;
            List<User> receivers = new ArrayList<>();

            if (sendToAll) {
                duckService.findAllDucks().forEach(receivers::add);
                persoanaService.findAllPersons().forEach(receivers::add);
                receivers.removeIf(u -> u.getId().equals(sender.getId()));
            } else {
                User receiver = findUserByUsername(receiverName);
                if (receiver == null) {
                    showAlert("Eroare", "Destinatar invalid!");
                    return;
                }
                receivers.add(receiver);
            }

            Message messageToSend;
            Message parentMessage = null;

            if (idReplyText != null && !idReplyText.equals("0") && !idReplyText.isEmpty()) {
                try {
                    parentMessage = messageService.findMessageById(Long.parseLong(idReplyText));
                } catch (Exception _) {
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

        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Eroare Critica", ex.getMessage());
        }
    }

    public void completeNamesComboBox() {
        List<String> usernames = new ArrayList<>();
        duckService.findAllDucks().forEach(d -> usernames.add(d.getUsername()));
        persoanaService.findAllPersons().forEach(p -> usernames.add(p.getUsername()));
        Collections.sort(usernames);
        namesComboBox.getItems().clear();
        namesComboBox.getItems().addAll(usernames);
        if (!namesComboBox.getItems().isEmpty()) {
            namesComboBox.getSelectionModel().selectFirst();
        }
    }

    private User findUserByUsername(String username) {
        if (username == null) return null;
        User user = duckService.findByUsernameDuck(username);
        if (user == null) {
            user = (User) persoanaService.findByUsernamePerson(username);
        }
        return user;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void sendBackToMainPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

        MainController mainCtrl = loader.getController();
        mainCtrl.setServices(duckService, persoanaService,
                friendshipService, networkService, messageService, requestService);

        stage.show();
    }
}