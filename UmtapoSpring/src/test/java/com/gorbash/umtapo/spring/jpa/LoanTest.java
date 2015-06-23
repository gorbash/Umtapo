package com.gorbash.umtapo.spring.jpa;

import com.gorbash.umtapo.jpa.entities.Book;
import com.gorbash.umtapo.jpa.entities.Borrower;
import com.gorbash.umtapo.jpa.entities.Loan;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Gorbash on 2015-06-05.
 */
public class LoanTest {

    @Test
    public void testSimpleLoanLifeCycle() throws Exception {
        Book book = new Book("Casino Royale");
        Borrower borrower = new Borrower("radek", "sztando");

        LocalDate borrowDate = LocalDate.now();
        Loan loan = new Loan(book, borrower, borrowDate);

        assertThat(loan.isReturned(), is(false));
        assertThat(loan.getLoanDate(), is(borrowDate));
        loan.returnBook();
        assertThat(loan.isReturned(), is(true));
        assertNotNull(loan.getReturnDate());
    }


    @Test(expected = RuntimeException.class)
    public void testIncorrectStatus() {
        Book book = new Book("Casino Royale");
        Borrower borrower = new Borrower("radek", "sztando");
        Loan loan = new Loan(book, borrower);
        loan.returnBook();
        loan.returnBook();
    }


}
