package org.example.feasibility;

import org.example.domain.Duck;
import org.example.domain.Lane;
import org.example.reader.DuckContainer;
import org.example.reader.LaneContainer;

public class DuckSelector {

    private DuckContainer bestCombination;
    private double bestTime;

    public DuckContainer selectDucks(DuckContainer allDucks, LaneContainer lanes,
                                     int numberOfRows, double timeLimit, RaceEvaluator raceEvaluator) {

        Duck[] ducks = allDucks.getAll();
        Lane[] laneArr = lanes.getAll();

        bestCombination = null;
        bestTime = Double.MAX_VALUE;

        Duck[] current = new Duck[numberOfRows];

        generateCombinations(ducks, laneArr, raceEvaluator, timeLimit,
                current, 0, 0, numberOfRows);

        if (bestCombination == null) return null;

        return bestCombination;
    }

    private void generateCombinations(Duck[] all, Lane[] lanes,
                                      RaceEvaluator evaluator, double timeLimit,
                                      Duck[] current, int start, int depth, int m) {

        if (depth == m) {

            DuckContainer candidate = new DuckContainer(m);
            for (int i = 0; i < m; i++) candidate.add(current[i]);

            LaneContainer lanes_container = new LaneContainer(lanes.length);
            for (int i = 0; i < lanes.length; i++) lanes_container.add(lanes[i]);

            double totalTime = evaluator.computeTotalTime(candidate, lanes_container);

            if (totalTime <= timeLimit && totalTime < bestTime) {
                bestTime = totalTime;
                bestCombination = candidate;
            }
            return;
        }

        for (int i = start; i < all.length; i++) {
            current[depth] = all[i];
            generateCombinations(all, lanes, evaluator, timeLimit, current, i + 1, depth + 1, m);
        }
    }


    
}
