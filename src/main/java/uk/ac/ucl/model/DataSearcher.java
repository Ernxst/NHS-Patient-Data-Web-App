package uk.ac.ucl.model;

import uk.ac.ucl.dataframe.Column;
import uk.ac.ucl.dataframe.DataFrame;
import uk.ac.ucl.dataframe.Patient;

import java.util.*;

public class DataSearcher {
    private final DataFrame dataFrame;

    public DataSearcher(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    //Return a list of all patients in the given age range
    public List<Patient> ageRangeSearch(int min, int max, List<Patient> alivePatients) {
        List<Patient> result = new ArrayList<>();
        for (Patient patient : alivePatients) {
            String birthDate = patient.getBirthdate();
            int age = DataAnalyser.getAge(birthDate);
            if (age >= min && age <= max) {
                result.add(patient);
            }
        }
        return result;
    }

    //Return a list of integers representing row numbers of all values in a column that are in the range
    public List<Integer> rangeSearch(String columnName, String min, String max) {
        List<Integer> result = new ArrayList<>();
        Column column = dataFrame.getColumn(columnName);
        int maxRows = column.getSize();
        for (int x=0; x<maxRows; x++) {
            String value = dataFrame.getValue(columnName, x);
            if ((value.compareTo(min) == 0) || (value.compareTo(max) == 0) ||
                    (value.compareTo(min) > 0 && value.compareTo(max) < 0)) {
                result.add(x);
            }
        }
        return result;
    }

    //Return a list of patients who match the search term
    public List<Patient> searchFor(List<String> keywords) {
        List<Patient> result = new ArrayList<>();
        List<Patient> patients = new ArrayList<>(dataFrame.getPatientList());
        for (Patient patient : patients) {
            List<String> patientData = patient.getAllData();
            if (patientData.containsAll(keywords)) {
                result.add(patient);
            }
        }
        return result;
    }

    //return array of 2-tuples containing cell locations (row, col)
    public int[][] searchAll(String searchTerm) {
        String[] columns = dataFrame.getColumnNames();
        int[][] allCells = new int[dataFrame.getRowCount(columns[0])][2];
        for (int x=0; x<columns.length; x++) {
            ArrayList<Integer> cellsInColumn = searchByColumn(columns[x], searchTerm);
            if (cellsInColumn.size() > 0) {
                for (Integer rowIndex : cellsInColumn) {
                    int[] cell = {rowIndex, x};
                    allCells[x] = cell;
                }
                return allCells;
            }
        }
        return new int[0][2];
    }

    //return array of indices of the search term in the column (if any)
    public ArrayList<Integer> searchByColumn(String columnName, String searchTerm) {
        String[] columns = dataFrame.getColumnNames();
        if (Arrays.asList(columns).contains(columnName)) {
            Column column = dataFrame.getColumn(columnName);
            return findAllCells(column.getRows(), searchTerm);
        }
        return new ArrayList<>();
    }

    public int[][] getMinOrMax(String columnName, boolean isMin) {
        ArrayList<String> columnValues = getColumnValues(columnName);
        ArrayList<String> sortedValues = new ArrayList<>(columnValues);
        if (columnValues.size() > 0) {
            Collections.sort(sortedValues);
            int index = (isMin) ? (0) : (sortedValues.size()-1);
            int row = getNearestNonEmpty(sortedValues, columnValues, index);
            int[] cell = {row, dataFrame.getColumnIndex(columnName)};
            return new int[][]{cell};
        }
        return new int[0][0];
    }

    //return either the smallest or largest nonempty value
    private int getNearestNonEmpty(ArrayList<String> sortedValues, ArrayList<String> values, int index) {
        String value = sortedValues.get(index);
        int len = sortedValues.size();
        if (value.equals("")) {
            boolean found = false;
            int step = 1;
            if (index == len-1) { step = -1; }
            int x = index + step;
            while ((x >= 0) && (x < len)) {
                value = sortedValues.get(x);
                if (!(value.equals("") || value.equals("N/A"))) {
                    found = true;
                    break;
                }
                x += step;
            }
            if (found) { index = x; }
        }
        value = sortedValues.get(index);
        return values.indexOf(value);
    }

    private ArrayList<String> getColumnValues(String columnName) {
        Column column = dataFrame.getColumn(columnName);
        return column.getRows();
    }

    //Find all cells in a given column that match the search term and return a list of their row numbers
    private ArrayList<Integer> findAllCells(ArrayList<String> columnValues, String searchTerm) {
        ArrayList<Integer> allCells = new ArrayList<>();
        for (int x=0; x<columnValues.size(); x++) {
            String field = columnValues.get(x);
            if (field.equals(searchTerm)) {
                allCells.add(x);
            }
        }
        return allCells;
    }
}
