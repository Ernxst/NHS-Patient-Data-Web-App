package uk.ac.ucl.dataloader;

import uk.ac.ucl.dataframe.Column;
import uk.ac.ucl.dataframe.DataFrame;
import uk.ac.ucl.dataframe.Patient;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
Abstract class allowing for either a CSV or JSON reader subclass
Program detects which file extension is being used and instantiates
appropriate reader object
The two subclasses only differ in how they read data from files
THe two subclasses read data into a common format for use in the super class
 */
public abstract  class DataLoader implements DataFrameReader {
    protected String filename;

    public DataLoader(String filename) {
        this.filename = filename;
    }

    public DataLoader() {}

    public String getFilename() {
        return filename;
    }

    private Scanner openFile()  {
        try {
            File file = new File(filename);
            return new Scanner(file); }
        catch (FileNotFoundException | NullPointerException e) { return null; }
    }

    //Allows a default dataframe to be created
    public DataFrame createDataFrame() {
        Scanner scanner = openFile();
        if (scanner != null) {
            return intialiseDataFrame(scanner);
        }
        return null;
    }

    //Function handling the creation of a dataframe and creating patients
    private DataFrame intialiseDataFrame(Scanner scanner) {
        String dataFrameName = filename.substring(0, filename.indexOf(".")).toLowerCase();
        DataFrame dataFrame = new DataFrame(dataFrameName);
        ArrayList<ArrayList<String>> data = readData(scanner);
        ArrayList<String> columnNames = data.get(0);
        data.remove(0); //remove column headers
        createColumns(columnNames, dataFrame);
        addData(data, dataFrame);
        addPatients(dataFrame);
        return dataFrame;
    }

    //Function handling exceptions
    public DataFrame createDataFrame(File dataFile) {
        try {
            Scanner scanner = new Scanner(dataFile);
            filename = dataFile.getName();
            return intialiseDataFrame(scanner);
        }
        catch (FileNotFoundException e) {
            return new DataFrame("EmptyDataFrame");
        }
    }

    //Creates column objects from the first line of the csv file or keys in a json file
    private void createColumns(ArrayList<String> columnNames, DataFrame dataFrame) {
        for (String columnName : columnNames) { dataFrame.addColumn(columnName); }
    }

    /* A dataframe is column-based whereas a patient is a row so a separate function
    for dealing with rows of data is needed
     */
    public void addPatients(DataFrame dataFrame) {
        Column[] columns = dataFrame.getColumns();
        int cols = columns.length;
        int rows = columns[0].getSize();
        for (int x=0; x<rows; x++) {
            String[] patientData = new String[cols];
            int y = 0;
            for (Column column : columns) {
                patientData[y] = column.getRowValue(x);
                y++;
            }
            Patient patient = createPatient(patientData);
            dataFrame.addPatient(patient);
        }
    }

    //Returns a patient object
    private Patient createPatient(String[] data) {
        String id = data[0];
        String birthdate = data[1];
        String deathdate = data[2];
        String ssn = data[3];
        String drivers = data[4];
        String passport = data[5];
        String prefix = data[6];
        String first = data[7];
        String last = data[8];
        String suffix = data[9];
        String maiden = data[10];
        String marital = data[11];
        String race = data[12];
        String ethnicity = data[13];
        String gender = data[14];
        String birthplace = data[15];
        String address = data[16];
        String city = data[17];
        String state = data[18];
        String zip = data[19];
        return new Patient(id, birthdate, deathdate, ssn, drivers, passport, prefix,
                first,  last, suffix, maiden, marital, race, ethnicity, gender,
                birthplace, address, city, state, zip, data);
    }

    //Add data to the dataframe column by column, inserting all rows for a column before moving to the next
    private void addData(ArrayList<ArrayList<String>> csvData, DataFrame dataFrame) {
        int col = 0;
        int rows = csvData.size();
        String[] columns = dataFrame.getColumnNames();
        for (String columnName : columns) {
            int row = 0;
            while (row < rows) {
                ArrayList<String> patient = csvData.get(row);
                String field = getFieldFromRow(patient, col);
                dataFrame.addValue(columnName, field);
                row++;
            }
            col++;
        }
    }

    //method to get a field, handling the event of missing data
    private String getFieldFromRow(ArrayList<String> row, int column) {
        try { return row.get(column); }
        catch (IndexOutOfBoundsException e) { return ""; }
    }
}
