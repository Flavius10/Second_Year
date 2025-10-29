package org.example.network;

import org.example.domain.Friendship;
import org.example.domain.User;
import org.example.services.DuckService;
import org.example.services.FriendshipService;
import org.example.services.PersoanaService;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The type Network service.
 */
public class NetworkService {

    private final PersoanaService persoanaService;
    private final DuckService duckService;
    private final FriendshipService friendshipService;
    private final GraphBuilder graphBuilder;
    private final GraphAnalyzer graphAnalyzer;

    /**
     * Instantiates a new Network service.
     *
     * @param persoanaService   the persoana service
     * @param duckService       the duck service
     * @param friendshipService the friendship service
     */
    public NetworkService(PersoanaService persoanaService, DuckService duckService, FriendshipService friendshipService) {
        this.persoanaService = persoanaService;
        this.duckService = duckService;
        this.friendshipService = friendshipService;
        this.graphBuilder = new GraphBuilder();
        this.graphAnalyzer = new GraphAnalyzer();
    }

    private Map<String, List<String>> buildNetworkGraph() {
        Iterable<User> allUsers = Stream.concat(
                StreamSupport.stream(persoanaService.findAllPersons("persoane.txt").spliterator(), false),
                StreamSupport.stream(duckService.findAllDucks("ducks.txt").spliterator(), false)
        ).toList();

        Iterable<Friendship> friendships = friendshipService.findAllFriendships("friendships.txt");

        return graphBuilder.buildGraph(allUsers, friendships);
    }

    /**
     * Print number of communities.
     */
    public void printNumberOfCommunities() {
        Map<String, List<String>> graph = buildNetworkGraph();
        int communities = graphAnalyzer.countCommunities(graph);
        System.out.println("Numar de comunitati: " + communities);
    }

    /**
     * Print most sociable community.
     */
    public void printMostSociableCommunity() {
        Map<String, List<String>> graph = buildNetworkGraph();
        List<String> sociable = graphAnalyzer.findMostSociableCommunity(graph);
        System.out.println("Cea mai sociabila comunitate: " + sociable);
    }

    /**
     * Print network stats.
     */
    public void printNetworkStats() {
        printNumberOfCommunities();
        printMostSociableCommunity();
    }
}

