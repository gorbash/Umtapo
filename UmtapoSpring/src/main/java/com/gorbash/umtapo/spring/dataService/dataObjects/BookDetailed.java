package com.gorbash.umtapo.spring.dataService.dataObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gorbash on 2015-06-18.
 */
public class BookDetailed extends BookBrief{

    private String creationDate;

    public BookDetailed(long id, String title, List<PersonBrief> authors) {
        super(id, title, authors);
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

}
