package org.example.services;

import org.example.domain.Message;
import org.example.domain.User;
import org.example.repositories.messages.RepoMessage;

import java.time.LocalDateTime;

/**
 * The type Message service.
 */
public class MessageService {

    private RepoMessage repoMessage;
    private AuthService authService;
    private Long messageCount = 0L;

    /**
     * Instantiates a new Message service.
     *
     * @param repoMessage the repo message
     * @param authService the auth service
     */
    public MessageService(RepoMessage repoMessage, AuthService authService) {
        this.repoMessage = repoMessage;
        this.authService = authService;
    }

    /**
     * Send message.
     *
     * @param sender   the sender
     * @param receiver the receiver
     * @param content  the content
     */
    public void sendMessage(User sender, User receiver, String content){
        if (this.authService.isLoggedIn(sender)){
            try{
                Message message = new Message(messageCount++, sender, receiver, content, LocalDateTime.now());
                this.repoMessage.save(message);
                this.receiveMessage(sender, receiver, message);
                System.out.println("Message received succesful!");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else
            System.out.println("You must be logged in to receive a message!");
    }

    /**
     * Receive message.
     *
     * @param sender   the sender
     * @param receiver the receiver
     * @param message  the message
     */
    public void receiveMessage(User sender, User receiver, Message message){

        if (authService.isLoggedIn(receiver)) {
            System.out.println(receiver.getUsername() + " received a new message from " + sender.getUsername());
        } else {
            System.out.println("Message saved for " + receiver.getUsername() + ". They are offline.");
        }
    }




}
