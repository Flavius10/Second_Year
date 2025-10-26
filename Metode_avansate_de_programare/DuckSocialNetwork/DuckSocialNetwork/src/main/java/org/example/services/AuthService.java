package org.example.services;

import org.example.domain.User;

public class AuthService {

    private User user;

    public AuthService(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void login(String username, String password){
        if (username.equals(this.user.getUsername()) && password.equals(this.user.getPassword())){
            this.user.setIsLoggedIn(true);
            System.out.println("Login succesful!");
        } else {
            System.out.println("Login failed!");
        }
    }

    public void logout(){
        this.user.setIsLoggedIn(false);
        System.out.println("Logout succesful!");
    }

}
