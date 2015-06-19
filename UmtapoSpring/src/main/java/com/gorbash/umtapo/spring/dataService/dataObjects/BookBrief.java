package com.gorbash.umtapo.spring.dataService.dataObjects;

import java.util.List;

/**
 * Created by Gorbash on 2015-06-13.
 */
public class BookBrief {
    private long id;
    private String title;
    private List<AuthorBrief> authors;

    public BookBrief() {

    }

    public BookBrief(long id, String title, List<AuthorBrief> authors) {
        this.id = id;
        this.title = title;
        this.authors = authors;
    }

    public List<AuthorBrief> getAuthors() {
        return authors;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "BookBrief{" +
                "authors=" + authors +
                ", id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public void setAuthors(List<AuthorBrief> authors) {
        this.authors = authors;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
