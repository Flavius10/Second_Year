package org.example.ui;

import org.example.services.AuthService;
import org.example.services.MessageService;

public class Ui {

    private AuthService authService;
    private Menu menu;
    private MessageService messageService;

    public Ui(AuthService authService, Menu menu, MessageService messageService) {
        this.authService = authService;
        this.menu = menu;
        this.messageService = messageService;
    }



}
