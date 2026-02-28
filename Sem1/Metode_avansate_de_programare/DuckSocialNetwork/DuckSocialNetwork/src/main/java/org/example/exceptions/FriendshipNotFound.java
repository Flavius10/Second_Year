package org.example.exceptions;

public class FriendshipNotFound extends RuntimeException {
    public FriendshipNotFound(String message) {
        super(message + "Not found");
    }
}
