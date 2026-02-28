package org.example.sorter;

import org.example.domain.Duck;
import org.example.reader.DuckContainer;

public class DuckSorterByResistanceBubble implements Sorter{

    @Override
    public DuckContainer sort(DuckContainer container){

        Duck[] ducks = container.getAll();
        int n = container.size();

        for (int i = 0; i < n; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; ++j){
                if (ducks[j].getResistance() > ducks[j + 1].getResistance()){

                    Duck temp = ducks[j];
                    ducks[j] = ducks[j + 1];
                    ducks[j + 1] = temp;

                    swapped = true;
                }
            }

            if (!swapped) break;
        }

        DuckContainer containerSorted = new DuckContainer(n);
        for(int i = 0; i < n; i++){
            containerSorted.add(ducks[i]);
        }

        return containerSorted;
    }

}
