package org.example.reader;

import org.example.domain.Duck;
import org.example.domain.Lane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class ReadDataClass implements DataReader{

    @Override
    public RaceProblemData readData(String file_input) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(file_input));

        /// Salvam numerele de rate si lanes
        StringTokenizer st = new StringTokenizer(reader.readLine());
        int numberOfDucks = Integer.parseInt(st.nextToken());
        int numberOfLanes = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(reader.readLine());
        int[] speeds = new int[numberOfDucks];
        for (int i = 0; i < numberOfDucks; i++) {
            speeds[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(reader.readLine());
        int[] resistances = new int[numberOfDucks];
        for (int i = 0; i < numberOfDucks; i++) {
            resistances[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(reader.readLine());
        int[] distances = new int[numberOfLanes];
        for (int i = 0; i < numberOfLanes; i++) {
            distances[i] = Integer.parseInt(st.nextToken());
        }

        reader.close();

        DuckContainer ducks = new DuckContainer(numberOfDucks);
        for (int i = 0; i < numberOfDucks; ++i){
            Duck duck = new Duck(i + 1, speeds[i], resistances[i]);
            ducks.add(duck);
        }

        LaneContainer lanes = new LaneContainer(numberOfLanes);
        for(int i = 0; i < numberOfLanes; ++i){
            Lane lane = new Lane(i + 1, distances[i]);
            lanes.add(lane);
        }

        return new RaceProblemData(ducks, lanes);

    }
}
