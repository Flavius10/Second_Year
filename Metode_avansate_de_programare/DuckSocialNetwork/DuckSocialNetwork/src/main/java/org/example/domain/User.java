package org.example.domain;

import org.example.repositories.RepoMessage;

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


    /// Mesajul care a fost catre Userul user si cu mesajul message
    public void sendMessage(User user, String message){
        if (this.isLoggedIn){
            try{

                System.out.println("Message received succesful!");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else
            System.out.println("You must be logged in to receive a message!");
    }

    public void receiveMessage(User user, String message){

        if (this.isLoggedIn){
            try{
                System.out.println("Message received succesful!");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else
            System.out.println("You must be logged in to receive a message!");
    }

}
