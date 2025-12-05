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
     * Returns the number of distinct communities in the network.
     * CHANGED: from void to int
     */
    public int connectedCommunities() {
        Map<String, List<String>> graph = buildNetworkGraph();
        // Returneaza direct rezultatul (int), nu il printeaza
        return graphService.countCommunities(graph);
    }

    /**
     * Returns the most sociable community (with largest diameter).
     * CHANGED: from void to List<String>
     */
    public List<String> mostSociableCommunity() {
        Map<String, List<String>> graph = buildNetworkGraph();
        // Returneaza lista de ID-uri/Username-uri
        return graphService.findMostSociableCommunity(graph);
    }

    /**
     * Prints both stats together (Useful only for console debugging).
     */
    public void printNetworkStats() {
        // Aici putem folosi metodele noi si sa le printam daca vrem neaparat in consola
        System.out.println("Numar de comunitati: " + connectedCommunities());
        System.out.println("Cea mai sociabila comunitate: " + mostSociableCommunity());
    }
}