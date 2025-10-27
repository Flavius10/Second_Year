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
    private RepoFilePersoana persoanaRepo;
    private RepoFileDuck duckRepo;

    public Ui(AuthService authService, Menu menu, MessageService messageService) {
        this.authService = authService;
        this.menu = menu;
        this.messageService = messageService;
    }

    public void menuBeforeSignUp() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            this.menu.showMenuBeforeSignUp();
            choice = this.getUserChoice(3);

            switch (choice) {
                case 1:
                    signUp();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    finalMessage();
                    return;
                default:
                    System.out.println("Opțiune invalidă!");
            }
        }
    }

    public void menuAfterSignUp(User loggedInUser) {
        int choice;
        while (true) {
            this.menu.showMenuAfterSignUp();
            choice = this.getUserChoice(4);

            switch (choice) {
                case 1:
                    System.out.println("Această opțiune este deja folosită pentru login.");
                    break;
                case 2:
                    logout(loggedInUser);
                    return;
                case 3:
                    System.out.println("Funcționalitatea de trimis mesaje va fi implementată aici.");
                    break;
                case 4:
                    System.out.println("Alte opțiuni...");
                    break;
                default:
                    System.out.println("Opțiune invalidă!");
            }
        }
    }

    public int getUserChoice(int max) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.print("Alege o opțiune (1-" + max + "): ");
            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Trebuie să introduci un număr valid!");
                continue;
            }

            if (choice >= 1 && choice <= max) {
                break;
            } else {
                System.out.println("Opțiune invalidă! Introduceți un număr între 1 și " + max + ".");
            }
        }

        return choice;
    }

    public void signUp() {
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
            RepoUser repo = new RepoFileDuck();
            repo.save(duck, "ducks.txt");
            System.out.println("Rata creata cu succes: " + duck.getUsername());

            menuAfterSignUp(duck);

        } else if (userType.equals("persoana")) {
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

            menuAfterSignUp(persoana);

        } else {
            System.out.println("Tip de utilizator invalid. Scrie 'rata' sau 'persoana'.");
        }
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti numele de utilizator: ");
        String username = scanner.nextLine();
        System.out.print("Introduceti parola: ");
        String password = scanner.nextLine();

        try {
            User loggedInUser = authService.loginAndReturnUser(username, password);
            System.out.println("Autentificare reușită!");
            menuAfterSignUp(loggedInUser);
        } catch (RuntimeException e) {
            System.out.println("Eroare la autentificare: " + e.getMessage());
        }
    }

    public void logout(User user) {
        authService.logout(user);
        System.out.println("V-ati deconectat cu succes!");
        menuBeforeSignUp();
    }

    private void finalMessage() {
        System.out.println("Programul se va inchide...");
        try {
            Thread.sleep(1000);
            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
