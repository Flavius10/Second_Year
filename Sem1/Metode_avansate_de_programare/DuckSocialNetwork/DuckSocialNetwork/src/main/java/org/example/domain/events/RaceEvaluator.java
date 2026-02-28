package org.example.domain.events;
import org.example.domain.ducks.Duck;
import org.example.domain.ducks.Lane;
import org.example.domain.ducks.SwimmingDuck;

import java.util.List;


public class RaceEvaluator {

    private final double tolerance;

    public RaceEvaluator(double tolerance) {
        this.tolerance = tolerance;
    }

    public double computeRace(Duck duck, Lane lane){
        double duck_speed = duck.getViteza();
        double lane_distance = lane.getLength();
        return (lane_distance / (double)duck_speed) * 2.0;
    }
    public double computeTotalTime(List<SwimmingDuck> ducks, List<Lane> lanes) {

        double maxTime = 0.0;

        for (int i = 0; i < ducks.size(); i++) {
            double time = computeRace(ducks.get(i), lanes.get(i));
            if (time > maxTime) maxTime = time;
        }

        return maxTime;
    }


}
