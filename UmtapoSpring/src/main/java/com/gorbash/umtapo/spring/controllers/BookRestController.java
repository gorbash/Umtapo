package com.gorbash.umtapo.spring.controllers;

import com.gorbash.umtapo.spring.dataService.DataService;
import com.gorbash.umtapo.spring.dataService.JPADataService;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookBrief;
import com.gorbash.umtapo.spring.dataService.dataObjects.BookDetailed;
import com.gorbash.umtapo.spring.dataService.dataObjects.PersonBrief;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Created by Gorbash on 2015-06-15.
 */
@RestController
@SpringApplicationConfiguration
@ComponentScan(basePackages = {"com.gorbash.umtapo"})
@RequestMapping("/books")
public class BookRestController {

    private final Logger logger = Logger.getLogger(BookRestController.class);

    private DataService dataService;

    @Autowired
    public void setDataService(DataService dataService) {
        logger.info("Setting up data service: " + dataService);
        this.dataService = dataService;
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<BookBrief>> getBooks() {
        logger.info("GET books");
        return new ResponseEntity<>(dataService.getBooksBrief(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    ResponseEntity<BookDetailed> getBook(@PathVariable long bookId) {
        logger.info("GET book id: " + bookId);
        Optional<BookDetailed> quantumBook = dataService.getSingleBook(bookId);
        return quantumBook.isPresent() ? new ResponseEntity<>(quantumBook.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    ResponseEntity<BookBrief> createBook(@RequestBody BookBrief book) {
        logger.info("POST books: " + book);
        book.setId(666);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

}
