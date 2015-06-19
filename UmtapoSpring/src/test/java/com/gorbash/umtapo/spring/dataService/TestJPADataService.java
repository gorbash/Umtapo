package com.gorbash.umtapo.spring.dataService;

import com.gorbash.umtapo.jpa.dataAccess.DataAccess;
import com.gorbash.umtapo.jpa.entities.Author;
import com.gorbash.umtapo.jpa.entities.Book;
import com.gorbash.umtapo.spring.dataService.dataObjects.AuthorBrief;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookBrief;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookDetailed;
import com.gorbash.umtapo.spring.dataService.dataObjects.DataObjectFactory;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by Gorbash on 2015-06-14.
 */
public class TestJPADataService {

    private DataService ds;
    private DataObjectFactory factory;
    private Book book1;
    private Book book2;

    @Before
    public void setUp() throws Exception {

        Author jrr = new Author("J.R.R", "Tolkien");
        Author winston = new Author("Winston", "Groom");
        book1 = new Book("Forrest Gump", Arrays.asList(winston));
        book2 = new Book("Lord of the Rings", Arrays.asList(jrr));
        AuthorBrief tolkien = new AuthorBrief("J.R.R", "Tolkien");
        AuthorBrief groom = new AuthorBrief("Winston", "Groom");
        BookBrief bBook1 = new BookBrief(1, book1.getTitle(), Arrays.asList(tolkien));
        BookBrief bBook2 = new BookBrief(2, book2.getTitle(), Arrays.asList(groom));
        BookDetailed dBook1 = new BookDetailed(book1.getId(), book1.getTitle(), Arrays.asList(tolkien), book1.getCreationTime());
        BookDetailed dBook2 = new BookDetailed(book2.getId(), book2.getTitle(), Arrays.asList(groom), book2.getCreationTime());


        DataAccess da = new DataAccess() {
            @Override
            public List<Book> getBooks() {
                return Arrays.asList(book1, book2);
            }

            @Override
            public List<Author> getAuthors() {
                return Arrays.asList(jrr, winston);
            }

            @Override
            public Optional<Book> getBook(long id) {
                switch ((int) id) {
                    case 1:
                        return of(book1);
                    case 2:
                        return of(book2);
                    default:
                        return empty();
                }
            }
        };

        factory = mock(DataObjectFactory.class);
        when(factory.createBookBrief(book1)).thenReturn(bBook1);
        when(factory.createBookBrief(book2)).thenReturn(bBook2);
        when(factory.createBookDetailed(book1)).thenReturn(dBook1);
        when(factory.createBookDetailed(book2)).thenReturn(dBook2);
        ds = new JPADataService(da, factory);

    }

    @Test
    public void testThatDataServiceProvideListOfBooks() throws Exception {
        List<BookBrief> booksBrief = ds.getBooksBrief();
        assertThat(booksBrief.size(), is(2));
        assertThat(booksBrief.get(0).getTitle(), is("Forrest Gump"));
        assertThat(booksBrief.get(1).getTitle(), is("Lord of the Rings"));

        verify(factory).createBookBrief(book1);
        verify(factory).createBookBrief(book2);
    }


    @Test
    public void testThatServiceProvidesBookByID() throws Exception {
        assertThat(ds.getSingleBook(1).get().getTitle(), is("Forrest Gump"));
        assertThat(ds.getSingleBook(2).get().getTitle(), is("Lord of the Rings"));
    }

    @Test
    public void testThatServiceProvidesEmptyOption() {
        assertThat(ds.getSingleBook(666), is(empty()));
    }
}