package org.example.domain;

public abstract class User {

    private Long id;
    private String username;
    private String email;
    private String password;

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
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


    /// Metode generale
    public void login(){
        System.out.println("Login succesful!");
    }

    public void logout(){
        System.out.println("Logout succesful!");
    }

    public void sendMessage(){
        System.out.println("Message sent succesful!");
    }

    public void receiveMessage(){
        System.out.println("Message received succesful!");
    }

}
