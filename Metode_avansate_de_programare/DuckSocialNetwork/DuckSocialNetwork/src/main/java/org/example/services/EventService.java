package org.example.services;

import org.example.domain.Observable;
import org.example.domain.Observer;
import org.example.domain.Signal;
import org.example.domain.events.RaceEvent;
import org.example.repositories.repo_db.RepoDBEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EventService implements Observable {

    private final RepoDBEvent repoDBEvent;
    private List<Observer> observers = new ArrayList<>();

    public EventService(RepoDBEvent repoDBEvent) {
        this.repoDBEvent = repoDBEvent;
    }

    public void add(RaceEvent event) {
        try {
            Optional<RaceEvent> saved = repoDBEvent.save(event);
            if (saved.isPresent()) {
                throw new RuntimeException("Evenimentul deja există!");
            }
            System.out.println("Evenimentul '" + event.getName() + "' a fost adăugat cu succes!");
        } catch (Exception e) {
            throw new RuntimeException("Eroare la salvarea evenimentului: " + e.getMessage());
        }

        notifyObservers(new Signal("ADDED", null, null, event));
    }

    public Iterable<RaceEvent> getAllEvents() {
        return repoDBEvent.findAll();
    }

    public void updateEvent(RaceEvent event) {
        Optional<RaceEvent> updated = repoDBEvent.update(event);
        if (updated.isEmpty()) {
            throw new RuntimeException("Nu s-a putut actualiza evenimentul (poate ID-ul e greșit).");
        }

        notifyObservers(new Signal("UPDATE", null, null, event));
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(Signal signal) {
        for (Observer observer : observers) {
            observer.update(signal);
        }
    }
}
