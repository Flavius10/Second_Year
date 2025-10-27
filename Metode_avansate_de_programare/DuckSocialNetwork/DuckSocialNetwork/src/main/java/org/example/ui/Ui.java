package org.example.ui;

import org.example.domain.*;
import org.example.repositories.RepoFileDuck;
import org.example.repositories.RepoFilePersoana;
import org.example.repositories.RepoUser;
import org.example.services.AuthService;
import org.example.services.MessageService;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public void menuAfterSignUp(){
        this.menu.showMenuAfterSignUp();

        int choice = this.getUserChoice(4);
        while (choice != 4) {
            executeAfterSignUp(choice);
            choice = this.getUserChoice(4);
        }

        finalMessage();

    }

    public void menuBeforeSignUp(){
        this.menu.showMenuBeforeSignUp();

        int choice = this.getUserChoice(3);
        while (choice != 3) {
            executeBeforeSignUp(choice);
            choice = this.getUserChoice(3);
        }

        finalMessage();
    }

    public int getUserChoice(int max) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.print("Alege o optiune (1-" + max + "): ");
            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Trebuie sa introduci un numar valid!");
                continue;
            }

            if (choice >= 1 && choice <= max) {
                break;
            } else {
                System.out.println("Optiune invalida! Introduceti un numar intre 1 si 5.");
            }
        }

        return choice;
    }

    public void executeAfterSignUp(int choice){

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
            default:
                System.out.println("Optiune invalida!");
        }
    }

    public void executeBeforeSignUp(int choice){
        switch (choice){
            case 1:
                signUp();
                break;
            case 2:
                break;
            default:
                System.out.println("Optiune invalida!");
        }
    }

    public void signUp() {

        this.menu.showMenuBeforeSignUp();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti numele de utilizator: ");
        String username = scanner.nextLine();
        System.out.print("Introduceti adresa de email: ");
        String email = scanner.nextLine();
        System.out.print("Introduceti parola: ");
        String password = scanner.nextLine();

        System.out.print("Doriti sa fiti 'rata' sau 'persoana'? ");
        String userType = scanner.nextLine().trim().toLowerCase();

        Long id = System.currentTimeMillis();

        if (userType.equals("rata")) {
            System.out.print("Introduceti tipul ratei (FLYING, SWIMMING, FLYING_AND_SWIMMING): ");
            TypeDuck tip = TypeDuck.valueOf(scanner.nextLine().trim().toUpperCase());

            System.out.print("Introduceti viteza ratei: ");
            double viteza = Double.parseDouble(scanner.nextLine());

            System.out.print("Introduceti rezistenta ratei: ");
            double rezistenta = Double.parseDouble(scanner.nextLine());

            System.out.print("Introduceti numele cardului (sau lasati gol): ");
            String numeCard = scanner.nextLine();
            Card card = numeCard.isEmpty() ? null : new Card();

            Duck duck = new Duck(id, username, email, password, tip, viteza, rezistenta, card);

            // Salvare în fișier
            RepoUser repo = new RepoFileDuck();
            repo.save(duck, "ducks.txt");
            System.out.println("Rata creata cu succes: " + duck.getUsername());

        } else if (userType.equals("persoana")) {
            // === DATE SPECIFICE PERSOANEI ===
            System.out.print("Introduceti numele: ");
            String nume = scanner.nextLine();
            System.out.print("Introduceti prenumele: ");
            String prenume = scanner.nextLine();
            System.out.print("Introduceti ocupatia: ");
            String ocupatie = scanner.nextLine();
            System.out.print("Introduceti data nasterii (yyyy-MM-dd): ");
            LocalDate dataNastere = LocalDate.parse(scanner.nextLine());

            Persoana persoana = new Persoana(id, username, email, password, nume, prenume, ocupatie, dataNastere);

            RepoUser repo = new RepoFilePersoana();
            repo.save(persoana, "persoane.txt");
            System.out.println("Persoana creata cu succes: " + persoana.getUsername());

        } else {
            System.out.println("Tip de utilizator invalid. Scrie 'rata' sau 'persoana'.");
        }
    }


    private void finalMessage(){
        System.out.println("Asteptare pentru a inchide programul...");
        try {
            Thread.sleep(1000);
            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
