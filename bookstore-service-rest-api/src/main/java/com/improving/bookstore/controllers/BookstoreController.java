package com.improving.bookstore.controllers;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;

import javax.ws.rs.*;
import java.util.List;

@Path("/bookstore")
public class BookstoreController {

    private BookstoreService bookstoreService;

    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @GET
    @Path("/books")
    public List<Book> getAllBooks() {
        return bookstoreService.getAllBooks();
    }

    @GET
    @Path("/authors")
    public List<Author> getAllAuthors() {
        return bookstoreService.getAllAuthors();
    }

    @GET
    @Path("/genres")
    public List<Genre> getAllGenres() {
        return bookstoreService.getAllGenres();
    }

    @GET
    @Path("/books")
    public List<Book> getBooksByTitle(@QueryParam("title") String title) {
        return bookstoreService.getBooksByTitle(title);
    }

    @GET
    @Path("/books")
    public List<Book> getBooksByAuthor(@QueryParam("authorFirstName") String authorFirstName, @QueryParam("authorMiddleName") String authorMiddleName,
                                       @QueryParam("authorLastName") String authorLastName) {
        return bookstoreService.getBooksByAuthor(new Author(authorFirstName, authorMiddleName, authorLastName));
    }

    @GET
    @Path("/books")
    public List<Book> getBooksByGenre(@QueryParam("genre") String genreName) {
        return bookstoreService.getBooksByGenre(genreName);
    }

    @POST
    @Path("/genre")
    public void addGenre(Genre genre) {
        bookstoreService.addGenre(genre);
    }

}
