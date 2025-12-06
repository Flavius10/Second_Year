package org.example.domain;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message{

    private Message message;

    public ReplyMessage(Long id, User from, List<User> to, String message,
                        LocalDateTime data, Message messageObj) {
        super(id, from, to, message, data);

        this.message = messageObj;
    }

    public void setReplyMessage(Message message) {
        this.message = message;
    }

    public Message getReplyMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "Reply to [" + this.message.getSender().getUsername() + "]: " +
                super.getMessage() + " (" + super.getData() + ")";
    }

}
