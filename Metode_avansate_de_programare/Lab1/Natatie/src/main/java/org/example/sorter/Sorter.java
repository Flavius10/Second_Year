package org.example.sorter;

import org.example.domain.Duck;
import org.example.reader.DuckContainer;
import org.example.reader.ProblemContainer;

public interface Sorter {

    DuckContainer sort(DuckContainer container);

}
