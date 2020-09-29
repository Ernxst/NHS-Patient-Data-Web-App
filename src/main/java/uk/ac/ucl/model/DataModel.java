package uk.ac.ucl.model;

import uk.ac.ucl.dataframe.Patient;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

/*
Data model to hide implementation of a dataframe from the UI
Also allows for searching
 */
public interface DataModel {

    void readFile(File file) throws IOException;

    void saveFile(String filename, PrintWriter PrintWriter) throws IOException;

    void editDataframe(String ID, String columnName, String newValue);

    String[] getColumnNames();

    Patient getPatientByRow(int row);

    Patient getPatientByID(String ID);

    int getPatientRow(String ID);

    TreeMap<Character, List<Patient>> getPatients();

    int getColumnIndex(String columnName);

    String getPatientFullName(String id);

    TreeMap<Character, List<Patient>> getPatients(List<Patient> patients);

    LinkedHashMap<String, String> getPatientData(String id);

    TreeMap<Character, List<Patient>> searchFor(List<String> keywords);

    TreeMap<Character, List<Patient>> boundsSearch(String columnName, boolean isMin);

    TreeMap<Character, List<Patient>> rangeSearch(String columnName, String min, String max);

    int[][] search(String searchTerm);

    int[][] search(String columnName, String searchTerm);

    TreeMap<String, LinkedHashMap<String, Integer>> getAnalyticData();
}
