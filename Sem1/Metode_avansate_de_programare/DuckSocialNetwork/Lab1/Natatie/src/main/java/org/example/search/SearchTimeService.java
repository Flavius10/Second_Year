package org.example.search;

import org.example.domain.Task;
import org.example.feasibility.RaceService;

public class SearchTimeService extends Task {

    private final TimeSearchStrategy timeSearchStrategy;
    private final RaceService raceService;
    private final double lower;
    private final double upper;
    private final double epsilon;

    public SearchTimeService(String idTask, String description,
                             TimeSearchStrategy timeSearchStrategy, RaceService raceService
                            , double lower, double upper, double epsilon) {
        super(idTask, description);
        this.timeSearchStrategy = timeSearchStrategy;
        this.raceService = raceService;

        this.lower = lower;
        this.upper = upper;
        this.epsilon = epsilon;
    }

    @Override
    public void execute(){
        this.findMinimumTime();
    }

    public void findMinimumTime(){
        this.timeSearchStrategy.findFeasibleTime(this.raceService, this.lower, this.upper, this.epsilon);
    }

}
