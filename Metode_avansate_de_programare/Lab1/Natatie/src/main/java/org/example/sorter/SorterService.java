package org.example.sorter;

import org.example.reader.DuckContainer;

public class SorterService {

    private final Sorter sorter;

    public SorterService(Sorter sorter) {
        this.sorter = sorter;
    }

    public DuckContainer sortContainer(DuckContainer container){
        return sorter.sort(container);
    }

}
