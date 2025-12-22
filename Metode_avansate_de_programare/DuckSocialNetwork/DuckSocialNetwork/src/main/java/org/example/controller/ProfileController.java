package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.domain.Persoana;
import org.example.domain.Profile;
import org.example.domain.User;
import org.example.domain.friendship.Friendship;
import org.example.network.NetworkService;
import org.example.services.*;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileController {


    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;
    private RequestService requestService;
    private User loggedInUser;

    @FXML private ImageView profileImageView;
    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;
    @FXML private Label typeLabel;
    @FXML private Label friendsCountLabel;

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
        updateUI();
    }

    @FXML
    public void initialize() {
    }

    public void setServices(DuckService duckService, PersoanaService persoanaService, FriendshipService friendshipService, NetworkService networkService, MessageService messageService, RequestService requestService) {
        this.duckService = duckService;
        this.persoanaService = persoanaService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.messageService = messageService;
        this.requestService = requestService;
    }

    private int countFriends(){

        int cnt = 0;
        List<Friendship> friendships =  (List) this.friendshipService.findAllFriendships();
        String username = this.loggedInUser.getUsername();

        cnt = friendships.stream()
                .filter(f -> f.getFirst_friend_username().equals(username) ||
                        f.getSecond_friend_username().equals(username))
                .toArray().length;

        return cnt;

    }

    public Profile buildProfile(){
        String username = loggedInUser.getUsername();
        String email = loggedInUser.getEmail();
        String profileType = loggedInUser instanceof Persoana ? "Persoana" : "Duck";
        String profilePicture;

        List<String> images_ducks = new ArrayList<>();
        images_ducks.add("/images/ducks/drink_water.png");
        images_ducks.add("/images/ducks/duck.png");
        images_ducks.add("/images/ducks/img.png");

        List<String> images_persons = new ArrayList<>();
        images_persons.add("/images/persons/img.png");
        images_persons.add("/images/persons/img_1.png");
        images_persons.add("/images/persons/img_2.png");

        if (profileType.equals("Duck"))
            profilePicture = images_ducks.get((int)(Math.random() * images_ducks.size()));
        else
            profilePicture = images_persons.get((int)(Math.random() * images_persons.size()));

        return new Profile(profilePicture, username, email, profileType);
    }

    private void updateUI(){
        if (loggedInUser == null) return;

        Profile currentProfile = buildProfile();

        usernameLabel.setText(currentProfile.getUsername());
        emailLabel.setText(currentProfile.getEmail());

        String type = currentProfile.getProfileType();
        typeLabel.setText(type.toUpperCase());
        if ("Duck".equals(type)) {
            typeLabel.setStyle("-fx-background-color: #FFF9C4; -fx-text-fill: #FBC02D; -fx-background-radius: 15; -fx-padding: 5 15;");
        } else {
            typeLabel.setStyle("-fx-background-color: #E3F2FD; -fx-text-fill: #1976D2; -fx-background-radius: 15; -fx-padding: 5 15;");
        }

        try {
            String imagePath = currentProfile.getProfilePicture();

            System.out.println("Caut imaginea: " + imagePath);
            var inputStream = getClass().getResourceAsStream(imagePath);

            if (inputStream == null) {
                System.out.println("EROARE: Nu am găsit fișierul! Verifică folderul 'target/classes/images'");
            } else {
                Image image = new Image(inputStream);
                profileImageView.setImage(image);

                double radius = 60;
                Circle clip = new Circle(radius, radius, radius);
                profileImageView.setClip(clip);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        int count = countFriends();
        friendsCountLabel.setText(String.valueOf(count));
    }

    @FXML
    public void backToMainView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

        MainController mainCtrl = loader.getController();
        mainCtrl.setLoggedInUser(this.loggedInUser);
        mainCtrl.setServices(duckService, persoanaService, friendshipService, networkService, messageService, requestService);

        stage.show();
    }
}
