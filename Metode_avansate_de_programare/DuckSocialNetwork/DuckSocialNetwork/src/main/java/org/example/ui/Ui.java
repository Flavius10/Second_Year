package org.example.ui;

import org.example.domain.*;
import org.example.exceptions.UserAlreadyExists;
import org.example.exceptions.UserNotFound;
import org.example.services.*;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * The type Ui.
 */
public class Ui {

    private AuthService authService;
    private Menu menu;

    private PersoanaService persoanaService;
    private DuckService duckService;
    private FriendshipService friendshipService;

    private User loggedInUser;

    /**
     * Instantiates a new Ui.
     *
     * @param menu            the menu
     * @param persoanaService the persoana service
     * @param duckService     the duck service
     */
    public Ui(Menu menu, PersoanaService persoanaService, DuckService duckService,
              FriendshipService friendshipService) {
        this.menu = menu;
        this.persoanaService =  persoanaService;
        this.duckService = duckService;
        this.friendshipService = friendshipService;
    }

    /**
     * Start app.
     */
    public void startApp(){
        beforeStart();
        menuBeforeSignUp();
    }

    /**
     * Before start.
     */
    public void beforeStart(){
        this.authService = new AuthService(persoanaService, duckService);
    }

    /**
     * Menu before sign up.
     */
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
                    System.out.println("Optiune invalida!");
            }
        }
    }

    /**
     * Menu after sign up.
     *
     * @param loggedInUser the logged in user
     */
    public void menuAfterSignUp(User loggedInUser) {
        int choice;
        while (true) {
            this.menu.showMenuAfterSignUp();
            choice = this.getUserChoice(5);

            switch (choice) {
                case 1:
                    logout();
                    break;
                case 2:
                    addFriend();
                    break;
                case 3:
                    System.out.println("Functionalitatea de trimis mesaje va fi implementata aici.");
                    break;
                case 4:
                    deleteAccount();
                    break;
                case 5:
                    menuBeforeSignUp();
                default:
                    System.out.println("Optiune invalida!");
            }
        }
    }

    /**
     * Gets user choice.
     *
     * @param max the max
     * @return the user choice
     */
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
                System.out.println("Optiune invalida! Introduceti un numar intre 1 si " + max + ".");
            }
        }

        return choice;
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
            Card card = numeCard.isEmpty() ? null : new Card(numeCard);

            Duck duck = new Duck(id, username, email, password, tip, viteza, rezistenta, card);
            try{
                this.authService.signUp(duck, "ducks.txt");
            } catch (UserAlreadyExists e) {
                System.out.println("Eroare la inregistrare: " + e.getMessage());
                return;
            }

            System.out.println("Rata creata cu succes:\n" + duck.toString());

            loggedInUser = duck;
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
            try{
                this.authService.signUp(persoana, "persoane.txt");
            } catch (UserAlreadyExists e) {
                System.out.println("Eroare la inregistrare: " + e.getMessage());
                return;
            }

            System.out.println("Persoana creata cu succes:\n" + persoana.toString());

            loggedInUser = persoana;
            menuAfterSignUp(persoana);

        } else {
            System.out.println("Tip de utilizator invalid. Scrie 'rata' sau 'persoana'.");
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
            User loggedInUser = authService.loginAndReturnUser(username, password);
            System.out.println("Autentificare reusita!");
            this.loggedInUser = loggedInUser;
            menuAfterSignUp(loggedInUser);
        } catch (RuntimeException e) {
            System.out.println("Eroare la autentificare: " + e.getMessage());
        }
    }

    /**
     * Logout.
     */
    public void logout() {

        if (loggedInUser != null) {
            authService.logout(loggedInUser);
            System.out.println("V-ati deconectat cu succes!");
            loggedInUser = null;
        }
        menuBeforeSignUp();
    }

    public void addFriend(){
        Scanner scanner = new Scanner(System.in);

        String username_loggedInUser = this.loggedInUser.getUsername();
        System.out.println("Introduceti numele userului cu care vreti sa fiti prieten: ");
        String username_friend = scanner.nextLine();

        User user_duck = this.duckService.findByUsernameDuck(username_friend, "ducks.txt");
        if (user_duck != null) {
            String user_duck_username = user_duck.getUsername();

            if (user_duck_username != null && !user_duck_username.equals(username_loggedInUser))
            {
                Long id = System.currentTimeMillis();
                Friendship friendship = new Friendship(id, username_loggedInUser, username_friend);
                try{
                    this.friendshipService.saveFriendship(friendship, "friendships.txt");
                } catch(UserNotFound e){
                    System.out.println("Exception occurred: " + e.getMessage());
                }
            }
        }


        User user_person = this.persoanaService.findByUsernamePerson(username_friend, "persoane.txt");
        if (user_person != null) {
            String user_person_username = user_person.getUsername();

            if (user_person_username != null && !user_person_username.equals(username_loggedInUser))
            {
                Long id = System.currentTimeMillis();
                Friendship friendship = new Friendship(id, username_loggedInUser, username_friend);
                try{
                    this.friendshipService.saveFriendship(friendship, "friendships.txt");
                } catch(UserNotFound e){
                    System.out.println("Exception occurred: " + e.getMessage());
                }
            }
        }
    }

    private void deleteAccount(){
        if (this.loggedInUser != null) {
            if (this.loggedInUser instanceof Persoana) {
                try{
                    this.persoanaService.deletePerson(this.loggedInUser, "persoane.txt");
                } catch(UserNotFound e){
                    System.out.println("Exception occurred: " + e.getMessage());
                }

            } else if (this.loggedInUser instanceof Duck) {

                try{
                    this.duckService.deleteDuck(this.loggedInUser, "ducks.txt");
                } catch(UserNotFound e){
                    System.out.println("Exception occurred: " + e.getMessage());
                }

            }
            System.out.println("Utilizator sters cu succes.");
            this.loggedInUser = null;

            menuBeforeSignUp();
        }
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
