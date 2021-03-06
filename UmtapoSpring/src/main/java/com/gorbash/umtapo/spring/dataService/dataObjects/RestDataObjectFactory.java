package com.gorbash.umtapo.spring.dataService.dataObjects;

import com.gorbash.umtapo.jpa.entities.Author;
import com.gorbash.umtapo.jpa.entities.Book;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Gorbash on 2015-06-13.
 */
@Service
public class RestDataObjectFactory implements DataObjectFactory {

    @Override
    public BookBrief createBookBrief(Book book) {
        return new BookBrief(book.getId(), book.getTitle(),mapAuthors(book.getAuthors()));
    }

    @Override
    public BookDetailed createBookDetailed(Book book) {
        BookDetailed result = new BookDetailed(book.getId(), book.getTitle(),mapAuthors(book.getAuthors()));
        result.setCreationDate(getStringDateFromLocalDate(book.getCreationTime()));
        return result;
    }


    private List<PersonBrief> mapAuthors(List<Author> authors) {
        return authors.stream().map(a -> new PersonBrief(a.getId(), a.getFirstName(), a.getLastName())).collect(Collectors.toList());
    }

    private String getStringDateFromLocalDate(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
