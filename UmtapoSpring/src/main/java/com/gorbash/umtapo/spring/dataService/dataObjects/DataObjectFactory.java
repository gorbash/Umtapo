package com.gorbash.umtapo.spring.dataService.dataObjects;

import com.gorbash.umtapo.jpa.entities.Book;


/**
 * Created by Gorbash on 2015-06-13.
 */
public interface DataObjectFactory {
    BookBrief createBookBrief(Book book);

    BookDetailed createBookDetailed(Book book);
}
