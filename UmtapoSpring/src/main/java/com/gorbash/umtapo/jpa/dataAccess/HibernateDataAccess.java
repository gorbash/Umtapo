package com.gorbash.umtapo.jpa.dataAccess;

import com.gorbash.umtapo.jpa.db.DBSetup;
import com.gorbash.umtapo.jpa.entities.Author;
import com.gorbash.umtapo.jpa.entities.Book;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by Gorbash on 2015-06-09.
 */
@Component
public class HibernateDataAccess implements DataAccess {
    private final Logger log = Logger.getLogger(HibernateDataAccess.class);
    private DBSetup dbSetup;
    private EntityManager em;

    @Autowired
    public HibernateDataAccess(DBSetup dbSetup) {
        log.info("Hibernate Data Access created");
        this.dbSetup = checkNotNull(dbSetup);
        this.em = checkNotNull(dbSetup.getEM());
    }


    private <T> T runWithLog(String operationName, Supplier<T> supplier) {
        T result = supplier.get();
        log.info(String.format("%s:%s", operationName, result));
        return result;
    }

    @Override
    public List<Book> getBooks() {
        return runWithLog("getBooks", () -> em.createNamedQuery(Book.FIND_ALL).getResultList());
    }

    @Override
    public List<Author> getAuthors() {
        return runWithLog("getAuthors", () -> em.createNamedQuery(Author.FIND_ALL).getResultList());
    }

    @Override
    public Optional<Book> getBook(long id) {
        return (Optional<Book>) runWithLog("getBook id:" + id, () -> {
            List<Book> list = em.createNamedQuery(Book.FIND_BY_ID).setParameter("id", id).getResultList();
            return list.isEmpty() ? empty() : of(list.get(0));
        });
    }

}
