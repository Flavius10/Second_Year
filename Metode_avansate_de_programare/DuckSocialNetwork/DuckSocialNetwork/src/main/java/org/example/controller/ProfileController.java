package org.example.controller;

import org.example.domain.User;

public class ProfileController {

    private User loggedInUser;

    private void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
