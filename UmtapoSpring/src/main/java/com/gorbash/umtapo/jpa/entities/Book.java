package com.gorbash.umtapo.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Gorbash on 2015-06-02.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Book.FIND_ALL, query = "from Book b order by b.title"),
        @NamedQuery(name = Book.FIND_BY_ID, query = "from Book b where id = :id")
})
public class Book {

    public static final String FIND_ALL = "Book.findAll";
    public static final String FIND_BY_ID = "Book.findByID";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String title;

    @OneToMany(mappedBy = "book")
    @NotNull
    private List<Loan> loans = new ArrayList<>();

    @ManyToMany
    private List<Author> authors = new ArrayList<>();

    private LocalDate creationTime = LocalDate.now();

    public Book() {
    }

    public Book(String title) {
        this.title = checkNotNull(title);
    }

    public Book(String title, List<Author> authors) {
        this(title);
        authors.forEach(this::addAuthor);
    }


    public void addAuthor(Author author) {
        authors.add(author);
    }

    public String getTitle() {
        return title;
    }

    public List<Loan> getLoans() {
        return loans;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", loans=" + loans +
                '}';
    }

    public long getId() {
        return id;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }
}
