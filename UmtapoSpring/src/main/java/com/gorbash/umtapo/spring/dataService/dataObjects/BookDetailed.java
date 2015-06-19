package com.gorbash.umtapo.spring.dataService.dataObjects;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Gorbash on 2015-06-18.
 */
public class BookDetailed extends BookBrief{

    private String creationDate;

    public BookDetailed(long id, String title, List<AuthorBrief> authors, String creationDate) {
        super(id, title, authors);
        this.creationDate = creationDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
