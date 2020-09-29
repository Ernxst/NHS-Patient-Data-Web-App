package uk.ac.ucl.datawriter;

import uk.ac.ucl.dataframe.DataFrame;

import java.util.StringJoiner;

public class CSVWriter extends DataWriter {

    public CSVWriter(DataFrame dataFrame, int indent) {
        super(dataFrame, indent, ".csv");
    }

    public String buildString(String[][] data) {
        StringJoiner outData = new StringJoiner("");
        outData.merge(addHeaders());
        outData.add("\n");
        for (String[] patient : data) {
            StringJoiner patientString = new StringJoiner(",");
            for (String field : patient) {
                /*
                Data Model shows blank data as N/A, need to revert this before saving
                 */
                if (field.equals("N/A")) {
                    field = "";
                }
                patientString.add(field);
            }
            patientString.add("\n");
            outData.add(patientString.toString());
        }
        return outData.toString();
    }

    private StringJoiner addHeaders() {
        String[] columnNames = dataFrame.getColumnNames();
        StringJoiner headers = new StringJoiner(",");
        for (String column : columnNames) {
            headers.add(column);
        }
        return headers;
    }
}
