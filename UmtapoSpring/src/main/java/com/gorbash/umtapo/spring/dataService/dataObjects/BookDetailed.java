package com.gorbash.umtapo.spring.dataService.dataObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gorbash on 2015-06-18.
 */
public class BookDetailed extends BookBrief{

    private String creationDate;
    private List<Loan> loans;

    public BookDetailed(long id, String title, List<PersonBrief> authors) {
        super(id, title, authors);
        loans = new ArrayList<>();
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}
