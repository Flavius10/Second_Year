package org.example.domain;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message {
    private Message replyMessage;

    public ReplyMessage(Long id, User sender, List<User> receivers, String message, LocalDateTime data, Message replyMessage) {
        super(id, sender, receivers, message, data);
        this.replyMessage = replyMessage;
    }

    public Message getReplyMessage() {
        return replyMessage;
    }

    public void setReplyMessage(Message replyMessage) {
        this.replyMessage = replyMessage;
    }
}