package com.improving.bookstore.controllers;

import javax.ws.rs.Path;
import java.awt.print.Book;
import java.util.List;

@Path("/bookstore")
public class BookController {



    @Path("/books")
    public List<Book> getAllBooks() {
        return null;
    }

}
