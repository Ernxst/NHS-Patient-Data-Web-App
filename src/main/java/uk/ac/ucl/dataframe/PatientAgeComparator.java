package uk.ac.ucl.dataframe;

import uk.ac.ucl.model.DataAnalyser;

import java.util.Comparator;
//A comparator to sort patients objects by age
public class PatientAgeComparator implements Comparator<Patient> {
    public int compare(Patient patient1, Patient patient2) {
        String birthDate1 = patient1.getBirthdate();
        String birthDate2 = patient2.getBirthdate();
        int age1 = DataAnalyser.getAge(birthDate1);
        int age2 = DataAnalyser.getAge(birthDate2);
        return Integer.compare(age1, age2);
    }
}
