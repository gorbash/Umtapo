package com.gorbash.umtapo.spring.dataService;

import com.gorbash.umtapo.spring.dataService.dataObjects.BookBrief;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookDetailed;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Gorbash on 2015-06-09.
 */
public interface DataService {
    List<BookBrief> getBooksBrief();
    Optional<BookDetailed> getSingleBook(long id);
}
