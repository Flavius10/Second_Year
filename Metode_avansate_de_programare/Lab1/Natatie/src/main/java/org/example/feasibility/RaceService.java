package org.example.feasibility;

import org.example.domain.Duck;
import org.example.domain.Lane;
import org.example.domain.RaceResultEntry;
import org.example.domain.Task;
import org.example.output.RaceResult;
import org.example.output.ResultBuilder;
import org.example.reader.DuckContainer;
import org.example.reader.LaneContainer;
import org.example.reader.RaceResultsContainer;
import org.example.sorter.DataSortedStorage;

public class RaceService extends Task {

    //Datele sortate
    private DataSortedStorage sortedStorage;

    //FeasibilityChecker ca sa verifice daca se poate realiza cursa in timpul time
    private FeasibilityChecker checker;

    //cel care alege ratele pentru cursa
    private DuckSelector selector;

    //cel care construieste rezultatul final
    private ResultBuilder resultBuilder;

    private DuckContainer container_principal;


    public RaceService(String idTask, String description,
                       DataSortedStorage sortedStorage, FeasibilityChecker checker, ResultBuilder resultBuilder,
                       DuckSelector selector){
        super(idTask, description);
        this.sortedStorage = sortedStorage;
        this.checker = checker;
        this.resultBuilder = resultBuilder;
        this.selector = selector;
    }

    @Override
    public void execute(){

    }

    public boolean runRace(double time){

        //Data for the call of the method selector
        DuckContainer sortedDucks = this.sortedStorage.getSortedDucks();
        int numberOfLanes = this.sortedStorage.getLanes().size();
        LaneContainer lanes = this.sortedStorage.getLanes();
        RaceEvaluator evaluator = this.checker.getRaceEvaluator();

        //aici alegem cele M rate
        DuckContainer ducks = this.selector.selectDucks(sortedDucks, lanes, numberOfLanes, time, evaluator);

        if (ducks == null) return false;
        container_principal = ducks;

        return checker.isFeasible(ducks, lanes, time);
    }

    public DuckContainer getDucks(){
        return container_principal;
    }

    public RaceResult getRaceResult(){

        RaceResultsContainer raceResultsContainer = new RaceResultsContainer(container_principal.size());
        LaneContainer lanes = this.sortedStorage.getLanes();

        Lane[] lanes_array = lanes.getAll();
        int index = 0;

        RaceEvaluator evaluator = this.checker.getRaceEvaluator();

        for (Duck duck : container_principal.getAll()) {
            if (duck != null){
                double time = evaluator.computeRace(duck, lanes_array[index]);
                RaceResultEntry entry = new RaceResultEntry(duck, lanes_array[index], time);
                raceResultsContainer.add(entry);
                index++;
            }
        }

        return resultBuilder.build(raceResultsContainer);
    }

}
