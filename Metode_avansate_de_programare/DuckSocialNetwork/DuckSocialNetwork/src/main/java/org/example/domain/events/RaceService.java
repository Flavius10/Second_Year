package org.example.domain.events;


import org.example.domain.ducks.Lane;
import org.example.domain.ducks.SwimmingDuck;

import java.util.List;

public class RaceService{


    private FeasibilityChecker checker;
    private DuckSelector selector;
    private List<SwimmingDuck> sortedDucks;
    private List<Lane> lanes;

    public RaceService(FeasibilityChecker checker, DuckSelector selector,
                       List<SwimmingDuck> sortedDucks, List<Lane> lanes) {
        this.checker = checker;
        this.selector = selector;
        this.sortedDucks = sortedDucks;
        this.lanes = lanes;
    }


    public boolean runRace(double time){

        RaceEvaluator evaluator = this.checker.getRaceEvaluator();

        List<SwimmingDuck> ducks = this.selector.selectDucks(sortedDucks, lanes, lanes.size(), time, evaluator);

        if (ducks == null) return false;

        return checker.isFeasible(ducks, lanes, time);
    }


}
