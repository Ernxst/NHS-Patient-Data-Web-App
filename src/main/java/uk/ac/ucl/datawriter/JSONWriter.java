package uk.ac.ucl.datawriter;

import uk.ac.ucl.dataframe.DataFrame;

import java.util.Arrays;

public class JSONWriter extends DataWriter{

    public JSONWriter(DataFrame dataFrame, int indent) {
        super(dataFrame, indent, ".json");
    }

    public String buildString(String[][] jsonData) {
        //builds json string with indenting
        int patients = jsonData.length;
        int maxCol = jsonData[0].length - 1;
        String[] columnNames = dataFrame.getColumnNames();

        StringBuilder outData = new StringBuilder("{");
        outData.append("\n").append(getIndent(1)).append("\"").append(dataFrame.getName()).append("\": [");
        //outer for loop to build a json string containing all patients
        for (String[] patient : jsonData) {
            outData.append("\n").append(getIndent(2)).append("{\n");
            buildInnerString(outData, columnNames, patient, maxCol);
            outData.append("\n").append(getIndent(2)).append("}");
            if (!(Arrays.asList(jsonData).indexOf(patient) == patients-1)) {
                outData.append(",");
            }
        }
        outData.append("\n").append(getIndent(1)).append("]\n}");
        return outData.toString();
    }

    private void buildInnerString(StringBuilder outData, String[] columnNames,
                                  String[] patient, int maxCol) {
        int col = 0;
        for (String column : columnNames) {
            int depth = 3;
            outData.append(getIndent(depth));
            String field = patient[col];
            if (field.equals("N/A")) {
                field = "";
            }
            outData.append("\"").append(column.toLowerCase()).append("\": \"").append(field).append("\"");
            if (col < maxCol) {
                outData.append(",\n");
            }
            col++;
        }
    }
}
