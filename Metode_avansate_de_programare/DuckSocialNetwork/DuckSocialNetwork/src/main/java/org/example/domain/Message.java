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

}
