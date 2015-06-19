package com.gorbash.umtapo.spring.dataService.dataObjects;

/**
 * Created by Gorbash on 2015-06-18.
 */
public class AuthorBrief {

    private String lastName;
    private String firstName;

    public AuthorBrief(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "AuthorBrief{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
