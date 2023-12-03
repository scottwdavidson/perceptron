package com.davidsonsw;

public class ScottTester {
    public ScottTester() {
    }

    public String getFullName() {
        return this.lastName.toUpperCase() + ", " + this.firstName.toUpperCase();
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

    private String firstName;

    private String lastName;


}
