package org.example.controller;

import javafx.fxml.FXML;
import org.example.domain.Persoana;
import org.example.domain.Profile;
import org.example.domain.User;
import org.example.network.NetworkService;
import org.example.services.*;

public class ProfileController {


    private DuckService duckService;
    private PersoanaService persoanaService;
    private FriendshipService friendshipService;
    private NetworkService networkService;
    private MessageService messageService;
    private RequestService requestService;
    private User loggedInUser;

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
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

    public Profile buildProfile(){
        String username = loggedInUser.getUsername();
        String email = loggedInUser.getEmail();
        String profiletype = loggedInUser instanceof Persoana ? "Persoana" : "Duck";
        String profilePicture = "";


        Profile profile = new Profile(username, email, profilePicture, profiletype);

        return profile;
    }
}
