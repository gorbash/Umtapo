package com.gorbash.umtapo.spring.dataService;

import com.gorbash.umtapo.jpa.dataAccess.DataAccess;
import com.gorbash.umtapo.jpa.entities.Book;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookBrief;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookDetailed;
import com.gorbash.umtapo.spring.dataService.dataObjects.DataObjectFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by Gorbash on 2015-06-09.
 */
@Service
public class JPADataService implements DataService {

    private final Logger logger = Logger.getLogger(JPADataService.class);
    private DataAccess dataAccess;

    private DataObjectFactory objectFactory;

    @Autowired
    public JPADataService(DataAccess dataAccess, DataObjectFactory objectFactory) {
        this.objectFactory = checkNotNull(objectFactory);
        this.dataAccess = checkNotNull(dataAccess);

        logger.info("JPA data service created");
    }

    @Override
    public List<BookBrief> getBooksBrief() {
        return dataAccess.getBooks().stream().map(book -> objectFactory.createBookBrief(book)).collect(Collectors.toList());
    }

    @Override
    public Optional<BookDetailed> getSingleBook(long id) {
        Optional<Book> bookOption = dataAccess.getBook(id);
        return bookOption.isPresent() ? of(objectFactory.createBookDetailed(bookOption.get())) : empty();
    }
}
