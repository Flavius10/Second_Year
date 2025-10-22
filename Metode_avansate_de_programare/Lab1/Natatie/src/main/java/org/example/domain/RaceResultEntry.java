package org.example.domain;

import java.util.Objects;

public class RaceResultEntry {

    private Duck duck;
    private Lane lane;
    private double time;

    public RaceResultEntry(Duck duck, Lane lane, double time) {
        this.duck = duck;
        this.lane = lane;
        this.time = time;
    }

    public Duck getDuck() {
        return duck;
    }

    public Lane getLane() {
        return lane;
    }

    public double getTime() {
        return time;
    }

    public void setDuck(Duck duck){
        this.duck = duck;
    }

    public void setLane(Lane lane){
        this.lane = lane;
    }
    public void setTime(double time){
        this.time = time;
    }

    @Override
    public String toString(){
        return "RaceResultEntry{" +
                "duck=" + duck +
                ", lane=" + lane +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof RaceResultEntry raceResultEntry) {
            return this.duck.equals(raceResultEntry.duck) && this.lane.equals(raceResultEntry.lane);
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(duck, lane);
    }

}
