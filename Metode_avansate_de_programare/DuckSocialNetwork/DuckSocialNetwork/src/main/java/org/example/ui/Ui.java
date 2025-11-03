package org.example.ui;

import org.example.domain.User;
import org.example.services.*;
import org.example.network.NetworkService;

public class Ui {

    private final Menu menu;
    private final PersoanaService persoanaService;
    private final DuckService duckService;
    private final FriendshipService friendshipService;
    private final NetworkService networkService;
    private final CardService cardService;
    private AuthService authService;

    private User loggedInUser;

    public Ui(Menu menu,
              PersoanaService persoanaService,
              DuckService duckService,
              FriendshipService friendshipService,
              NetworkService networkService, CardService cardService) {
        this.menu = menu;
        this.persoanaService = persoanaService;
        this.duckService = duckService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
        this.cardService = cardService;
    }

    public void startApp() {
        beforeStart();

        UiBeforeSignUp uiBefore = new UiBeforeSignUp(
                authService, menu,
                persoanaService, duckService, friendshipService, networkService, cardService
        );

        uiBefore.execute();

        this.loggedInUser = uiBefore.getLoggedInUser();

        if (loggedInUser != null) {
            UiAfterSignUp uiAfter = new UiAfterSignUp(
                    authService, menu,
                    persoanaService, duckService, friendshipService, networkService, cardService,
                    loggedInUser
            );

            uiAfter.execute();
        }

        finalMessage();
    }

    private void beforeStart() {
        this.authService = new AuthService(persoanaService, duckService);
    }

    private void finalMessage() {
        System.out.println("Programul se va închide...");
        try {
            Thread.sleep(1000);
            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
