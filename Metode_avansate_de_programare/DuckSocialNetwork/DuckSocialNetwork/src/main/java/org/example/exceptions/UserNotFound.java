package org.example.exceptions;

/**
 * The type User not found.
 */
public class UserNotFound extends RuntimeException {
    /**
     * Instantiates a new User not found.
     *
     * @param message the message
     */
    public UserNotFound(String message) {
        super(message);
    }
}
