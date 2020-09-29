package uk.ac.ucl.dataframe;

import java.util.*;

public class DataFrame {
    private final LinkedHashMap<String, Column> columns;
    //Key: patient id, value: patient object
    private  LinkedHashMap<String, Patient> patients;
    private final String name;

    public DataFrame() {
        this("MyDataFrame");
    }

    public DataFrame(String name) {
        columns = new LinkedHashMap<>();
        patients = new LinkedHashMap<>();
        this.name = name;
    }

    public void setPatients(LinkedHashMap<String, Patient> patients) {
        this.patients = patients;
    }

    public String getName() {
        return this.name;
    }

    public void addColumn(String columnName) {
        Column newColumn = new Column(columnName);
        columns.put(columnName, newColumn);
    }

    public void addPatient(Patient patient) {
        this.patients.put(patient.getId(), patient);
    }

    public Patient getPatient(String id) {
        if (this.patients.containsKey(id)) {
            return patients.get(id);
        }
        return null;
    }

    public LinkedHashMap<String, Patient> getPatients() {
        return this.patients;
    }

    public Collection<Patient> getPatientList() {
        return this.patients.values();
    }

    public Column[] getColumns() {
        return columns.values().toArray(new Column[0]); }

    public Column getColumn(String columnName) {
        return columns.get(columnName);
    }

    public int getColumnIndex(String columnName) {
        String[] columnNames = getColumnNames();
        if (Arrays.asList(columnNames).contains(columnName)) {
            return Arrays.asList(columnNames).indexOf(columnName);
        }
        return -1;
    }

    public String[] getColumnNames() {
        ArrayList<String> names = new ArrayList<>(columns.keySet());
        int size = names.size();
        String[] columnNames = new String[size];
        for (int x=0; x<size; x++) {
            columnNames[x] = names.get(x);
        }
        return columnNames;
    }

    public int getRowCount(String columnName) {
        return columns.get(columnName).getSize();
    }

    public String getValue(String columnName, int row) {
        return columns.get(columnName).getRowValue(row);
    }

    public void putValue(String columnName, int row, String value) {
        columns.get(columnName).setRowValue(row, value);
    }

    public void addValue(String columnName, String value) {
       columns.get(columnName).addRowValue(value);
    }

}
