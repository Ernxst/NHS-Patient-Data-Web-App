package uk.ac.ucl.datawriter;/*
Interface for a filewriter that can write dataFrame data to a file
in a specified file structure
 */
import java.io.File;
import java.io.PrintWriter;

public interface DataFrameWriter {

    boolean saveToFile(File file, String data, PrintWriter fileWriter);

    String getString();

    String buildString(String[][] data);
}

