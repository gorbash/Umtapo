package com.gorbash.umtapo.spring.dataService;

import com.gorbash.umtapo.jpa.entities.Author;
import com.gorbash.umtapo.jpa.entities.Book;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookBrief;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookDetailed;
import com.gorbash.umtapo.spring.dataService.dataObjects.DataObjectFactory;
import com.gorbash.umtapo.spring.dataService.dataObjects.RestDataObjectFactory;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Gorbash on 2015-06-20.
 */
public class RestDataObjectFactoryTest {

    private DataObjectFactory obj = new RestDataObjectFactory();

    @Test
    public void testThatCreatesCorrectBriefBook() throws Exception {
        Book book = new Book("title", Arrays.asList(new Author("first1", "last1"), new Author("first2", "last2")));
        BookBrief brief = obj.createBookBrief(book);
        assertThat(brief.getTitle(), is("title"));
        assertThat(brief.getAuthors(), hasSize(2));
        assertThat(brief.getId(), is(book.getId()));
        assertThat(brief.getAuthors().get(0).getFirstName(), is("first1"));
        assertThat(brief.getAuthors().get(0).getLastName(), is("last1"));
    }


    @Test
    public void testThatCreateBookDetailed() throws Exception {
        Book book = new Book("title", Arrays.asList(new Author("first1", "last1"), new Author("first2", "last2")));

        BookDetailed bookDetailed = obj.createBookDetailed(book);
        assertThat(bookDetailed.getTitle(), is("title"));
        assertThat(bookDetailed.getId(), is (book.getId()));
        assertThat(bookDetailed.getCreationDate(), notNullValue());
        assertThat(bookDetailed.getAuthors(), hasSize(2));
        assertThat(bookDetailed.getAuthors().get(0).getFirstName(), is("first1"));
        assertThat(bookDetailed.getAuthors().get(0).getLastName(), is("last1"));
    }
}
