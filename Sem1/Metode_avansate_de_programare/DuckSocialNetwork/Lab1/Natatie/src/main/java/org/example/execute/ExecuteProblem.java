package org.example.execute;

import org.example.domain.Strategy;
import org.example.domain.Task;
import org.example.feasibility.DuckSelector;
import org.example.feasibility.FeasibilityChecker;
import org.example.feasibility.RaceEvaluator;
import org.example.feasibility.RaceService;
import org.example.output.RaceResult;
import org.example.output.ResultBuilder;
import org.example.output.ResultWriter;
import org.example.reader.DataReader;
import org.example.reader.DataStorage;
import org.example.reader.ReadDataClass;
import org.example.runner.StrategyTaskRunner;
import org.example.search.BinaryTimeSearch;
import org.example.search.SearchTimeService;
import org.example.search.TimeSearchStrategy;
import org.example.sorter.DataSortedStorage;
import org.example.sorter.DuckSorterByResistanceBubble;
import org.example.sorter.Sorter;
import org.example.sorter.SorterService;
import org.example.utils.Constants;

public class ExecuteProblem extends Task {

    public ExecuteProblem(String taskID, String descriere) {
        super(taskID, descriere);
    }

    @Override
    public void execute(){
        StrategyTaskRunner strategyTaskRunner = new StrategyTaskRunner(Strategy.FIFO);

//-----------Verificare Task Reader------------------
        DataReader reader = new ReadDataClass();
        Task storage = new DataStorage("TaskId", "DataStorage Class", reader);

        Sorter sorter = new DuckSorterByResistanceBubble();
        SorterService sorterService = new SorterService(sorter);

//-----------Verificare Task Sorter------------------
        Task sortedStorage = new DataSortedStorage("TaskId", "Descriere",
                sorterService, (DataStorage)storage);

        strategyTaskRunner.addTask(storage);
        strategyTaskRunner.addTask(sortedStorage);
        strategyTaskRunner.executeAll();

//----------------------Verificare Task Feasibility-------------------------

        /// Datele pentru RaceService
        RaceEvaluator evaluator = new RaceEvaluator(0.013);

        FeasibilityChecker feasibilityChecker = new FeasibilityChecker(evaluator);
        DuckSelector duckSelector = new DuckSelector();
        ResultBuilder resultBuilder = new ResultBuilder(evaluator);

        /// Creare RaceService si TimeSearchStrategy
        RaceService raceService = new RaceService("1", "description",
                (DataSortedStorage) sortedStorage, feasibilityChecker, resultBuilder, duckSelector);
        TimeSearchStrategy binarySearchStrategy = new BinaryTimeSearch();

        /// Creare SearchTimeService
        SearchTimeService searchTimeService = new SearchTimeService("1", "description1", binarySearchStrategy, raceService,
                0.0, Constants.MAX_TIME, 0.0013);

        strategyTaskRunner.addTask(searchTimeService);
        strategyTaskRunner.executeOneTask();

        RaceResult raceResult = raceService.getRaceResult();
        ResultWriter resultWriter = new ResultWriter("TaskIdWriter", "Output"
                ,raceResult, "result.txt");

        strategyTaskRunner.addTask(resultWriter);
        strategyTaskRunner.executeOneTask();

    }

}
