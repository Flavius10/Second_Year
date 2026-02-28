package org.example.domain.ducks;

import org.example.domain.Entity;

public class Lane extends Entity<Long> {

    public double length;

    public Lane(Long id, double length) {
        this.id = id;
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Lane{" +
                "id=" + id +
                ", length=" + length +
                '}';
    }

}
