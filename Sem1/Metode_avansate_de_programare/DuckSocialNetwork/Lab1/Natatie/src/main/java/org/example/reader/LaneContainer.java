package org.example.reader;

import org.example.domain.Lane;

import java.util.Arrays;

public class LaneContainer implements ProblemContainer<Lane>{

    private Lane[] lanes;
    private int size;

    public LaneContainer(int size) {
        this.lanes = new Lane[size];
        this.size = 0;
    }

    @Override
    public void add(Lane lane) {
        this.lanes[this.size++] = lane;
    }


    @Override
    public Lane[] getAll() {
        return lanes;
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
    public Lane remove(){
        return this.lanes[--this.size];
    }


}
