package org.example.network;

import org.example.domain.Friendship;
import org.example.domain.User;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.services.PersoanaService;
import org.example.utils.Constants;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DataProvider {

    private final FriendshipService friendshipService;
    private final PersoanaService persoanaService;
    private final DuckService duckService;

    public DataProvider(FriendshipService friendshipService, PersoanaService persoanaService, DuckService duckService) {
        this.friendshipService = friendshipService;
        this.persoanaService = persoanaService;
        this.duckService = duckService;
    }

    public Iterable<Friendship> loadAllFriendships() {
        return this.friendshipService.findAllFriendships(Constants.FILE_PATH_FRIENDSHIP);
    }

    public Iterable<User> loadAllUsers(){
        return Stream.concat(
                StreamSupport.stream(this.persoanaService.findAllPersons(Constants.FILE_PATH_PERSOANA).spliterator(), false),
                StreamSupport.stream(this.duckService.findAllDucks().spliterator(), false)
        ).toList();
    }

}
