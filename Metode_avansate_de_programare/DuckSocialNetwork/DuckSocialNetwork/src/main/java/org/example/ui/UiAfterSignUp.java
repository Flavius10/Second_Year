package org.example.ui;

import org.example.domain.TypeDuck;
import org.example.domain.ducks.*;
import org.example.domain.Friendship;
import org.example.domain.Persoana;
import org.example.domain.User;
import org.example.domain.card.Card;
import org.example.domain.card.FlyingCard;
import org.example.domain.card.SwimmingCard;
import org.example.domain.card.TypeCard;
import org.example.domain.events.*;
import org.example.exceptions.FriendshipNotFound;
import org.example.exceptions.UserNotFound;
import org.example.network.NetworkService;
import org.example.repositories.RepoEvent;
import org.example.services.*;
import org.example.utils.paging.Page;
import org.example.utils.paging.Pageable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.StreamSupport;

/**
 * The type Ui after sign up.
 */
public class UiAfterSignUp extends UiAbstract{

    private final PersoanaService persoanaService;
    private final DuckService duckService;
    private final FriendshipService friendshipService;
    private final NetworkService networkService;
    public final CardService cardService;
    private User loggedInUser;

    /**
     * Instantiates a new Ui after sign up.
     *
     * @param authService       the auth service
     * @param menu              the menu
     * @param persoanaService   the persoana service
     * @param duckService       the duck service
     * @param friendshipService the friendship service
     * @param networkService    the network service
     * @param user              the user
     */
    public UiAfterSignUp(AuthService authService, Menu menu,
                         PersoanaService persoanaService, DuckService duckService,
                         FriendshipService friendshipService, NetworkService networkService, CardService cardService,
                         User user) {
        super(authService, menu);

        this.persoanaService = persoanaService;
        this.duckService = duckService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.loggedInUser = user;
        this.cardService = cardService;
    }

    @Override
    public void execute() {
        this.execute_commands();
    }

    @Override
    public void showMenu() {}

    /**
     * Execute commands.
     */
    public void execute_commands() {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        while (running) {

            if (loggedInUser.getClass().equals(SwimmingDuck.class)) {
                menu.showMenuAfterSignUpDucks();
            } else if (loggedInUser.getClass().equals(FlyingDuck.class)) {
                menu.showMenuAfterSignUpDucks();
            } else {
                this.menu.showMenuAfterSignUpPersons();
            }


            int choice = getUserChoice(9);

            switch (choice) {
                case 1:
                    logout();
                    new UiBeforeSignUp(authService, menu, persoanaService, duckService, friendshipService, networkService, cardService).execute();
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
                    if (loggedInUser.getClass().equals(SwimmingDuck.class)) {
                        addCard();
                    } else if (loggedInUser.getClass().equals(FlyingDuck.class)) {
                        addCard();
                    }else{
                        addEvent();
                    }
                    break;

                case 8:
                    pagging();
                    break;

                case 9:
                    new UiBeforeSignUp(authService, menu, persoanaService, duckService, friendshipService, networkService, cardService).execute();
                    break;

                default:
                    System.out.println("Optiune invalida!");
                    break;
            }
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
        new UiBeforeSignUp(authService, menu, persoanaService, duckService, friendshipService, networkService, cardService).execute();
    }

    /**
     * Add friend.
     */
    public void addFriend(){
        Scanner scanner = new Scanner(System.in);

        String username_loggedInUser = this.loggedInUser.getUsername();
        System.out.println("Introduceti numele userului cu care vreti sa fiti prieten: ");
        String username_friend = scanner.nextLine();

        User user_duck = this.duckService.findByUsernameDuck(username_friend);
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


        User user_person = this.persoanaService.findByUsernamePerson(username_friend);
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
                    this.persoanaService.deletePerson((Persoana) this.loggedInUser);
                } catch(UserNotFound e){
                    System.out.println("Exception occurred: " + e.getMessage());
                }

            } else if (this.loggedInUser instanceof Duck) {

                try{
                    this.duckService.deleteDuck((Duck)this.loggedInUser);
                } catch(UserNotFound e){
                    System.out.println("Exception occurred: " + e.getMessage());
                }

            }
            System.out.println("Utilizator sters cu succes.");
            this.loggedInUser = null;

