package uk.ac.ucl.dataloader;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONReader extends DataLoader {
    private ArrayList<String> columnNames = new ArrayList<>();

    public JSONReader(String filename) {
        super(filename);
    }

    public JSONReader() {
        super();
    }

    private String getFileData(Scanner scanner) {
        StringBuilder inputData = new StringBuilder();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            inputData.append(tidyString(line));
        }
        //remove enclosing braces before parsing
        return inputData.toString().substring(1, inputData.length()-2);
    }

    public ArrayList<ArrayList<String>> readData(Scanner scanner) {
        String inputData = getFileData(scanner);
        ArrayList<ArrayList<String>> jsonData = new ArrayList<>();
        //split data into its subgroups which are enclosed by {}
        String[] splitData = inputData.split("\\{");
        for (int x=1; x<splitData.length; x++) {
            ArrayList<String> rowData = parseLine(splitData[x]);
            jsonData.add(rowData);
            if (x==1) { addColumnNames(splitData[x]); }
        }
        jsonData.add(0, columnNames);
        return jsonData;
    }

    public ArrayList<String> parseLine(String line) {
        return parseLine(line, 1);
    }

    private ArrayList<String> parseLine(String line, int index) {
        ArrayList<String> rowData = new ArrayList<>();
        //split each patient into its key value pairs
        for (String s : line.split(",")) {
            //split string into a key and a value which are colon separated
            String[] columnAndData = s.split(":");
            String field = columnAndData[index];
            rowData.add(extractStringFromQuotes(field));
        }
        return rowData;
    }

    //Extract a string from quotes using a regex
    private String extractStringFromQuotes(String field) {
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(field);
        String parsedField = "";
        if (m.find()) {
            parsedField = m.group(1);
        }
        return parsedField;
    }

    private void addColumnNames(String inputData) {
        if (columnNames.size() == 0) { //only add column names once for performance
            columnNames = parseLine(inputData, 0);
            columnNames.replaceAll(String::toUpperCase);
        }
    }

    /*
    Remove whitespace
    Note that some strings in the dataframe are separated by two spaces
    so only replace sequences of 3 spaces or more
     */
    private String tidyString(String str) { //remove whitespace
        return str.replaceAll("\\s\\s\\s+", "");
    }
}
