package org.example.output;

import org.example.domain.Task;
import org.example.feasibility.RaceEvaluator;
import org.example.reader.DuckContainer;
import org.example.reader.LaneContainer;
import org.example.reader.RaceResultsContainer;

public class ResultBuilder{

    private RaceEvaluator raceEvaluator;

    public ResultBuilder(RaceEvaluator raceEvaluator) {
        this.raceEvaluator = raceEvaluator;
    }


    public RaceResult build(RaceResultsContainer raceResultsContainer){
        RaceResult raceResult = new RaceResult(0.10, raceResultsContainer);
        return raceResult;
    }

}
