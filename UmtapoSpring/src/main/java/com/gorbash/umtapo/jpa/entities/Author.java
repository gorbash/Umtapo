package com.gorbash.umtapo.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gorbash on 2015-06-04.
 */
@Entity
@NamedQuery(name = Author.FIND_ALL, query = "from Author a order by a.lastName, a.firstName")
public class Author extends Person {

    public static final String FIND_ALL = "Author.findAll";

    private String psuedo;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

    public Author() {
        super();
    }

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public String getPsuedo() {
        return psuedo;
    }

    public void setPsuedo(String psuedo) {
        this.psuedo = psuedo;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + getFirstName() + '\'' +
                " lastName='" + getLastName() + '\'' +
                " psuedo='" + psuedo + '\'' +
                '}';
    }
}
