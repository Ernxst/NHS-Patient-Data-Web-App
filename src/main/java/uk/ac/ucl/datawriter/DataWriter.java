package uk.ac.ucl.datawriter;

import uk.ac.ucl.dataframe.Column;
import uk.ac.ucl.dataframe.DataFrame;

import java.io.File;
import java.io.PrintWriter;

/*
Abstract class allowing for either a CSV or JSON writer subclass
Program detects which file extension is being used and instantiates
appropriate writer object
The two subclasses only differ in how they format data to be written
THe two subclasses build a string which is written to the file
 */

public abstract class DataWriter implements DataFrameWriter {
    protected final DataFrame dataFrame;
    protected final int INDENT;
    private final String fileExtension;

    public DataWriter(DataFrame dataFrame, int indent, String fileExtension) {
        this.dataFrame = dataFrame;
        this.INDENT = indent;
        this.fileExtension = fileExtension;
    }

    private String[][] getData() {
        //converts dataframe data into 2d array
        Column[] columns = dataFrame.getColumns();
        int cols = columns.length;
        int rows = columns[0].getSize();
        String[][] data = new String[rows][cols];
        for (int row=0; row<rows; row++) {
            int col = 0;
            String[] patientData = new String[cols];
            for (Column column : columns) {
                patientData[col] = column.getRowValue(row);
                col++;
            }
            data[row] = patientData;
        }
        return data;
    }

    public boolean saveToFile(File file, String data, PrintWriter fileWriter) {
        boolean validFilename = isValidFilename(file.getName());
        if (!validFilename) { return false; }
        // FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(data);
        fileWriter.close();
        return true;
    }

    private boolean isValidFilename(String filename) {
        return filename.endsWith(fileExtension);
    }

    public String getString() { return buildString(getData()); }

    protected String getIndent(int depth) { //prepends spaces to represent indent
        return new String(new char[depth * INDENT]).replace("\0", " ");
    }
}
