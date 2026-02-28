package org.example.network;

import org.example.domain.friendship.Friendship;
import org.example.domain.User;

import java.util.List;
import java.util.Map;

public class GraphService {

    private final GraphAnalyzer graphAnalyzer;
    private final GraphBuilder graphBuilder;

    public GraphService(GraphAnalyzer graphAnalyzer, GraphBuilder graphBuilder) {
        this.graphAnalyzer = graphAnalyzer;
        this.graphBuilder = graphBuilder;
    }

    public Map<String, List<String>> buildGraph(Iterable<User> users, Iterable<Friendship> friendships){
        return this.graphBuilder.buildGraph(users, friendships);
    }

    public int countCommunities(Map<String, List<String>> graph){
        return this.graphAnalyzer.countCommunities(graph);
    }

    public List<String> findMostSociableCommunity(Map<String, List<String>> graph){
        return this.graphAnalyzer.findMostSociableCommunity(graph);
    }

}
