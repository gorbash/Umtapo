package com.gorbash.umtapo.jpa.dataAccess;

import com.gorbash.umtapo.jpa.entities.Author;
import com.gorbash.umtapo.jpa.entities.Book;

import java.util.List;
import java.util.Optional;

/**
 * Created by Gorbash on 2015-06-09.
 */
public interface DataAccess {
    List<Book> getBooks();

    List<Author> getAuthors();

    Optional<Book> getBook(long id);
}
