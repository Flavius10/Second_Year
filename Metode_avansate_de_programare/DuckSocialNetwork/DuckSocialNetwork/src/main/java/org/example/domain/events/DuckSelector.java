package org.example.domain.events;

import org.example.domain.ducks.Duck;
import org.example.domain.ducks.Lane;
import org.example.domain.ducks.SwimmingDuck;

import java.util.ArrayList;
import java.util.List;

public class DuckSelector {

    private List<SwimmingDuck> bestCombination;
    private double bestTime;

    public List<SwimmingDuck> selectDucks(List<SwimmingDuck> allDucks, List<Lane> lanes,
                                     int numberOfRows, double timeLimit, RaceEvaluator raceEvaluator) {


        bestCombination = null;
        bestTime = Double.MAX_VALUE;

        List<SwimmingDuck> current = new ArrayList<>(numberOfRows);

        generateCombinations(allDucks, lanes, raceEvaluator, timeLimit,
                current, 0, 0, numberOfRows);

        if (bestCombination == null) return null;

        return bestCombination;
    }

    private void generateCombinations(List<SwimmingDuck> all, List<Lane> lanes,
                                      RaceEvaluator evaluator, double timeLimit,
                                      List<SwimmingDuck> current, int start, int depth, int m) {

        if (depth == m) {

            List<SwimmingDuck> candidate = new ArrayList<>(m);
            for (int i = 0; i < m; i++) candidate.add(current.get(i));

            List<Lane> lanes_container = new ArrayList<>(lanes.size());
            for (int i = 0; i < lanes.size(); i++) lanes_container.add(lanes.get(i));

            double totalTime = evaluator.computeTotalTime(candidate, lanes_container);

            if (totalTime <= timeLimit && totalTime < bestTime) {
                bestTime = totalTime;
                bestCombination = candidate;
            }
            return;
        }

        for (int i = start; i < all.size(); i++) {
            current.set(depth, all.get(i));
            generateCombinations(all, lanes, evaluator, timeLimit, current, i + 1, depth + 1, m);
        }
    }

}
