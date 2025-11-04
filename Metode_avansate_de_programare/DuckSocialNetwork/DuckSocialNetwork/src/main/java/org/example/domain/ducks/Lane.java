package org.example.domain.ducks;

public class Lane {

    public Long id;
    public double length;

    public Lane(Long id, double length) {
        this.id = id;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public double getLength() {
        return length;
    }

    public void setId(Long id) {
        this.id = id;
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
