package com.gorbash.umtapo.jpa.entities;

import javax.persistence.*;
import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Gorbash on 2015-06-05.
 */
@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOAN_ID")
    private int loanID;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Borrower borrower;

    private LoanStatus status;

    private LocalDate loanDate;

    private LocalDate returnDate;

    public Loan() {

    }

    public Loan(Book book, Borrower borrower) {
        this(book, borrower, LocalDate.now());
    }

    public Loan(Book book, Borrower borrower, LocalDate loanDate) {
        this.book = checkNotNull(book);
        this.borrower = checkNotNull(borrower);
        this.loanDate = checkNotNull(loanDate);
        status = LoanStatus.NOT_RETURNED;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public int getId() {
        return loanID;
    }

    public Book getBook() {
        return book;
    }

    public void returnBook(LocalDate returnDate) {
        this.returnDate = checkNotNull(returnDate);
        if (status != LoanStatus.NOT_RETURNED)
            throw new RuntimeException("Cannot return already returned book");

        status = LoanStatus.RETURNED;
    }

    public void returnBook() {
        returnBook(LocalDate.now());
    }

    public boolean isReturned() {
        return status == LoanStatus.RETURNED;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanID=" + loanID +
                ", book=" + book.getTitle() +
                ", borrower=" + borrower.getFirstName() + " " + borrower.getLastName() +
                ", status=" + status +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }


    public static enum LoanStatus {
        NOT_RETURNED, RETURNED
    }
}
