package com.example.covid19tracker.CovidVaccineLocation.Model;

public class VaccineModel {

    private String vaccineCenter;
    private String vaccinationAge;
    private String vaccinationTiming;
    private String vaccineName;
    private String vaccineCenterTime;
    private String vaccinationAddress;
    private String vaccineAvailable;

    public VaccineModel() {

    }

    public VaccineModel(String vaccineCenter, String vaccinationAge, String vaccinationTiming, String vaccineName, String vaccineCenterTime, String vaccinationAddress, String vaccineAvailable) {
        this.vaccineCenter = vaccineCenter;
        this.vaccinationAge = vaccinationAge;
        this.vaccinationTiming = vaccinationTiming;
        this.vaccineName = vaccineName;
        this.vaccineCenterTime = vaccineCenterTime;
        this.vaccinationAddress = vaccinationAddress;
        this.vaccineAvailable = vaccineAvailable;
    }

    public String getVaccineCenter() {
        return vaccineCenter;
    }

    public void setVaccineCenter(String vaccineCenter) {
        this.vaccineCenter = vaccineCenter;
    }

    public String getVaccinationAge() {
        return vaccinationAge;
    }

    public void setVaccinationAge(String vaccinationAge) {
        this.vaccinationAge = vaccinationAge;
    }

    public String getVaccinationTiming() {
        return vaccinationTiming;
    }

    public void setVaccinationTiming(String vaccinationTiming) {
        this.vaccinationTiming = vaccinationTiming;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getVaccineCenterTime() {
        return vaccineCenterTime;
    }

    public void setVaccineCenterTime(String vaccineCenterTime) {
        this.vaccineCenterTime = vaccineCenterTime;
    }

    public String getVaccinationAddress() {
        return vaccinationAddress;
    }

    public void setVaccinationAddress(String vaccinationAddress) {
        this.vaccinationAddress = vaccinationAddress;
    }

    public String getVaccineAvailable() {
        return vaccineAvailable;
    }

    public void setVaccineAvailable(String vaccineAvailable) {
        this.vaccineAvailable = vaccineAvailable;
    }
}
