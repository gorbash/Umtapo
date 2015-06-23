package com.gorbash.umtapo.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gorbash on 2015-06-04.
 */
@Entity
public class Borrower extends Person {

    private String email;

    @OneToMany(mappedBy = "borrower")
    private List<Loan> loans;

    public Borrower(String firstName, String lastName) {
        super(firstName, lastName);
        loans = new ArrayList<>();
    }

    public Borrower() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    @Override
    public String toString() {
        return "Borrower{" +
                "firstName='" + getFirstName() + '\'' +
                " lastName='" + getLastName() + '\'' +
                " email='" + email + '\'' +
                ", loans=" + loans +
                '}';
    }
}
