package org.example.exceptions;

public class FriendshipAlreadyExists extends RuntimeException {
    public FriendshipAlreadyExists(String message) {
        super(message);
    }
}
