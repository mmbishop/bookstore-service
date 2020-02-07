package com.improving.bookstore.controllers;

import com.improving.bookstore.dto.PurchaseBookRequest;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.BookPurchaseInvoice;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("bookstore")
public class BookstoreController {

    private BookstoreService bookstoreService;

    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @GetMapping(path = "/books", produces = MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        return Response.ok().entity(bookstoreService.getAllBooks()).build();
    }

    @GetMapping(path = "/authors", produces = MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        return Response.ok().entity(bookstoreService.getAllAuthors()).build();
    }

    @GetMapping(path = "/genres", produces = MediaType.APPLICATION_JSON)
    public Response getAllGenres() {
        return Response.ok().entity(bookstoreService.getAllGenres()).build();
    }

    @GetMapping(path = "/booksbytitle", produces = MediaType.APPLICATION_JSON)
    public Response getBooksByTitle(@QueryParam("title") String title) {
        List<Book> booksByTitle = bookstoreService.getBooksByTitle(title);
        if (booksByTitle.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(booksByTitle).build();
    }

    @GetMapping(path = "/booksbyauthor", produces = MediaType.APPLICATION_JSON)
    public Response getBooksByAuthor(@QueryParam("authorFirstName") String authorFirstName, @QueryParam("authorMiddleName") String authorMiddleName,
                                       @QueryParam("authorLastName") String authorLastName) {
        List<Book> booksByAuthor = bookstoreService.getBooksByAuthor(new Author(authorFirstName, authorMiddleName, authorLastName));
        if (booksByAuthor.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(booksByAuthor).build();
    }

    @GetMapping(path = "/booksbygenre", produces = MediaType.APPLICATION_JSON)
    public Response getBooksByGenre(@QueryParam("genre") String genreName) {
        List<Book> booksByGenre = bookstoreService.getBooksByGenre(genreName);
        if (booksByGenre.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(booksByGenre).build();
    }

    @PostMapping(path = "/genre", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Response addGenre(Genre genre) {
        bookstoreService.addGenre(genre);
        return Response.ok().entity(genre).build();
    }

    @PutMapping(path = "/books/{bookId}", consumes = MediaType.APPLICATION_JSON)
    public Response changeSalesPrice(@PathVariable("bookId") int bookId, BigDecimal newPrice) {
        bookstoreService.changeSalesPrice(bookId, newPrice);
        return Response.ok().build();
    }

    @PostMapping(path = "/books", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Response purchaseBook(PurchaseBookRequest purchaseBookRequest) {
        BookPurchaseInvoice invoice = bookstoreService.purchaseBook(getBookFromPurchaseRequest(purchaseBookRequest), purchaseBookRequest.getGenre());
        return Response.ok().entity(invoice).build();
    }

    @PostMapping(path = "/books/{bookId}", produces = MediaType.APPLICATION_JSON)
    public Response sellBook(@PathVariable("bookId") int bookId) {
        Book soldBook = bookstoreService.sellBook(bookId);
        return Response.ok().entity(soldBook).build();
    }

    private Book getBookFromPurchaseRequest(PurchaseBookRequest purchaseBookRequest) {
        Book book = new Book();
        book.setTitle(purchaseBookRequest.getTitle());
        book.setAuthor(new Author(purchaseBookRequest.getAuthorFirstName(), purchaseBookRequest.getAuthorMiddleName(), purchaseBookRequest.getAuthorLastName()));
        book.setPublisher(purchaseBookRequest.getPublisher());
        book.setPublishYear(purchaseBookRequest.getPublishYear());
        book.setIsbn(purchaseBookRequest.getIsbn());
        book.setNumberOfPages(purchaseBookRequest.getNumberOfPages());
        return book;
    }

}
