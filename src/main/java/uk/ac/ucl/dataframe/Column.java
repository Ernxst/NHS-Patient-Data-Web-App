package uk.ac.ucl.dataframe;

import java.util.ArrayList;

public class Column { //represents a dataframe column
    private final String name;
    private final ArrayList<String> rows;

    public Column(String name) {
        this.name = name;
        this.rows = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return rows.size();
    }

    public ArrayList<String> getRows() {
        return rows;
    }

    public String getRowValue(int row) {
        return rows.get(row);
    }

    public void setRowValue(int row, String value) {
        rows.set(row, value);
    }

    public void addRowValue(String value) {
        rows.add(value);
    }

}
