package org.example.reader;

import java.io.IOException;

public interface DataReader {

    RaceProblemData readData(String file_input) throws IOException;

}
