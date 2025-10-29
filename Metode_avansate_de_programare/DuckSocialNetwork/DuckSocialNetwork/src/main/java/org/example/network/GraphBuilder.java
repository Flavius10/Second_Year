package org.example.network;

import org.example.domain.Friendship;
import org.example.domain.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBuilder {

    public Map<String, List<String>> buildGraph(Iterable<User> users, Iterable<Friendship> friendships){

        Map<String, List<String>> graph = new HashMap<>();

        for (User user : users){
            graph.putIfAbsent(user.getUsername(), new ArrayList<>());
        }

        for (Friendship f : friendships){
            String u1 = f.getFirst_friend_username();
            String u2 = f.getSecond_friend_username();

            graph.putIfAbsent(u1, new ArrayList<>());
            graph.putIfAbsent(u2, new ArrayList<>());

            graph.get(u1).add(u2);
            graph.get(u2).add(u1);
        }

        return graph;

    }

}
