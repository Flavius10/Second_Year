package org.example.search;

import org.example.feasibility.RaceService;

public class SecventialTimeSearch implements TimeSearchStrategy{

    @Override
    public double findFeasibleTime(RaceService service, double left, double right, double epsilon){

        for (double i = left; i <= right; i += epsilon){
            if (service.runRace(i)){
                return i;
            }
        }

        return -1;

    }

}
