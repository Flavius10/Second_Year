package org.example.domain;

public abstract class User {

    private Long id;
    private String username;
    private String email;
    private String password;
    private boolean isLoggedIn;

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isLoggedIn = false;
    }

    /// Getters
    public Long getId(){
        return this.id;
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public boolean getIsLoggedIn(){
        return this.isLoggedIn;
    }

    /// Setters
    public void setId(Long id){
        this.id = id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setIsLoggedIn(boolean isLoggedIn){
        this.isLoggedIn = isLoggedIn;
    }


    /// Metode generale
    public void login(String username, String password){
        if (username.equals(this.username) && password.equals(this.password)){
            this.isLoggedIn = true;
            System.out.println("Login succesful!");
        } else {
            System.out.println("Login failed!");
        }
    }

    public void logout(){
        this.isLoggedIn = false;
        System.out.println("Logout succesful!");
    }

    /// Mesajul care a fost catre Userul user si cu mesajul message
    public void sendMessage(User user, String message){
        System.out.println("Message sent succesful!");
    }

    public void receiveMessage(){
        System.out.println("Message received succesful!");
    }

}
