package org.example.search;

import org.example.feasibility.RaceService;
import org.example.output.RaceResult;
import org.example.reader.DuckContainer;

public class BinaryTimeSearch implements TimeSearchStrategy{

    @Override
    public double findFeasibleTime(RaceService service, double left, double right, double epsilon){

        double result = right;

        while(right - left > epsilon){
            double mid = (left + right) / 2.0;

            boolean feasible = service.runRace(mid);

            if (feasible){
                result = mid;
                right = mid;
            }
            else {
                left = mid;
            }

        }

        return result;

    }

}
