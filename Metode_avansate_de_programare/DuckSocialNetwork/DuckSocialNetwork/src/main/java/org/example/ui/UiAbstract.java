package org.example.ui;

import org.example.services.*;

import java.util.Scanner;

public abstract class UiAbstract {

    protected final AuthService authService;
    protected final Menu menu;


    protected final Scanner scanner = new Scanner(System.in);

    public UiAbstract(AuthService authService,
                      Menu menu) {
        this.authService = authService;
        this.menu = menu;

    }

    /**
     * Citeste o optiune de la utilizator intre 1 și max (inclusiv).
     */
    protected int getUserChoice(int max) {
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
                System.out.println("Optiune invalida! Introdu un numar intre 1 si " + max + ".");
            }
        }

        return choice;
    }

    public abstract void execute();
    public abstract void showMenu();
}
