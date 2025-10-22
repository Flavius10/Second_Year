package org.example.sorter;

import org.example.domain.Task;
import org.example.reader.DataStorage;
import org.example.reader.DuckContainer;
import org.example.reader.LaneContainer;

public class DataSortedStorage extends Task {

    private final SorterService sorterService;
    private DataStorage dataStorage;

    public DataSortedStorage(String taskID, String descriere,
                             SorterService sorterService, DataStorage dataStorage) {
        super(taskID, descriere);
        this.sorterService = sorterService;
        this.dataStorage = dataStorage;
    }

    public DuckContainer getSortedDucks() {
        return (DuckContainer) this.dataStorage.getRaceProblemData().getDucks();
    }

    public LaneContainer getLanes() {
        return (LaneContainer) this.dataStorage.getRaceProblemData().getLanes();
    }

    public void preparedDataSorted(){
        DuckContainer duckContainer = (DuckContainer) this.dataStorage.getRaceProblemData().getDucks();
        DuckContainer duckContainerSorted = this.sorterService.sortContainer(duckContainer);
        this.dataStorage.getRaceProblemData().setDucks(duckContainerSorted);

    }


    @Override
    public void execute(){
        preparedDataSorted();
    }

}
