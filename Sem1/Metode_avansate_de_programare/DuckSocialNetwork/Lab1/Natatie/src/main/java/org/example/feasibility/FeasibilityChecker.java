package org.example.feasibility;

import org.example.domain.Duck;
import org.example.reader.DuckContainer;
import org.example.reader.LaneContainer;

public class FeasibilityChecker {

    private final RaceEvaluator raceEvaluator;

    public FeasibilityChecker(RaceEvaluator raceEvaluator) {
        this.raceEvaluator = raceEvaluator;
    }

    public RaceEvaluator getRaceEvaluator() {
        return raceEvaluator;
    }

    // primeste un sir de rate si un sir de lanes si
    // returneaza true daca se poate realiza in timpul time race-ul
    public boolean isFeasible(DuckContainer ducks, LaneContainer lanes, double time){
        return this.raceEvaluator.computeTotalTime(ducks, lanes) <= time;
    }

}
