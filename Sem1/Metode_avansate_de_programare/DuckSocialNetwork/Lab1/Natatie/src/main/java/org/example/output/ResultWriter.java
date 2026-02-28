package org.example.output;

import org.example.domain.RaceResultEntry;
import org.example.domain.Task;
import org.example.reader.RaceResultsContainer;

import java.io.*;

public class ResultWriter extends Task {

    private RaceResult raceResult;
    private String fileName;

    public ResultWriter(String taskID, String descriere,
                        RaceResult raceResult, String fileName) {
        super(taskID, descriere);
        this.raceResult = raceResult;
        this.fileName = fileName;
    }

    @Override
    public void execute(){
        writeToFile();
    }

    public void writeToFile(){

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            FileWriter fWriter = new FileWriter(fileName);

            double time = 0.0;

            RaceResultsContainer raceResultsContainer = raceResult.getEntries();

            for(RaceResultEntry entry : raceResultsContainer.getAll()){
                if (entry != null){
                    fWriter.write("Duck: " + entry.getDuck().getDuckId() +
                            ", Lane: " + entry.getLane().getLaneId() +
                            ", Time: " + entry.getTime() + "\n");

                    if(time < entry.getTime()){
                        time = entry.getTime();
                    }
                }
            }

            fWriter.write("\n");
            fWriter.write("Total time: " + time);

            fWriter.close();

            System.out.println("Mesaj scrise in fisier: " + fileName + "");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
