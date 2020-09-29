package uk.ac.ucl.dataloader;

import uk.ac.ucl.dataloader.DataLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CSVReader extends DataLoader {

    public CSVReader(String filename) { super(filename); }

    public CSVReader() { super(); }

    public ArrayList<ArrayList<String>> readData(Scanner scanner) {
        ArrayList<ArrayList<String>> csvData = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            ArrayList<String> parsedLine = parseLine(line);
            csvData.add(parsedLine);
        }
        return csvData;
    }

    public ArrayList<String> parseLine(String line) {
        String[] row = line.split(",");
        return new ArrayList<>(Arrays.asList(row));
    }
}
