package org.example.domain;

import java.time.LocalDateTime;

/**
 * The type Message.
 */
public class Message {

    private Long id;
    private User sender;
    private User receiver;
    private String content;
    private LocalDateTime timestamp;

    /**
     * Instantiates a new Message.
     *
     * @param id        the id
     * @param sender    the sender
     * @param receiver  the receiver
     * @param content   the content
     * @param timestamp the timestamp
     */
    public Message(Long id, User sender, User receiver,
                   String content, LocalDateTime timestamp) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = timestamp;
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
        return this.sender;
    }

    /**
     * Get receiver user.
     *
     * @return the user
     */
    public User getReceiver(){
        return this.receiver;
    }

    /**
     * Get content string.
     *
     * @return the string
     */
    public String getContent(){
        return this.content;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Setters @param id the id
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * Set sender.
     *
     * @param sender the sender
     */
    public void setSender(User sender){
        this.sender = sender;
    }

    /**
     * Set receiver.
     *
     * @param receiver the receiver
     */
    public void setReceiver(User receiver){
        this.receiver = receiver;
    }

    /**
     * Set content.
     *
     * @param content the content
     */
    public void setContent(String content){
        this.content = content;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
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
