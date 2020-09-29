package uk.ac.ucl.model;

import uk.ac.ucl.dataframe.DataFrame;
import uk.ac.ucl.dataframe.Patient;
import uk.ac.ucl.dataframe.PatientAgeComparator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

public class DataAnalyser extends Model {
    private final DataFrame dataFrame;

    public DataAnalyser(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    private List<Integer> getAllAges() {
        List<Patient> alivePatients = getAlivePatients();
        List<Integer> ages = new ArrayList<>();
        for (Patient patient : alivePatients) {
            int age = getAge(patient.getBirthdate());
            ages.add(age);
        }
        return ages;
    }

    public double getAverageAge() {
        List<Integer> ages = getAllAges();
        int len = ages.size();
        if (len != 0) {
            double sum = 0.0;
            for (int age : ages) {
                sum += age;
            }
            return Math. round((sum/len)*10) / 10.0; //round to 1dp
        }
        return -1.0;
    }

    public Patient getYoungestOrOldestPatient(boolean isYoungest) {
        return ageBoundSearch(isYoungest);
    }

    private Patient ageBoundSearch(boolean isYoungest) {
        List<Patient> alivePatients = getAlivePatients();
        if (alivePatients.size() > 0) {
            List<Patient> sortedPatients = new ArrayList<>(alivePatients);
            sortedPatients.sort(new PatientAgeComparator());
            int index = (isYoungest) ? (sortedPatients.size() - 1) : (0);
            return sortedPatients.get(index);
        }
        return null;
    }

    public int getYoungestOrOldest(boolean isYoungest) {
        Patient patient = ageBoundSearch(isYoungest);
        if (patient != null) {
            return getAge(patient.getBirthdate());
        }
        return -1;
    }

    public List<Patient> getAlivePatients() {
        List<Patient> alivePatients = new ArrayList<>();
        Collection<Patient> patients = dataFrame.getPatientList();
        for (Patient patient : patients) {
            if (isAlive(patient)) {
                alivePatients.add(patient);
            }
        }
        return alivePatients;
    }

    private boolean isAlive(Patient patient) {
        return (patient.getDeathdate().equals("N/A") || patient.getDeathdate().length() == 0);
    }

    public static int getAge(String birthDate) {
        String[] dateParts = birthDate.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        LocalDate startDate = LocalDate.of (year, month, day);
        LocalDateTime currentDate = LocalDateTime.now();
        return Period.between(startDate, LocalDate.from(currentDate)).getYears();
    }

    public LinkedHashMap<String, Integer> getColumnDistribution(String columnName) {
        return getColumnDistribution(dataFrame.getColumn(columnName).getRows());
    }

    private LinkedHashMap<String, Integer> getColumnDistribution(List<String> columnValues) {
        Set<String> uniqueValues = new HashSet<>(columnValues);
        LinkedHashMap<String, Integer> distribution = new LinkedHashMap<>();
        for (String value : uniqueValues) {
            int number = Collections.frequency(columnValues, value);
            distribution.put(value, number);
        }
        return distribution;
    }

    public LinkedHashMap<String, Integer> getAgeDistribution() {
        return getAgeRangeFrequencies(getAllAges());
    }

    private LinkedHashMap<String, Integer> setupMap() {
        LinkedHashMap<String, Integer> distribution = new LinkedHashMap<>();
        distribution.put("N/A", 0);
        distribution.put("0-9", 0);
        for (int x=1; x<10; x++) {
            String key = x + "0-" + x + "9";
            distribution.put(key, 0);
        }
        distribution.put("100+", 0);
        return distribution;
    }

    private LinkedHashMap<String, Integer> getAgeRangeFrequencies(List<Integer> values) {
        LinkedHashMap<String, Integer> distribution = setupMap();
        for (Integer value : values) {
            String key = getKey(value);
            int frequency = distribution.get(key);
            distribution.put(key, frequency + 1);
        }
        return distribution;
    }

    private String getKey(Integer value) {
        if (value < 0) {
            return "N/A";
        }
        String intString = value.toString();
        String key;
        switch (intString.length()) {
            case 1:
                key = "0-9";
                break;
            case 2:
                Character firstChar = intString.charAt(0);
                key = firstChar + "0-" + firstChar + "9";
                break;
            case 3:
                key = "100+";
                break;
            default:
                key = "N/A";
                break;
        }
        return key;
    }
}
