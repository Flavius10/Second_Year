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
    private AuthService authService;

    private User loggedInUser;

    public Ui(Menu menu,
              PersoanaService persoanaService,
              DuckService duckService,
              FriendshipService friendshipService,
              NetworkService networkService) {
        this.menu = menu;
        this.persoanaService = persoanaService;
        this.duckService = duckService;
        this.friendshipService = friendshipService;
        this.networkService = networkService;
    }

    public void startApp() {
        beforeStart();

        UiBeforeSignUp uiBefore = new UiBeforeSignUp(
                authService, menu,
                persoanaService, duckService, friendshipService, networkService
        );

        uiBefore.execute();

        this.loggedInUser = uiBefore.getLoggedInUser();

        if (loggedInUser != null) {
            UiAfterSignUp uiAfter = new UiAfterSignUp(
                    authService, menu,
                    persoanaService, duckService, friendshipService, networkService,
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
