package org.example.domain.events;

import org.example.domain.ducks.Lane;
import org.example.domain.ducks.SwimmingDuck;

import java.util.List;

public class FeasibilityChecker {

    private final RaceEvaluator raceEvaluator;

    public FeasibilityChecker(RaceEvaluator raceEvaluator) {
        this.raceEvaluator = raceEvaluator;
    }

    public RaceEvaluator getRaceEvaluator() {
        return raceEvaluator;
    }

    public boolean isFeasible(List<SwimmingDuck> ducks, List<Lane> lanes, double time){
        return this.raceEvaluator.computeTotalTime(ducks, lanes) <= time;
    }

}
