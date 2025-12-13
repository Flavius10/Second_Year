package org.example.domain.events;

import org.example.domain.Entity;
import org.example.domain.User;

import java.util.ArrayList;
import java.util.List;

public class Event extends Entity<Long> {

    private List<User> subscribers;

    public Event() {
        this.subscribers = new ArrayList<>();
    }

    public void subscribe(User user){
        if (!subscribers.contains(user))
        {
            subscribers.add(user);
            System.out.println("User " + user.getUsername() + " subscribed to event");
        }
    }

    public void unsubscribe(User user){
        boolean removed = subscribers.remove(user);

        if (removed) {
            System.out.println("User " + user.getUsername() + " unsubscribed from event");
        } else {
            System.out.println("User " + user.getUsername() + " was not subscribed to event");
        }
    }

    public void notifySubscribers(String message){


    }

    public List<User> getSubscribers() {
        return this.subscribers;
    }

}
