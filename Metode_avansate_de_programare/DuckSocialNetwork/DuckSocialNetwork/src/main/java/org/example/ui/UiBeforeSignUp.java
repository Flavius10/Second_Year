package org.example.ui;

import org.example.domain.*;
import org.example.domain.ducks.Card;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.FlyingDuck;
import org.example.domain.ducks.SwimmingDuck;
import org.example.exceptions.UserAlreadyExists;
import org.example.services.AuthService;
import org.example.services.PersoanaService;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.network.NetworkService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * The type Ui before sign up.
 */
public class UiBeforeSignUp extends UiAbstract {

    private final PersoanaService persoanaService;
    private final DuckService duckService;
    private final FriendshipService friendshipService;
    private final NetworkService networkService;
    private User loggedInUser;

    /**
     * Instantiates a new Ui before sign up.
     *
     * @param authService       the auth service
     * @param menu              the menu
     * @param persoanaService   the persoana service
     * @param duckService       the duck service
     * @param friendshipService the friendship service
     * @param networkService    the network service
     */
    public UiBeforeSignUp(AuthService authService, Menu menu,
                          PersoanaService persoanaService, DuckService duckService,
                          FriendshipService friendshipService, NetworkService networkService) {
        super(authService, menu);
        this.persoanaService = persoanaService;
        this.duckService = duckService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            showMenu();
            choice = getUserChoice(3);

            switch (choice) {
                case 1 -> signUp();
                case 2 -> login();
                case 3 -> exitApp();
                default -> System.out.println("Optiune invalida!");
            }
        }
    }

    @Override
    public void showMenu() {
        this.menu.showMenuBeforeSignUp();
    }

    private void exitApp() {
        System.out.println("Programul se va inchide...");
        try {
            Thread.sleep(1000);
            System.exit(0);
        } catch (InterruptedException ignored) {}
    }

    /**
     * Sign up.
     */
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
            List<Duck> membri = Collections.emptyList();
            Card card = numeCard.isEmpty() ? null : new Card(10L, numeCard, membri );

            Duck duck;
            if (tip == TypeDuck.FLYING)
                duck = new SwimmingDuck(id, username, email, password, tip, viteza, rezistenta, card);
            else
                duck = new FlyingDuck(id, username, email, password, tip, viteza, rezistenta, card);

            try {
                authService.signUp(duck, "ducks.txt");
            } catch (UserAlreadyExists e) {
                System.out.println("Eroare la înregistrare: " + e.getMessage());
                return;
            }

            System.out.println("Rata creata cu succes!");
            loggedInUser = duck;
            new UiAfterSignUp(authService, menu, persoanaService, duckService, friendshipService, networkService, loggedInUser).execute();

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
            try {
                authService.signUp(persoana, "persoane.txt");
            } catch (UserAlreadyExists e) {
                System.out.println("Eroare la inregistrare: " + e.getMessage());
                return;
            }

            System.out.println("Persoana creata cu succes!");
            loggedInUser = persoana;
            new UiAfterSignUp(authService, menu, persoanaService, duckService, friendshipService, networkService, loggedInUser).execute();
        } else {
            System.out.println("Tip invalid. Scrie 'rata' sau 'persoana'.");
        }
    }

    /**
     * Login.
     */
    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti numele de utilizator: ");
        String username = scanner.nextLine();
        System.out.print("Introduceti parola: ");
        String password = scanner.nextLine();

        try {
            User user = authService.loginAndReturnUser(username, password);
            System.out.println("Autentificare reusita!");
            loggedInUser = user;
            new UiAfterSignUp(authService, menu, persoanaService, duckService, friendshipService, networkService, user).execute();
        } catch (RuntimeException e) {
            System.out.println("Eroare la autentificare: " + e.getMessage());
        }
    }

    /**
     * Get logged in user user.
     *
     * @return the user
     */
    public User getLoggedInUser(){
        return this.loggedInUser;
    }
}

