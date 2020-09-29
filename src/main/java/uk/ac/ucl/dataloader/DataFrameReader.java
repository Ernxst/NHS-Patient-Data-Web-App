package uk.ac.ucl.dataloader;

import uk.ac.ucl.dataframe.DataFrame;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
Interface to read data into a common format regardless of file structure
A valid patients.csv/json file must have:
    An 'ID' column
    A 'BIRTHDATE' column
    A 'DEATHDATE' column
    A 'FIRST' column (first name)
    A 'LAST' column (last name)
    An 'ETHNICITY' column
    A 'RACE' column
    A 'GENDER' column
    - Note that date formats must be of the form yyyy/mm/dd
 */

public interface DataFrameReader {
    String getFilename();

    ArrayList<ArrayList<String>> readData(Scanner scanner);

    ArrayList<String> parseLine(String line);

    DataFrame createDataFrame(File dataFile);
}
