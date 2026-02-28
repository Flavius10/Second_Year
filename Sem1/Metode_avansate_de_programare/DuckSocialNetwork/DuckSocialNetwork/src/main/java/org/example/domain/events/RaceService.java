package org.example.domain.events;


import org.example.domain.ducks.Duck;
import org.example.domain.ducks.Lane;
import org.example.domain.ducks.SwimmingDuck;

import java.awt.*;
import java.util.AbstractMap;
import java.util.List;

public class RaceService{


    private FeasibilityChecker checker;
    private DuckSelector selector;
    private List<SwimmingDuck> sortedDucks;
    private List<Lane> lanes;
    private List<SwimmingDuck> container_principal;

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
        this.container_principal = ducks;

        if (ducks == null) return false;

        return checker.isFeasible(ducks, lanes, time);
    }

    public List<SwimmingDuck> getRaceResult(){

        int index = 0;

        RaceEvaluator evaluator = this.checker.getRaceEvaluator();

        for (Duck duck : container_principal) {
            if (duck != null){
                double time = evaluator.computeRace(duck, this.lanes.get(index));
                System.out.println(String.format("Duck %s finished in %f seconds on lane %d", duck.getUsername(), time, lanes.get(index).getId()));
                index++;
            }
        }

        return this.container_principal;
    }


}
