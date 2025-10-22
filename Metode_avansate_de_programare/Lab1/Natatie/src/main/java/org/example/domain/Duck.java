package org.example.domain;

import java.util.Objects;

public class Duck implements Comparable<Duck>{

    private int duckId;
    private int speed;
    private int resistance;

    public Duck(int duckId, int speed, int resistance) {
        this.duckId = duckId;
        this.speed = speed;
        this.resistance = resistance;
    }

    public int getDuckId() {
        return duckId;
    }

    public int getSpeed() {
        return speed;
    }

    public int getResistance() {
        return resistance;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public void setDuckId(int duckId) {
        this.duckId = duckId;
    }

    @Override
    public String toString(){
        return "Duck{" +
                "duckId=" + duckId +
                ", speed=" + speed +
                ", resistance=" + resistance +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof Duck duck) {
            return this.resistance == duck.resistance;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(resistance);
    }

    @Override
    public int compareTo(Duck other) {
        return Integer.compare(this.resistance, other.resistance);
    }


}
