package org.example.search;

import org.example.feasibility.RaceService;

public interface TimeSearchStrategy {

    double findFeasibleTime(RaceService raceService, double left, double right, double epsilon);

}
