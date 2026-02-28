package org.example.reader;

import org.example.domain.Duck;
import org.example.domain.Lane;

public class RaceProblemData {

    private ProblemContainer<Duck> ducks;
    private ProblemContainer<Lane> lanes;
    private int numberOfDucks;
    private int numberOfLanes;

    public RaceProblemData(ProblemContainer<Duck> ducks, ProblemContainer<Lane> lanes) {
        this.ducks = ducks;
        this.lanes = lanes;
        this.numberOfDucks = ducks.size();
        this.numberOfLanes = lanes.size();
    }

    public ProblemContainer<Duck> getDucks() {
        return this.ducks;
    }

    public ProblemContainer<Lane> getLanes() {
        return this.lanes;
    }

    public int getNumberOfDucks() {
        return this.numberOfDucks;
    }

    public int getNumberOfLanes() {
        return this.numberOfLanes;
    }

    public void setNumberOfDucks(int numberOfDucks) {
        this.numberOfDucks = numberOfDucks;
    }

    public void setNumberOfLanes(int numberOfLanes) {
        this.numberOfLanes = numberOfLanes;
    }

    public void setDucks(ProblemContainer<Duck> ducks) {
        this.ducks = ducks;
    }

    public void setLanes(ProblemContainer<Lane> lanes) {
        this.lanes = lanes;
    }

}
