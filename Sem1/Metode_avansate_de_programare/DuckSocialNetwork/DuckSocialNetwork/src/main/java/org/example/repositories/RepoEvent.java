package org.example.repositories;

import org.example.domain.events.Event;
import org.example.domain.events.RaceEvent;

import java.util.ArrayList;
import java.util.List;

public class RepoEvent {
    
    private List<Event> events;
    
    public RepoEvent() {
        this.events = new ArrayList<>();
    }

    public void add_event(Event event){


        if (events.contains(event))
            throw new RuntimeException("Event already exists!");
        else if (event == null)
            throw new RuntimeException("Event is null!");


        events.add(event);
    }
    
    
    
}
