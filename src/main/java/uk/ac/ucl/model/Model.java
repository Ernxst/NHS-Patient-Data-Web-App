package uk.ac.ucl.model;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import uk.ac.ucl.dataframe.*;
import uk.ac.ucl.dataloader.CSVReader;
import uk.ac.ucl.dataloader.DataLoader;
import uk.ac.ucl.dataloader.JSONReader;
import uk.ac.ucl.datawriter.CSVWriter;
import uk.ac.ucl.datawriter.DataWriter;
import uk.ac.ucl.datawriter.JSONWriter;

import java.io.*;
import java.util.*;

public class Model implements DataModel {
    private DataLoader dataLoader;
    private DataFrame dataFrame;
    private File dataFile;

    private void setDataLoader(DataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    private void setDataFile(File file) {
        this.dataFile = file;
    }

    private void setDataFrame() {
        dataFrame = dataLoader.createDataFrame(dataFile);
    }

    public Patient getPatientByID(String ID) {
        return dataFrame.getPatient(ID);
    }

    public Patient getPatientByRow(int row) {
        String ID = dataFrame.getValue("ID", row);
        return getPatientByID(ID);
    }

    //Return the row number of a patient in the dataframe given their ID
    public int getPatientRow(String ID) {
        int[][] cell = search("ID", ID);
        int[] patientPosition = cell[0]; //row, column
        return patientPosition[1];
    }


    public String getPatientFullName(String id) {
        Patient patient = getPatientByID(id);
        return patient.getFullName();
    }

    //Get a column header and pair it with the corresponding data for a single patient
    public LinkedHashMap<String, String> getPatientData(String id) {
        Patient patient = getPatientByID(id);
        List<String> allData = patient.getAllData();
        String[] columns = this.dataFrame.getColumnNames();
        int len = allData.size();
        LinkedHashMap<String, String> patientData = new LinkedHashMap<>();

        for (int x = 0; x < len; x++) {
            String data = formatData(allData.get(x));
            patientData.put(columns[x], data);
        }
        return patientData;
    }

    // Show N/A for missing data instead of blanks
    private String formatData(String data) {
        if (data.length() == 0) {
            return "N/A";
        }
        return data;
    }

    public String[] getColumnNames() {
        return dataFrame.getColumnNames();
    }

    public int getColumnIndex(String columnName) {
        return dataFrame.getColumnIndex(columnName);
    }

    //If a list of patients (e.g. from search results) is not supplied, display all patients
    public TreeMap<Character, List<Patient>> getPatients() {
        return getPatients(new ArrayList<>(dataFrame.getPatientList()));
    }

    /*Get all patient objects in alphabetical order
        Key: character
        Value: patient objects of patients whose surname start with that character */
    public TreeMap<Character, List<Patient>> getPatients(List<Patient> patients) {
        TreeMap<Character, List<Patient>> allPatients = new TreeMap<>();
        for (Patient patient : patients) {
            char firstChar = patient.getLast().charAt(0);
            List<Patient> patientsWithChar = getPatientsStartingWith(firstChar, allPatients);
            patientsWithChar.add(patient);
            allPatients.put(firstChar, patientsWithChar);
        }
        sortPatients(allPatients);
        return allPatients;
    }

    protected List<Patient> getPatientsStartingWith(Character character, TreeMap<Character, List<Patient>> allPatients) {
        //If key is already in map, get it so the next patient can be added to it
        if (allPatients.containsKey(character)) {
            return allPatients.get(character);
        }
        //If key not in map, create a new list to store patients whose last name begin with first char
        return new ArrayList<>();
    }

    //Sort patients alphabetically using custom comparator
    protected void sortPatients(TreeMap<Character, List<Patient>> patients) {
        for (List<Patient> patientList : patients.values()) {
            patientList.sort(new PatientNameComparator());
        }
    }

    /*
    Function to ensure uniqueness of IDs is upheld otherwise
    the dataframe logic fails
     */
    private boolean checkID(String columnName, String newValue) {
        if (columnName.equals("ID")) {
            int[][] cell = search("ID", newValue);
            if (cell.length > 0) {
                //Show error message instead
                System.out.println("IDs must be unique! This ID is already in the dataframe");
                return false;
            }
        }
        return true;
    }

    public void editDataframe(String ID, String columnName, String newValue) {
        //Only remove data if the user has explicitly instructed to do
        if (newValue.length() == 0) {
            return;
        }
        if (!checkID(columnName, newValue)) {
            return;
        }
        if (newValue.equals("N/A")) { //Allows user to delete data - saved to file as blank
            newValue = "";
        }
        updateDataframe(ID, columnName, newValue);
    }

    private void updateDataframe(String ID, String columnName, String newValue) {
        int patientRow = getPatientRow(ID);
        dataFrame.putValue(columnName, patientRow, newValue);
        Patient patient = getPatientByID(ID);
        int row = getColumnIndex(columnName);
        patient.setData(row, newValue);
    }

    //Convert InputStream (from Servlet) to a File object
    public File createFile(String filename, InputStream fileContent) throws IOException {
        File file = new File(filename);
        FileOutputStream outputStream = new FileOutputStream(file);
        IOUtils.copy(fileContent, outputStream);
        fileContent.close();
        outputStream.close();
        return file;
    }

    //Reject any file that is not a .csv or .json file
    private boolean isValidFileExtension(File file) {
        String fileExtension = getFileExtension(file);
        switch (fileExtension) {
            case "csv":
                setDataLoader(new CSVReader(file.getName()));
                return true;
            case "json":
                setDataLoader(new JSONReader(file.getName()));
                return true;
            default:
                return false;
        }
    }

    private String getFileExtension(File file) {
        String filename = file.getName().toLowerCase();
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public void readFile(File file) throws IOException {
        // Read a patient data file and create the DataFrame.
        if (file.isFile()) {
            if (isValidFileExtension(file)) {
                setDataFile(file);
                setDataFrame();
            } else {
                //invalid file extension
                throw new IOException("Only CSV or JSON files can be imported");
            }
        } else {
            throw new IOException("File " + file.getName() + " could not be found");
        }
    }

    //Overloading to allow either a filename or a file object
    public void saveFile(String filename, PrintWriter PrintWriter) throws IOException {
        saveFile(new File(filename), PrintWriter);
    }

    private void saveFile(File file, PrintWriter PrintWriter) throws IOException {
        DataWriter dataWriter;
        String fileExtension = getFileExtension(file);
        switch (fileExtension) {
            case "csv":
                dataWriter = new CSVWriter(dataFrame, 2);
                break;
            case "json":
                dataWriter = new JSONWriter(dataFrame, 2);
                break;
            default:
                throw new IOException("You can only save as CSV or JSON");
        }
        saveWithWriter(dataWriter, file, PrintWriter);
    }

    private void saveWithWriter(DataWriter dataWriter, File file, PrintWriter PrintWriter) throws IOException {
        String dataString = dataWriter.getString();
        boolean saved = dataWriter.saveToFile(file, dataString, PrintWriter);
        if (!saved) {
            throw new IOException("File could not be saved to " + file.getName());
        }
    }

    public TreeMap<Character, List<Patient>> searchFor(List<String> keywords) {
        DataSearcher searcher = new DataSearcher(this.dataFrame);
        //Replace all blank fields with N/A to stop the searcher incorrectly matching a patient
        Collections.replaceAll(keywords, "", "N/A");
        List<Patient> result = searcher.searchFor(keywords);
        return getPatients(result);
    }

    /*
      Adapted from cw part 2:
        Used row, column combination to highlight a cell in JTable
        Now, use the same function but take the row from each cell
        and find the corresponding patient
     */

    private TreeMap<Character, List<Patient>> ageBoundsSearch(boolean isYoungest) {
        DataAnalyser dataAnalyser = new DataAnalyser(dataFrame);
        Patient patient = dataAnalyser.getYoungestOrOldestPatient(isYoungest);
        List<Patient> result = Collections.singletonList(patient);
        return getPatients(result);
    }

    public TreeMap<Character, List<Patient>> boundsSearch(String columnName, boolean isMin) {
        if (columnName.equals("age")) {
            return ageBoundsSearch(isMin);
        }
        else {
            return doBoundsSearch(columnName, isMin);
        }
    }

    private TreeMap<Character, List<Patient>> doBoundsSearch(String columnName, boolean isMin) {
        List<Patient> result = new ArrayList<>();
        int[][] cells = getMinOrMax(columnName, isMin);
        for (int[] patientCell : cells) {
            int row = patientCell[0];
            Patient patient = getPatientByRow(row);
            result.add(patient);
        }
        return getPatients(result);
    }

    private int[][] getMinOrMax(String columnName, boolean isMin) {
        DataSearcher searcher = new DataSearcher(dataFrame);
        return searcher.getMinOrMax(columnName, isMin);
    }

    private TreeMap<Character, List<Patient>> ageRangeSearch(String min, String max) {
        int minimum = Integer.parseInt(min);
        int maximum = Integer.parseInt(max);
        DataAnalyser dataAnalyser = new DataAnalyser(dataFrame);
        List<Patient> alivePatients = dataAnalyser.getAlivePatients();
        DataSearcher dataSearcher = new DataSearcher(dataFrame);
        List<Patient> result = dataSearcher.ageRangeSearch(minimum, maximum, alivePatients);
        return getPatients(result);
    }

    public TreeMap<Character, List<Patient>> rangeSearch(String columnName, String min, String max) {
        if (columnName.equals("age")) {
            return ageRangeSearch(min, max);
        }
        else {
            return doRangeSearch(columnName, min, max);
        }
    }

    private TreeMap<Character, List<Patient>> doRangeSearch(String columnName, String min, String max) {
        DataSearcher searcher = new DataSearcher(dataFrame);
        List<Integer> matchingRows = searcher.rangeSearch(columnName, min, max);
        List<Patient> result = new ArrayList<>();
        for (Integer row : matchingRows) {
            result.add(getPatientByRow(row));
        }
        return getPatients(result);
    }

    //Returns an array of 2-tuples representing row,column cells of data matching the search term
    public int[][] search(String searchTerm) {
        DataSearcher searcher = new DataSearcher(dataFrame);
        return searcher.searchAll(searchTerm);
    }

    //Overloading to allow for an optional columnname
    public int[][] search(String columnName, String searchTerm) {
        DataSearcher searcher = new DataSearcher(dataFrame);
        ArrayList<Integer> allCells = searcher.searchByColumn(columnName, searchTerm);
        int[][] cells = new int[allCells.size()][2];
        int columnIndex = getColumnIndex(columnName);
        for (int x = 0; x < allCells.size(); x++) {
            int row = allCells.get(x);
            cells[x] = new int[]{row, columnIndex};
        }
        return cells;
    }

    public TreeMap<String, LinkedHashMap<String, Integer>> getAnalyticData() {
        TreeMap<String, LinkedHashMap<String, Integer>> data = new TreeMap<>();
        data.put("AGES", getAgeBounds());
        String[] columnNames = new String[]{"ETHNICITY", "RACE", "GENDER"};
        for (String column : columnNames) {
            data.put(column, getColumnDistribution(column));
        }
        data.put("AGE DISTRIBUTION", getAgeDistribution());
        return data;
    }

    private LinkedHashMap<String, Integer> getAgeBounds() {
        LinkedHashMap<String, Integer> ages = new LinkedHashMap<>();
        ages.put("Youngest", getYoungest());
        ages.put("Oldest", getOldest());
        ages.put("Average", (int) getAverageAge());
        return ages;
    }

    private LinkedHashMap<String, Integer> getAgeDistribution() {
        DataAnalyser dataAnalyser = new DataAnalyser(dataFrame);
        return dataAnalyser.getAgeDistribution();
    }

    private int getYoungest() {
        DataAnalyser dataAnalyser = new DataAnalyser(dataFrame);
        return dataAnalyser.getYoungestOrOldest(false);
    }

    private int getOldest() {
        DataAnalyser dataAnalyser = new DataAnalyser(dataFrame);
        return dataAnalyser.getYoungestOrOldest(true);
    }

    private double getAverageAge() {
        DataAnalyser dataAnalyser = new DataAnalyser(dataFrame);
        return dataAnalyser.getAverageAge();
    }

    private LinkedHashMap<String, Integer> getColumnDistribution(String columnName) {
        DataAnalyser dataAnalyser = new DataAnalyser(dataFrame);
        return dataAnalyser.getColumnDistribution(columnName);
    }
}
