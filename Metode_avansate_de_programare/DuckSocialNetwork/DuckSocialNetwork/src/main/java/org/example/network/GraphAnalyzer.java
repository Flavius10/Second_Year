package org.example.network;

import java.util.*;

public class GraphAnalyzer{

    private void dfs(String node, Map<String, List<String>> graph, Set<String> visited){
        visited.add(node);
        for (String friend : graph.get(node)){
            if (!visited.contains(friend)){
                dfs(friend, graph, visited);
            }
        }
    }

    public int countCommunities(Map<String, List<String>> graph){
        Set<String> visited = new java.util.HashSet<>();
        int count = 0;
        for (String node : graph.keySet()){
            if (!visited.contains(node)){
                dfs(node, graph, visited);
                count++;
            }
        }
        return count;
    }

    public List<String> findMostSociableCommunity(Map<String, List<String>> graph){
        Set<String> visited = new HashSet<>();
        List<String> mostSociable = new ArrayList<>();
        int maxDiameter = 0;

        for(String user : graph.keySet()){
            if (!visited.contains(user)) {
                List<String> community = new ArrayList<>();
                dfsCollect(user, graph, visited, community);

                int diameter = computeDiameter(graph, community);
                if (diameter > maxDiameter) {
                    maxDiameter = diameter;
                    mostSociable = community;
                }
            }
        }

        return mostSociable;
    }

    private void dfsCollect(String node, Map<String, List<String>> graph, Set<String> visited, List<String> component) {
        visited.add(node);
        component.add(node);
        for (String friend : graph.get(node)) {
            if (!visited.contains(friend)) {
                dfsCollect(friend, graph, visited, component);
            }
        }
    }

    private int computeDiameter(Map<String, List<String>> graph, List<String> community) {
        int diameter = 0;
        for (String user : community) {
            diameter = Math.max(diameter, bfsMaxDistance(graph, user));
        }
        return diameter;
    }

    private int bfsMaxDistance(Map<String, List<String>> graph, String start) {
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> dist = new HashMap<>();
        queue.add(start);
        dist.put(start, 0);
        int max = 0;

        while (!queue.isEmpty()) {
            String u = queue.poll();
            for (String v : graph.get(u)) {
                if (!dist.containsKey(v)) {
                    dist.put(v, dist.get(u) + 1);
                    queue.add(v);
                    max = Math.max(max, dist.get(v));
                }
            }
        }
        return max;
    }

}

