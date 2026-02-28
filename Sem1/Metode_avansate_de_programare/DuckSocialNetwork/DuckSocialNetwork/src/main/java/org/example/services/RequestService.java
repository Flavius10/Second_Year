package org.example.services;

import org.example.domain.Observable;
import org.example.domain.Observer;
import org.example.domain.Signal;
import org.example.domain.friendship.Request;
import org.example.repositories.repo_db.RepoDBRequest;

import java.util.ArrayList;
import java.util.List;

public class RequestService implements Observable {
    private RepoDBRequest repo;
    private final List<Observer> observers = new ArrayList<>();

    public RequestService(RepoDBRequest repo) {
        this.repo = repo;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Signal signal) {
        for (Observer observer : new ArrayList<>(observers)) {
            observer.update(signal);
        }
    }

    public void addRequest(Request request) {
        repo.save(request);
        notifyObservers(new Signal("REQUEST_ADDED", null, null, request));
    }

    public void approveRequest(Request request) {
        repo.delete(request.getId());
        notifyObservers(new Signal("REQUEST_APPROVED", null, null, request));
    }

    public void rejectRequest(Request request) {
        repo.delete(request.getId());
        notifyObservers(new Signal("REQUEST_REJECTED", null, null, request));
    }

    public Iterable<Request> findByUsername(String username) {
        return repo.findByUsernameRequest(username);
    }
}