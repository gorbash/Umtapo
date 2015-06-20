package com.gorbash.umtapo.spring.dataService.dataObjects;

/**
 * Created by Gorbash on 2015-06-18.
 */
public class PersonBrief {

    private long id;
    private String lastName;
    private String firstName;

    public PersonBrief(long id, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PersonBrief{" +
                "firstName='" + firstName + '\'' +
                ", id=" + id +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
