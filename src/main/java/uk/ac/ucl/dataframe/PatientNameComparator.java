package uk.ac.ucl.dataframe;

import java.util.Comparator;
//A comparator to sort patients objects by full name
public class PatientNameComparator implements Comparator<Patient> {
    public int compare(Patient patient1, Patient patient2) {
        int lastNameCompare = patient1.getLast().compareTo(patient2.getLast());
        int firstNameCompare = patient1.getFirst().compareTo(patient2.getFirst());
        if (lastNameCompare == 0) {
            return ((firstNameCompare == 0) ? lastNameCompare : firstNameCompare);
        }
        return lastNameCompare;
    }
}
