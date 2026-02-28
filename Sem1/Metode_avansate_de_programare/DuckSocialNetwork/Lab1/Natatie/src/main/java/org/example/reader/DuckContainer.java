package org.example.reader;

import org.example.domain.Duck;

import java.util.Arrays;

public class DuckContainer implements ProblemContainer<Duck>{

    private Duck[] ducks;
    private int size;

    public DuckContainer(int size) {
        this.ducks = new Duck[size];
        this.size = 0;
    }

    @Override
    public void add(Duck duck) {
        this.ducks[this.size++] = duck;
    }

    @Override
    public Duck[] getAll() {
        return ducks;
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
    public Duck remove(){
        return this.ducks[--this.size];
    }

    public void setAll(DuckContainer ducks){
        this.ducks = ducks.getAll();
        this.size = ducks.size();
    }


}