            new UiBeforeSignUp(authService, menu, persoanaService, duckService, friendshipService, networkService, cardService).execute();
        }
    }

    private void printNrCommunities(){
        this.networkService.printNumberOfCommunities();
    }

    private void printMostSociableCommunity(){
        this.networkService.printMostSociableCommunity();
    }

    private void addCard(){

        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceti id-ul cardului: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.println("Adaugati numele cardului: ");
        String numeCard = scanner.nextLine();

        System.out.println("Introduceti tipul cardului(FLYING/SWIMMING): ");
        String type = scanner.nextLine();
        TypeCard typeCard = TypeCard.valueOf(type.toUpperCase());

        List<Duck> membri = new ArrayList<>();
        System.out.println("Adaugati lista de rate: ");
        System.out.println("Adaugati ratele membre (introduceti username-ul):");

        while (true) {
            System.out.print("   -> Username rata (sau 'gata' pentru a termina): ");
            String username = scanner.nextLine();

            if (username.equalsIgnoreCase("gata")) {
                break;
            }

            User foundUser = this.duckService.findByUsernameDuck(username);

            if (foundUser != null) {
                Duck duckToAdd = (Duck) foundUser;

                if (!membri.contains(duckToAdd)){
                    if (duckToAdd.getTip().equals(TypeDuck.valueOf(type.toUpperCase()))) {
                        membri.add(duckToAdd);
                        System.out.println("   + Rata '" + duckToAdd.getUsername() + "' a fost adaugata.");
                    }else{
                        System.out.println("Rata trebuie sa fie de tipul " + type.toUpperCase() + " si nu " + duckToAdd.getTip() + ".");
                    }

                } else {
                    System.out.println("   ! Rata '" + duckToAdd.getUsername() + "' este deja in lista.");
                }
            } else {
                System.out.println("   ! EROARE: Rata cu username-ul '" + username + "' nu a fost gasita.");
            }
        }

        Card<? extends Duck> newCard;

        if (type.equals("SWIMMING")){

            List<SwimmingDuck> general_list = membri.stream()
                    .filter(duck -> duck instanceof SwimmingDuck)
                    .map(duck -> (SwimmingDuck) duck)
                    .toList();

            newCard = new SwimmingCard(id, numeCard, general_list, typeCard);
        } else {
            List<FlyingDuck> general_list = membri.stream()
                    .filter(duck -> duck instanceof FlyingDuck)
                    .map(duck -> (FlyingDuck) duck)
                    .toList();

            newCard = new FlyingCard(id, numeCard, general_list, typeCard);
        }

        try{
            this.cardService.saveCard(newCard, "cards.txt");

            System.out.println("Cardul '" + newCard.getNumeCard() + "' a fost creat cu " + membri.size() + " membri.");
            System.out.println("Performanta medie a cardului: " + newCard.getMediePerformanta());
        } catch (Exception e){
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    private void addEvent(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti numele evenimentului: ");
        String numeEvent = scanner.nextLine();

        System.out.println("Introduceti numarul de rate care sa participe: ");
        int nrRate = Integer.parseInt(scanner.nextLine());

        List<Lane> lanes = new ArrayList<>();
        System.out.println("Adaugati lista de lane-uri: ");
        System.out.println("Adaugati lane-urile membre (se va citi pana la introducerea cifrei 0):" );

        Long i = 0L;
        while (true) {
            String lane = scanner.nextLine();
            int laneInt = Integer.parseInt(lane);
            Lane laneToAdd = new Lane(i, laneInt);
            i++;
            if (laneInt == 0) {
                break;
            }
            lanes.add(laneToAdd);
        }
        if (nrRate < lanes.size()) {
            System.out.println("EROARE: Nu pot participa " + nrRate + " rate pe doar " + lanes.size() + " culoare!");
            System.out.println("Evenimentul a fost anulat.");
            return;
        }

        try{
            Iterable<Duck> allDucksIterable = this.duckService.findAllDucks();

            List<SwimmingDuck> swimmingDucks = StreamSupport.stream(allDucksIterable.spliterator(), false)
                    .filter(duck -> duck instanceof SwimmingDuck)
                    .map(duck -> (SwimmingDuck) duck)
                    .toList();

            List<SwimmingDuck> ducksSortedBySpeed = swimmingDucks.stream()
                    .sorted(Comparator.comparingDouble(SwimmingDuck::getViteza))
                    .toList();

            List<Lane> sortedLanes = lanes.stream()
                    .sorted(Comparator.comparingDouble(Lane::getLength))
                    .toList();

            RaceEvent raceEvent = new RaceEvent(i, numeEvent);
            RepoEvent repoEvent = new RepoEvent();
            EventService eventService = new EventService(repoEvent);
            eventService.add(raceEvent);

            raceEvent.subscribe(loggedInUser);
            raceEvent.startRace(ducksSortedBySpeed, sortedLanes);
            List<SwimmingDuck> swimmingDucksFinal = raceEvent.getDucks_final();
            for(SwimmingDuck swimmingDuck : swimmingDucksFinal){
                raceEvent.subscribe(swimmingDuck);
            }

            raceEvent.notifySubscribers(raceEvent.getMessage());

        } catch(Exception e){
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    private void pagging(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti numarul de elemente din pagina: ");
        int nrPagini = Integer.parseInt(scanner.nextLine());
        int currentPage = 0;


        while(true){
            System.out.println("Page " + (currentPage + 1) + ":");
            Pageable pageable = new Pageable(currentPage, nrPagini);
            if (this.loggedInUser instanceof Persoana) {
                Page<Persoana> persoanaOnPage = this.persoanaService.findAllOnPage(pageable);
                Iterable<Persoana> persoanaIterable = persoanaOnPage.getElementsOnPage();
                for (Persoana persoana : persoanaIterable) {
                    System.out.println(persoana.toString());
                }
            }
            else{
                Page<Duck> ducksOnPage = this.duckService.findAllOnPage(pageable);
                Iterable<Duck> ducksIterable = ducksOnPage.getElementsOnPage();
                for (Duck duck : ducksIterable) {
                    System.out.println(duck.toString());
                }
            }



            System.out.println("N. Next page");
            System.out.println("P. Previous page");
            System.out.println("Q. exit");

            System.out.println("Enter your choice: ");
            String input = scanner.nextLine();
            if(input.equals("Q")){
                break;
            }else if(input.equals("N")){
                currentPage++;
            }else if(input.equals("P")){
                currentPage--;
            }else{
                System.out.println("Invalid input");
            }
        }


    }

}
