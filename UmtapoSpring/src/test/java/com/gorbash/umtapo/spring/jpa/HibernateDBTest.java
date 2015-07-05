package com.gorbash.umtapo.spring.jpa;

import com.gorbash.umtapo.jpa.db.DBConfig;
import com.gorbash.umtapo.jpa.db.DBSetup;
import com.gorbash.umtapo.jpa.db.URLProvider;
import com.gorbash.umtapo.jpa.entities.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Gorbash on 2015-06-03.
 */
public class HibernateDBTest {

    private static EntityManager em;

    @BeforeClass
    public static void setup() {
        DBConfig config = new DBConfig(URLProvider.TEST_URL);
        config.addProperty("hibernate.hbm2ddl.auto", "create");
        em = new DBSetup(config).getEM();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        em.getEntityManagerFactory().close();
    }

    @Before
    public void cleanup() {
        em.createQuery("from Loan").getResultList().forEach(em::remove);
        em.createQuery("from Book").getResultList().forEach(em::remove);
        em.createQuery("from Person").getResultList().forEach(em::remove);
    }

    @Test
    public void testBookWithAuthors() throws Exception {
        Author terry = new Author("Terry", "Pratchett");
        Author stephen = new Author("Stephen", "Baxter");

        Book longEarth = new Book("Long Earth", Arrays.asList(terry, stephen));
        Book snuff = new Book("Snuff", Arrays.asList(terry));

        runInTransaction(() -> {
            em.persist(terry);
            em.persist(stephen);
            em.persist(longEarth);
            em.persist(snuff);
        });

        em.refresh(terry);
        em.refresh(stephen);
        assertThat(terry.getBooks().size(), is(2));
        assertThat(stephen.getBooks().size(), is(1));
    }

    @Test
    public void simpleTestOfBook() throws Exception {
        String title = "Lord of the rings";

        Book book = new Book(title);
        runInTransaction(() -> em.persist(book));

        List<Book> books = (List<Book>) em.createQuery("from Book where id = " + book.getId()).getResultList();
        assertThat(books.size(), is(1));
        assertThat(books.get(0).getTitle(), is(title));

    }

    private void runInTransaction(Runnable action) {
        em.getTransaction().begin();
        action.run();
        em.getTransaction().commit();
    }

    @Test
    public void testSimpleBookLoan() throws Exception {
        Book book = new Book("Martian");
        Borrower borrower = new Borrower("Magda", "Sztando");

        runInTransaction(() -> {
            em.persist(book);
            em.persist(borrower);
        });


        Loan loan = new Loan(book, borrower);
        runInTransaction(() -> {
            em.persist(loan);
            em.refresh(book);
            em.refresh(borrower);
        });

        assertThat(book.getLoans().size(), is(1));
        assertThat(borrower.getLoans().size(), is(1));
        assertThat(book.getLoans().get(0).getId(), is(loan.getId()));
        assertThat(borrower.getLoans().get(0).getId(), is(loan.getId()));

        loan.returnBook();
        runInTransaction(() -> em.merge(loan));
        assertNotNull(loan.getReturnDate());


        runInTransaction(() -> {
            em.remove(loan);
            em.refresh(book);
        });
        assertThat(book.getLoans().size(), is(0));
    }

    @Test
    public void testPerson() throws Exception {

        String authorFirstName = "radek";
        String authorLastName = "sztando";

        String borrowerFirstName = "magda";
        String borrowerLastName = "sztando";

        Author author = new Author(authorFirstName, authorLastName);
        author.setPsuedo("gorbash");
        Borrower borrower = new Borrower(borrowerFirstName, borrowerLastName);
        borrower.setEmail("magdalenapto@gmail.com");
        runInTransaction(() -> {
            em.persist(author);
            em.persist(borrower);
        });


        List<Author> authorList = em.createQuery("from Author where id = " + author.getId()).getResultList();
        assertThat(authorList.size(), is(1));
        assertThat(authorList.get(0).getFirstName(), is(authorFirstName));
        assertThat(authorList.get(0).getLastName(), is(authorLastName));

        List<Borrower> borrowerList = em.createQuery("from Borrower where id = " + borrower.getId()).getResultList();
        assertThat(1, is(borrowerList.size()));
        assertThat(borrowerList.get(0).getFirstName(), is(borrowerFirstName));
        assertThat(borrowerList.get(0).getLastName(), is(borrowerLastName));


        List<Person> personList = em.createQuery("from Person where id = " + borrower.getId() + " or id = " + author.getId()).getResultList();
        assertThat(personList.size(), is(2));

        runInTransaction(() -> {
            em.remove(author);
            em.remove(borrower);
        });

        personList = (List<Person>) em.createQuery("from Person where id = " + borrower.getId() + " or id = " + author.getId()).getResultList();
        assertTrue(personList.isEmpty());
    }
}
