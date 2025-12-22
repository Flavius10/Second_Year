package org.example.services;

import org.example.domain.events.RaceEvent;
import org.example.repositories.repo_db.RepoDBEvent;

import java.util.Optional;

public class EventService {

    private final RepoDBEvent repoDBEvent;

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
    }

    public Iterable<RaceEvent> getAllEvents() {
        return repoDBEvent.findAll();
    }

    public void updateEvent(RaceEvent event) {
        Optional<RaceEvent> updated = repoDBEvent.update(event);
        if (updated.isEmpty()) {
            throw new RuntimeException("Nu s-a putut actualiza evenimentul (poate ID-ul e greșit).");
        }
    }
}
