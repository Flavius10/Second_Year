package org.example.ui;

import org.example.domain.Duck;
import org.example.domain.Friendship;
import org.example.domain.Persoana;
import org.example.domain.User;
import org.example.exceptions.FriendshipNotFound;
import org.example.exceptions.UserNotFound;
import org.example.network.NetworkService;
import org.example.services.AuthService;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.services.PersoanaService;

import java.util.Scanner;

public class UiAfterSignUp extends UiAbstract{

    private final PersoanaService persoanaService;
    private final DuckService duckService;
    private final FriendshipService friendshipService;
    private final NetworkService networkService;
    private User loggedInUser;

    public UiAfterSignUp(AuthService authService, Menu menu,
                         PersoanaService persoanaService, DuckService duckService,
                         FriendshipService friendshipService, NetworkService networkService,
                         User user) {
        super(authService, menu);

        this.persoanaService = persoanaService;
        this.duckService = duckService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.loggedInUser = user;
    }

    @Override
    public void execute() {
        this.execute_commands();
    }

    @Override
    public void showMenu() {}

    public void execute_commands() {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {
            menu.showMenuAfterSignUp();

            int choice = getUserChoice(7);

            switch (choice) {
                case 1:
                    logout();
                    new UiBeforeSignUp(authService, menu, persoanaService, duckService, friendshipService, networkService).execute();
                    break;

                case 2:
                    addFriend();
                    break;

                case 3:
                    removeFriend();
                    break;

                case 4:
                    deleteAccount();
                    break;

                case 5:
                    printNrCommunities();
                    break;

                case 6:
                    printMostSociableCommunity();
                    break;

                case 7:
                    new UiBeforeSignUp(authService, menu, persoanaService, duckService, friendshipService, networkService).execute();
                    break;

                default:
                    System.out.println("Optiune invalida!");
                    break;
            }
        }
    }


    public void logout() {

        if (loggedInUser != null) {
            authService.logout(loggedInUser);
            System.out.println("V-ati deconectat cu succes!");
            loggedInUser = null;
        }
        new UiBeforeSignUp(authService, menu, persoanaService, duckService, friendshipService, networkService).execute();
    }

    /**
     * Add friend.
     */
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
                } catch(Exception e){
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
                } catch(Exception e){
                    System.out.println("Exception occurred: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Remove friend.
     */
    public void removeFriend(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceti numele userului pe care vreti sa il stergeti ca prieten: ");
        String username_friend = scanner.nextLine();

        try{
            Friendship friendship = this.friendshipService.findByNames(this.loggedInUser.getUsername(), username_friend, "friendships.txt");
            this.friendshipService.deleteFriendship(friendship, "friendships.txt");
        }catch(FriendshipNotFound e){
            System.out.println("Exception occurred: " + e.getMessage());
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

            new UiBeforeSignUp(authService, menu, persoanaService, duckService, friendshipService, networkService).execute();
        }
    }

    private void printNrCommunities(){
        this.networkService.printNumberOfCommunities();
    }

    private void printMostSociableCommunity(){
        this.networkService.printMostSociableCommunity();
    }

}
