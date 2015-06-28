package com.gorbash.umtapo.spring.controller;

import com.gorbash.umtapo.spring.dataService.DataService;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookBrief;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookDetailed;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

/**
 * Created by Gorbash on 2015-06-15.
 */
@Component
public class MockDataService implements DataService {

    private static MockDataService instance;

    private List<BookBrief> bookBrief = asList();
    private Optional<BookDetailed> singleBookBrief;

    public MockDataService() {
        System.out.println("MockDataService()");
    }

    @Override
    public List<BookBrief> getBooksBrief() {
        return bookBrief;
    }

    public void setBooksBrief(List<BookBrief> list) {
        bookBrief = list;
    }

    @Override
    public Optional<BookDetailed> getSingleBook(long id) {
       return singleBookBrief;
    }

    public void setSingleBookBrief(Optional<BookDetailed> book) {
        this.singleBookBrief = book;
    }

    public void reset() {
        bookBrief = asList();
        singleBookBrief = Optional.empty();
    }
}
