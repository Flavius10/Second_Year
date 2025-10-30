package org.example.exceptions;

/**
 * The type User already exists.
 */
public class UserAlreadyExists extends RuntimeException{

    /**
     * Instantiates a new User already exists.
     *
     * @param message the message
     */
    public UserAlreadyExists(String message) {
        super(message + " already exists!");
    }

}
