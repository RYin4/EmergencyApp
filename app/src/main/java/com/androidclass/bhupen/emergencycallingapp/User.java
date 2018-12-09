package com.androidclass.bhupen.emergencycallingapp;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String homeAddress;
    private String emergencyContact;
    private String medicalInformation;

    public User() {

    }

    public User(String firstName, String lastName, String homeAddress, String emergencyContact, String medicalInformation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emergencyContact = emergencyContact;
        this.medicalInformation = medicalInformation;
    }


    public User(String firstName, String lastName, String email, String password, String homeAddress, String emergencyContact, String medicalInformation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.emergencyContact = emergencyContact;
        this.medicalInformation = medicalInformation;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getMedicalInformation() {
        return medicalInformation;
    }

    public void setMedicalInformation(String medicalInformation) {
        this.medicalInformation = medicalInformation;
    }

}
