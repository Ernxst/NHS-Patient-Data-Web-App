package uk.ac.ucl.dataframe;

import java.util.Arrays;
import java.util.List;

public class Patient {
    /* Represents one row of the dataframe - a patient */
    private String id, birthdate, deathdate, ssn, drivers, passport;
    private String prefix, first, last, suffix, maiden;
    private String marital, race, ethnicity, gender;
    private String birthplace, address, city, state, zip;
    private final List<String> allData; //stores all above data but makes it less verbose to retrieve all at once

    public Patient(String id, String birthdate, String deathdate, String ssn, String drivers, String passport,
                   String prefix, String first, String last, String suffix, String maiden, String marital,
                   String race, String ethnicity, String gender, String birthplace, String address, String city,
                   String state, String zip, String[] data) {
        this.id = id;
        this.birthdate = birthdate;
        this.deathdate = deathdate;
        this.passport = passport;
        this.suffix = suffix;
        this.maiden = maiden;
        this.marital = marital;
        this.birthplace = birthplace;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.prefix = prefix;
        this.first = first;
        this.last = last;
        this.ssn = ssn;
        this.drivers = drivers;
        this.race = race;
        this.ethnicity = ethnicity;
        this.gender = gender;
        this.allData = Arrays.asList(data);
    }

    public String toString() {
        return getFullName();
    }

    public void setData(int index, String value) {
        this.allData.set(index, value);
    }

    public List<String> getAllData() {
        return this.allData;
    }

    public String getFullName() {
        return this.last + ", " + this.first;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getDeathdate() {
        return this.deathdate;
    }

    public void setDeathdate(String deathdate) {
        this.deathdate = deathdate;
    }

    public String getSsn() {
        return this.ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getDrivers() {
        return this.drivers;
    }

    public void setDrivers(String drivers) {
        this.drivers = drivers;
    }

    public String getPassport() {
        return this.passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFirst() {
        return this.first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return this.last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getMaiden() {
        return this.maiden;
    }

    public void setMaiden(String maiden) {
        this.maiden = maiden;
    }

    public String getMarital() {
        return this.marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getRace() {
        return this.race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getEthnicity() {
        return this.ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthplace() {
        return this.birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
