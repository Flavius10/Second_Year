package org.example.output;

import org.example.reader.RaceResultsContainer;

public class RaceResult {

    private double minTime;
    private RaceResultsContainer entries;

    public RaceResult(double minTime, RaceResultsContainer entries) {
        this.minTime = minTime;
        this.entries = entries;
    }

    public double getMinTime() {
        return this.minTime;
    }

    public RaceResultsContainer getEntries() {
        return this.entries;
    }

    @Override
    public String toString(){
        return "RaceResult{" +
                "minTime=" + minTime +
                ", entries=" + entries +
                '}';
    }
}
