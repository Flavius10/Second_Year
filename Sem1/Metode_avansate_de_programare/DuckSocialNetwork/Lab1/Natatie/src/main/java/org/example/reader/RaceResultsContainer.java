package org.example.reader;

import org.example.domain.RaceResultEntry;

public class RaceResultsContainer implements ProblemContainer<RaceResultEntry>{

    private RaceResultEntry[] raceResults;
    private int size;

    public RaceResultsContainer(int capacity) {
        this.raceResults = new RaceResultEntry[capacity];
        this.size = 0;
    }

    @Override
    public void add(RaceResultEntry item){
        this.raceResults[this.size++] = item;
    }

    @Override
    public RaceResultEntry[] getAll() {
        return raceResults;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public RaceResultEntry remove(){
        return this.raceResults[--this.size];
    }

}
