package org.example.network;

import org.example.domain.Friendship;
import org.example.domain.User;

import java.util.List;
import java.util.Map;

public class NetworkService {

    private final DataProvider dataProvider;
    private final GraphService graphService;

    public NetworkService(DataProvider dataProvider, GraphService graphService) {
        this.dataProvider = dataProvider;
        this.graphService = graphService;
    }

    private Map<String, List<String>> buildNetworkGraph() {
        Iterable<User> users = dataProvider.loadAllUsers();
        Iterable<Friendship> friendships = dataProvider.loadAllFriendships();
        return graphService.buildGraph(users, friendships);
    }

    /**
     * Prints the number of distinct communities in the network.
     */
    public void printNumberOfCommunities() {
        Map<String, List<String>> graph = buildNetworkGraph();
        int communities = graphService.countCommunities(graph);
        System.out.println("Numar de comunitati: " + communities);
    }

    /**
     * Prints the most sociable community (with largest diameter).
     */
    public void printMostSociableCommunity() {
        Map<String, List<String>> graph = buildNetworkGraph();
        List<String> community = graphService.findMostSociableCommunity(graph);
        System.out.println("Cea mai sociabila comunitate: " + community);
    }

    /**
     * Prints both stats together.
     */
    public void printNetworkStats() {
        printNumberOfCommunities();
        printMostSociableCommunity();
    }
}
