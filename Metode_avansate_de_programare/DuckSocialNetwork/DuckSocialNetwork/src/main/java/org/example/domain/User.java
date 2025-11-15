package org.example.domain;

import java.util.List;
import java.util.Random;

/**
 * The type User.
 */
public abstract class User extends Entity<Long> implements Observer {

    private String username;
    private String email;
    private String password;

    /**
     * Instantiates a new User.
     *
     * @param id       the id
     * @param username the username
     * @param email    the email
     * @param password the password
     */
    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Get username string.
     *
     * @return the string
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Get email string.
     *
     * @return the string
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Get password string.
     *
     * @return the string
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Set username.
     *
     * @param username the username
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Set email.
     *
     * @param email the email
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Set password.
     *
     * @param password the password
     */
    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public void update(String message){

        List<String> listaDeNume = List.of(message.split(" "));
        Random random = new Random();

        int randomInt = random.nextInt(listaDeNume.size());

        System.out.println("Eu tin cu " + listaDeNume.get(randomInt) + "! Haide " + listaDeNume.get(randomInt) + "!");

    }

}
