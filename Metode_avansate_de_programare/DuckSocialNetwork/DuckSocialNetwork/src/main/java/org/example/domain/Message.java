package org.example.domain;

import java.time.LocalDateTime;

public class Message {

    private Long id;
    private User sender;
    private User receiver;
    private String content;
    private LocalDateTime timestamp;

    public Message(Long id, User sender, User receiver,
                   String content, LocalDateTime timestamp) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }

    /// Getters
    public Long getId(){
        return this.id;
    }

    public User getSender(){
        return this.sender;
    }

    public User getReceiver(){
        return this.receiver;
    }

    public String getContent(){
        return this.content;
    }

    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /// Setters
    public void setId(Long id){
        this.id = id;
    }

    public void setSender(User sender){
        this.sender = sender;
    }

    public void setReceiver(User receiver){
        this.receiver = receiver;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
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
