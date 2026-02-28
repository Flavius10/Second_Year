package org.example.sorter;

import org.example.domain.Duck;
import org.example.reader.DuckContainer;
import org.example.reader.ProblemContainer;

import java.util.Arrays;
import java.util.Comparator;

public class DuckSorterByResistanceSimple implements Sorter{

    @Override
    public DuckContainer sort(DuckContainer container){

        Duck[] ducks = container.getAll();
        int size = container.size();
        Arrays.sort(ducks, 0, size, Comparator.comparing(Duck::getResistance));

        DuckContainer sortedContainer = new DuckContainer(size);
        for(int i = 0; i < size; i++){
            sortedContainer.add(ducks[i]);
        }

        return sortedContainer;
    }

}
