package com.gorbash.umtapo.spring.jpa;

import com.gorbash.umtapo.jpa.dataAccess.DataAccess;
import com.gorbash.umtapo.jpa.dataAccess.HibernateDataAccess;
import com.gorbash.umtapo.jpa.db.DBConfig;
import com.gorbash.umtapo.jpa.db.DBSetup;
import com.gorbash.umtapo.jpa.entities.Author;
import com.gorbash.umtapo.jpa.entities.Book;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Gorbash on 2015-06-09.
 */
public class HibernateDataAccessTest {

    private static DataAccess da;
    private static DBSetup setup;
    private Book mars;

    @BeforeClass
    public static void beforeClass() throws Exception {
        DBConfig config = new DBConfig("jdbc:hsqldb:file:testdb");
        //config = new DBConfig("jdbc:hsqldb:hsql://localhost/");
        config.addProperty("hibernate.hbm2ddl.auto", "create");
        setup = new DBSetup(config);
        da = new HibernateDataAccess(setup);
    }

    private static EntityManager em() {
        return setup.getEM();
    }

    private static void commitTransaction() {
        setup.getEM().getTransaction().commit();
    }

    private static void startTransaction() {
        setup.getEM().getTransaction().begin();
    }

    public static void persist(Object... obj) {
        asList(obj).forEach(o -> setup.getEM().persist(o));

    }

    @AfterClass
    public static void tearDown() throws Exception {
        setup.getEM().getEntityManagerFactory().close();
    }

    private void cleanup() {
        em().createQuery("from Book").getResultList().forEach(r -> em().remove(r));
        em().createQuery("from Person").getResultList().forEach(r -> em().remove(r));
    }

    private void setupSomeObjects() {
        Author kosik = new Author("Rafał", "Kosik");
        Author ketchum = new Author("Jack", "Ketchum");
        Author pratchett = new Author("Terry", "Pratchett");
        Author baxter = new Author("Stephen", "Baxter");

        mars = new Book("Mars");
        mars.addAuthor(kosik);

        Book potomstwo = new Book("Potomstwo");
        potomstwo.addAuthor(ketchum);

        Book snuff = new Book("Snuff", asList(pratchett));

        Book longEarth = new Book("Long earth", asList(pratchett, baxter));


        startTransaction();
        persist(kosik, pratchett, ketchum, baxter);
        persist(mars, snuff, potomstwo, longEarth);
        commitTransaction();
    }

    @Before
    public void setUp() throws Exception {
        cleanup();
        setupSomeObjects();
    }

    @Test
    public void testThatDACanObtainBooks() throws Exception {
        assertThat(da.getBooks().size(), is(4));
        assertThat(da.getBooks().get(0).getTitle(), is("Long earth"));
    }

    @Test
    public void testThatDACanObtainAuthors() throws Exception {
        assertThat(da.getAuthors().size(), is(4));
        assertThat(da.getAuthors().get(0).getLastName(), is("Baxter"));
    }

    @Test
    public void testThatDACanObtainBookByID() throws Exception {
        assertThat(da.getBook(mars.getId()).get().getTitle(), is(mars.getTitle()));
    }

    @Test
    public void testThatDAReturnsEmptyOptionalWhenNoBookOfID() throws Exception {
        assertThat(da.getBook(Integer.MAX_VALUE), is(Optional.empty()));
    }
}
