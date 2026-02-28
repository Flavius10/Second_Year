package org.example.feasibility;

import org.example.domain.Duck;
import org.example.domain.Lane;
import org.example.reader.DuckContainer;
import org.example.reader.LaneContainer;

public class RaceEvaluator {

    private final double tolerance;

    public RaceEvaluator(double tolerance) {
        this.tolerance = tolerance;
    }

    public double computeRace(Duck duck, Lane lane){
        int duck_speed = duck.getSpeed();
        int lane_distance = lane.getDistance();
        return (lane_distance / (double)duck_speed) * 2.0;
    }

    public double getTolerance(){
        return this.tolerance;
    }

    public double computeTotalTime(DuckContainer ducks, LaneContainer lanes) {
        Duck[] ducksArray = ducks.getAll();
        Lane[] lanesArray = lanes.getAll();

        double maxTime = 0.0;

        for (int i = 0; i < lanesArray.length; i++) {
            double time = computeRace(ducksArray[i], lanesArray[i]);
            if (time > maxTime) maxTime = time;
        }

        return maxTime;
    }


}
