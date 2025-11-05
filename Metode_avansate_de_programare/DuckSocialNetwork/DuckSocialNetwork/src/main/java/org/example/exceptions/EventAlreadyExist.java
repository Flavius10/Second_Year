package org.example.exceptions;

public class EventAlreadyExist extends RuntimeException {
    public EventAlreadyExist(String message) {
        super(message);
    }
}
