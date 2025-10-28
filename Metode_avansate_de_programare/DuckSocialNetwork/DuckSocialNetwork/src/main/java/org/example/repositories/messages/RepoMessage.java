package org.example.repositories.messages;

import org.example.domain.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Repo message.
 */
public class RepoMessage implements RepoCrud<Message>{

    private List<Message> messages;

    /**
     * Instantiates a new Repo message.
     */
    public RepoMessage() {
        this.messages = new ArrayList<>();
    }

    @Override
    public void save(Message entity) {
        if (this.findById(entity.getId()) == null) {
            this.messages.add(entity);
        }
        else throw new RuntimeException("Message already exists");
    }

    @Override
    public void update(Message entity) {
        if (this.findById(entity.getId()) != null) {
            Message message = this.findById(entity.getId());
            message.setContent(entity.getContent());
            message.setTimestamp(entity.getTimestamp());
            message.setReceiver(entity.getReceiver());
            message.setSender(entity.getSender());
            this.messages.set(this.messages.indexOf(message), message);
        }
        else {
            throw new RuntimeException("Message does not exist");
        }
    }

    @Override
    public void delete(Message entity) {

        if (this.findById(entity.getId()) == null) {
            throw new RuntimeException("Message does not exist");
        }

        this.messages.remove(entity);
    }

    @Override
    public Message findById(Long id) {
        return this.messages.stream().filter(
                m->
                        m.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Iterable<Message> findAll() {
        return this.messages;
    }

}
