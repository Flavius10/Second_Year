package org.example.services;

import org.example.domain.events.Event;
import org.example.repositories.RepoEvent;

public class EventService {

    private final RepoEvent repoEvent;

    public EventService(RepoEvent repoEvent) {
        this.repoEvent = repoEvent;
    }

    public void add(Event event) {
        try{
            repoEvent.add_event(event);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
