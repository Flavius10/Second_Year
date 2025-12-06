package org.example.domain;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Message.
 */
public class Message extends Entity<Long> {

    private Long id;
    private User from;
    private List<User> to;
    private String message;
    private LocalDateTime data;

    public Message(Long id, User from, List<User> to,
                   String message, LocalDateTime data) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
    }

    /**
     * Getters @return  the long
     */
    public Long getId(){
        return this.id;
    }

    /**
     * Get sender user.
     *
     * @return the user
     */
    public User getSender(){
        return this.from;
    }

    /**
     * Get receiver user.
     *
     * @return the user
     */
    public List<User> getReceivers(){
        return this.to;
    }

    /**
     * Get content string.
     *
     * @return the string
     */
    public String getMessage(){
        return this.message;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public LocalDateTime getData() {
        return this.data;
    }

    public void setSender(User from){
        this.from = from;
    }

    public void setReceiver(List<User> to){
        this.to = to;
    }

    public void setContent(String message){
        this.message = message;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" +  from+
                ", receiver=" +  to.stream().map(User::getUsername).toList() +
                ", content='" + message + '\'' +
                ", timestamp=" + data +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Message other = (Message) obj;
        return other.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }

}
