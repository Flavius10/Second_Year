package org.example.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Message extends Entity<Long> {
    private User sender;
    private List<User> receivers;
    private String message;
    private LocalDateTime data;

    public Message(Long id, User sender, List<User> receivers, String message, LocalDateTime data) {
        setId(id);
        this.sender = sender;
        this.receivers = receivers;
        this.message = message;
        this.data = data;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public List<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender +
                ", receivers=" + receivers +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message1 = (Message) o;
        return Objects.equals(sender, message1.sender) &&
                Objects.equals(receivers, message1.receivers) &&
                Objects.equals(message, message1.message) &&
                Objects.equals(data, message1.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receivers, message, data);
    }
}