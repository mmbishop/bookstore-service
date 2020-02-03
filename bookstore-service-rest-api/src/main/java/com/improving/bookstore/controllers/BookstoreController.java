package com.improving.bookstore.controllers;

import com.improving.bookstore.dto.PurchaseBookRequest;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.BookPurchaseInvoice;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@Path("/bookstore")
public class BookstoreController {

    private BookstoreService bookstoreService;

    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        return Response.ok().entity(bookstoreService.getAllBooks()).build();
    }

    @GET
    @Path("/authors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAuthors() {
        return Response.ok().entity(bookstoreService.getAllAuthors()).build();
    }

    @GET
    @Path("/genres")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGenres() {
        return Response.ok().entity(bookstoreService.getAllGenres()).build();
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksByTitle(@QueryParam("title") String title) {
        List<Book> booksByTitle = bookstoreService.getBooksByTitle(title);
        if (booksByTitle.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(booksByTitle).build();
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksByAuthor(@QueryParam("authorFirstName") String authorFirstName, @QueryParam("authorMiddleName") String authorMiddleName,
                                       @QueryParam("authorLastName") String authorLastName) {
        List<Book> booksByAuthor = bookstoreService.getBooksByAuthor(new Author(authorFirstName, authorMiddleName, authorLastName));
        if (booksByAuthor.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(booksByAuthor).build();
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooksByGenre(@QueryParam("genre") String genreName) {
        List<Book> booksByGenre = bookstoreService.getBooksByGenre(genreName);
        if (booksByGenre.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().entity(booksByGenre).build();
    }

    @POST
    @Path("/genre")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addGenre(Genre genre) {
        bookstoreService.addGenre(genre);
        return Response.ok().entity(genre).build();
    }

    @PUT
    @Path("/books/{bookId}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response changeSalesPrice(@PathParam("bookId") int bookId, BigDecimal newPrice) {
        bookstoreService.changeSalesPrice(bookId, newPrice);
        return Response.ok().build();
    }

    @POST
    @Path("/books")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response purchaseBook(PurchaseBookRequest purchaseBookRequest) {
        BookPurchaseInvoice invoice = bookstoreService.purchaseBook(getBookFromPurchaseRequest(purchaseBookRequest), purchaseBookRequest.getGenre());
        return Response.ok().entity(invoice).build();
    }

    @POST
    @Path("/books/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sellBook(@PathParam("bookId") int bookId) {
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
