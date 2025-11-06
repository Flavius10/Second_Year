package org.example.domain.events;


public class BinaryTimeSearch{

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
