package com.gorbash.umtapo.spring.controller;

import com.gorbash.umtapo.spring.Application;
import com.gorbash.umtapo.spring.dataService.dataObjects.AuthorBrief;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookBrief;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookDetailed;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Gorbash on 2015-06-15.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BookRestControllerTest {

    private MockMvc mockMvc;

    @Autowired()
    private WebApplicationContext webApplicationContext;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;


    private MockDataService dataService = MockDataService.create();

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        dataService.reset();
    }

    @Test
    public void testThatEmptyDBReturnsCromulentResponce() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    public void testThatRestHandlesBooks() throws Exception {
        final String title = "test title";
        List<AuthorBrief> authors = asList(new AuthorBrief("firstName", "lastName"), new AuthorBrief("firstName2", "lastName2"));
        int id = 1;
        dataService.setBooksBrief(asList(new BookBrief(id, title, authors)));
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(title)))
                .andExpect(jsonPath("$[0].id", is(id)))
                .andExpect(jsonPath("$[0].authors", hasSize(2)))
                .andExpect(jsonPath("$[0].authors[0].firstName", is("firstName")))
                .andExpect(jsonPath("$[0].authors[0].lastName", is("lastName")))
                .andExpect(jsonPath("$[0].authors[1].firstName", is("firstName2")))
                .andExpect(jsonPath("$[0].authors[1].lastName", is("lastName2")));
    }

    @Test
    public void test404WhenBookNotFound() throws Exception {
        mockMvc.perform(get("/books/6")).andExpect(status().isNotFound());

    }

    @Test
    public void testThatRestHandleSingleBook() throws Exception {
        final String title = "test title";
        int id = 1;
        dataService.setSingleBookBrief(Optional.of(new BookDetailed(id, title, asList(), "2015-06-19")));
        mockMvc.perform(get("/books/6")).andExpect(status().isOk()).andExpect(jsonPath("title", is(title)));
    }

    @Test
    @Ignore
    public void testThatRestCanCreateBooks() throws Exception {
        fail();
    }
}


