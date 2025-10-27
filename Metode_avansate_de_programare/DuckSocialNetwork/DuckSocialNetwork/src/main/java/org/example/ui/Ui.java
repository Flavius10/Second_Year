package org.example.ui;

import org.example.services.AuthService;
import org.example.services.MessageService;

import java.util.Scanner;

public class Ui {

    private AuthService authService;
    private Menu menu;
    private MessageService messageService;

    public Ui(AuthService authService, Menu menu, MessageService messageService) {
        this.authService = authService;
        this.menu = menu;
        this.messageService = messageService;
    }

    public void start(){
        this.menu.showMenu();

        int choice = this.getUserChoice();
        while (choice != 5) {
            execute(choice);
            choice = this.getUserChoice();
        }

        System.out.println("Asteptare pentru a inchide programul...");
        try {
            Thread.sleep(1000);
            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.print("Alege o optiune (1-5): ");
            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Trebuie sa introduci un numar valid!");
                continue;
            }

            if (choice >= 1 && choice <= 5) {
                break;
            } else {
                System.out.println("Optiune invalida! Introduceti un numar intre 1 si 5.");
            }
        }

        return choice;
    }

    public void execute(int choice){

        switch (choice)
        {            case 1:
                // login
                break;
            case 2:
                // logout
                break;
            case 3:
                // send message
                break;
            case 4:
                // get messages
                break;
            default:
                System.out.println("Optiune invalida!");
        }
    }

}
