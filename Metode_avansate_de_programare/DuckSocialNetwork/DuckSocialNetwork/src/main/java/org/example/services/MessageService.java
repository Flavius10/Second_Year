package org.example.services;

import org.example.domain.messaging.Message;
import org.example.repositories.repo_db.RepoDBMessage;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.util.Optional;

public class MessageService {

    private RepoDBMessage repoDBMessage;

    public MessageService(RepoDBMessage repoDBMessage) {
        this.repoDBMessage = repoDBMessage;
    }

    public void sendMessage(Message message) {
        repoDBMessage.save(message);
    }

    public void deleteMessage(Long id) {
        Optional<Message> deleted = repoDBMessage.delete(id);
        if (deleted.isEmpty()) {
            throw new RuntimeException("Mesajul nu a fost gasit!");
        }
    }

    public void updateMessage(Message message) {
        Optional<Message> updated = repoDBMessage.update(message);
        if (updated.isEmpty()) {
            throw new RuntimeException("Nu s-a putut actualiza mesajul.");
        }
    }

    public Iterable<Message> findAllMessages() {
        return repoDBMessage.findAll();
    }

    public Message findMessageById(Long id) {
        return repoDBMessage.findOne(id)
                .orElseThrow(() -> new RuntimeException("Message not found!"));
    }

    public Page<Message> findAllOnPage(Pageable pageable) {
        return repoDBMessage.findAllOnPage(pageable);
    }

    public Iterable<Message> findMessagesToUser(Long userId) {
        return repoDBMessage.findAllForUser(userId);
    }
}