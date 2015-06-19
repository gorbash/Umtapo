package com.gorbash.umtapo.spring.dataService.dataObjects;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Gorbash on 2015-06-18.
 */
public class BookDetailed extends BookBrief{

    private LocalDate creationDate;

    public BookDetailed(long id, String title, List<AuthorBrief> authors, LocalDate creationDate) {
        super(id, title, authors);
        this.creationDate = creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
