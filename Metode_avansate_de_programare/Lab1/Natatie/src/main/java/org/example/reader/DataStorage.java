package org.example.reader;

import org.example.domain.Task;
import org.example.utils.Utils;

public class DataStorage extends Task {

    private final DataReader dataReader;
    private RaceProblemData raceProblemData;

    public DataStorage(String taskID, String descriere, DataReader dataReader) {
        super(descriere, taskID);
        this.dataReader = dataReader;
    }

    public void addDData(){

        try {
            String file_input = Utils.FILE_PATH;
            this.raceProblemData = this.dataReader.readData(file_input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public RaceProblemData getRaceProblemData() {
        return this.raceProblemData;
    }

    @Override
    public void execute(){
        this.addDData();
    }

}
