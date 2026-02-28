package org.example.domain;

import java.util.Objects;

public class Lane {

    private int laneId;
    private int distance;

    public Lane(int laneId, int distance) {
        this.laneId = laneId;
        this.distance = distance;
    }

    public int getLaneId() {
        return this.laneId;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setLaneId(int laneId) {
        this.laneId = laneId;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString(){
        return "Lane{" +
                "laneId=" + laneId +
                ", distance=" + distance +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof Lane lane) {
            return this.distance == lane.distance;
        }

        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(distance);
    }

}
