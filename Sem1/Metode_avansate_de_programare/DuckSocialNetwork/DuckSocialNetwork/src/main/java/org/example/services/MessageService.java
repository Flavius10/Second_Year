package org.example.services;

import org.example.domain.Observable;
import org.example.domain.Observer;
import org.example.domain.Signal;
import org.example.domain.User;
import org.example.domain.messaging.Message;
import org.example.repositories.repo_db.RepoDBMessage;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageService implements Observable {

    private RepoDBMessage repoDBMessage;
    private List<Observer> observers = new ArrayList<>();

    public MessageService(RepoDBMessage repoDBMessage) {
        this.repoDBMessage = repoDBMessage;
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
        observers.forEach(observer -> observer.update(signal));
    }


    public void sendMessage(Message message) {
        repoDBMessage.save(message);

        Signal signal = new Signal("MESSAGE_NEW", message.getSender(), message.getReceivers(), message);
        notifyObservers(signal);
    }

    public void notifyTyping(User sender, List<User> receivers) {
        Signal signal = new Signal("TYPING", sender, receivers, null);

        notifyObservers(signal);
    }

    public void deleteMessage(Long id) {
        Optional<Message> deleted = repoDBMessage.delete(id);
        if (deleted.isEmpty()) {
            throw new RuntimeException("Mesajul nu a fost găsit!");
        }

        notifyObservers(new Signal("MESSAGE_DELETED"));
    }

    public void updateMessage(Message message) {
        Optional<Message> updated = repoDBMessage.update(message);
        if (updated.isEmpty()) {
            throw new RuntimeException("Nu s-a putut actualiza mesajul.");
        }

        notifyObservers(new Signal("MESSAGE_UPDATED"));
    }

    public Iterable<Message> findAllMessages() {
        return repoDBMessage.findAll();
    }

    public Message findMessageById(Long id) {
        return repoDBMessage.findOne(id)
                .orElseThrow(() -> new RuntimeException("Mesajul nu a fost găsit!"));
    }

    public Iterable<Message> findMessagesToUser(Long userId) {
        return repoDBMessage.findAllForUser(userId);
    }

    public Page<Message> findAllOnPage(Pageable pageable) {
        return repoDBMessage.findAllOnPage(pageable);
    }
}