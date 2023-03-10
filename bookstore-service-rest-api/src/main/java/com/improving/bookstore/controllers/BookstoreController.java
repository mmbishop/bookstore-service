package com.improving.bookstore.controllers;

import com.improving.bookstore.dto.BookSalesPriceRequest;
import com.improving.bookstore.dto.PurchaseBookRequest;
import com.improving.bookstore.dto.PurchaseBookResponse;
import com.improving.bookstore.interactors.BookNotFoundException;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.BookPurchaseInvoice;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.List;

@Component
@RestController
@RequestMapping("bookstore")
public class BookstoreController {

    private BookstoreService bookstoreService;

    @Autowired
    public BookstoreController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @RequestMapping(path = "/books", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Book> getAllBooks() {
        return bookstoreService.getAllBooks();
    }

    @RequestMapping(path = "/authors", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Author> getAllAuthors() {
        return bookstoreService.getAllAuthors();
    }

    @RequestMapping(path = "/genres", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Genre> getAllGenres() {
        return bookstoreService.getAllGenres();
    }

    @RequestMapping(path = "/books/title/{title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Book> getBooksByTitle(@PathVariable("title") String title) {
        List<Book> booksByTitle = bookstoreService.getBooksByTitle(title);
        if (booksByTitle.isEmpty()) {
            throw new BookNotFoundException("No book with title " + title + " was found.");
        }
        return booksByTitle;
    }

    @RequestMapping(path = "/books/author", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Book> getBooksByAuthor(@RequestParam("firstName") String authorFirstName, @RequestParam("middleName") String authorMiddleName,
                                       @RequestParam("lastName") String authorLastName) {
        List<Book> booksByAuthor = bookstoreService.getBooksByAuthor(authorFirstName, authorMiddleName.length() > 0 ? authorMiddleName : null, authorLastName);
        if (booksByAuthor.isEmpty()) {
            throw new BookNotFoundException("No books written by " + authorFirstName + " " + authorLastName + " were found.");
        }
        return booksByAuthor;
    }

    @RequestMapping(path = "/books/genre/{genre}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public List<Book> getBooksByGenre(@PathVariable("genre") String genreName) {
        List<Book> booksByGenre = bookstoreService.getBooksByGenre(genreName);
        if (booksByGenre.isEmpty()) {
            throw new BookNotFoundException("No books in genre " + genreName + " were found.");
        }
        return booksByGenre;
    }

    @RequestMapping(path = "/genre", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public Genre addGenre(@RequestBody Genre genre) {
        return bookstoreService.addGenre(genre);
    }

    @RequestMapping(path = "/genre/{genreName}", method = RequestMethod.DELETE)
    public void deleteGenre(@PathVariable("genreName") String genreName) {
        bookstoreService.deleteGenre(genreName);
    }

    @RequestMapping(path = "/books/{bookId}/onsale", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON)
    public void putBookOnSale(@PathVariable("bookId") int bookId, @RequestBody BookSalesPriceRequest bookSalesPriceRequest) {
        bookstoreService.putBookOnSale(bookId, bookSalesPriceRequest.getNewPrice());
    }

    @RequestMapping(path = "/books/{bookId}/offsale", method = RequestMethod.PUT)
    public void takeBookOffSale(@PathVariable("bookId") int bookId) {
        bookstoreService.takeBookOffSale(bookId);
    }

    @RequestMapping(path = "/books", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public PurchaseBookResponse purchaseBook(@RequestBody PurchaseBookRequest purchaseBookRequest) {
        BookPurchaseInvoice invoice = bookstoreService.purchaseBook(getBookFromPurchaseRequest(purchaseBookRequest),
                getAuthorFromPurchaseRequest(purchaseBookRequest), purchaseBookRequest.getGenre());
        return new PurchaseBookResponse(invoice);
    }

    @RequestMapping(path = "/books/{bookId}/sell", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public Book sellBook(@PathVariable("bookId") int bookId) {
        return bookstoreService.sellBook(bookId);
    }

    private Book getBookFromPurchaseRequest(PurchaseBookRequest purchaseBookRequest) {
        Book book = new Book();
        book.setTitle(purchaseBookRequest.getTitle());
        book.setPublisher(purchaseBookRequest.getPublisher());
        book.setPublishYear(purchaseBookRequest.getPublishYear());
        book.setIsbn(purchaseBookRequest.getIsbn());
        book.setNumberOfPages(purchaseBookRequest.getNumberOfPages());
        return book;
    }

    private Author getAuthorFromPurchaseRequest(PurchaseBookRequest purchaseBookRequest) {
        return new Author(purchaseBookRequest.getAuthorFirstName(), purchaseBookRequest.getAuthorMiddleName(), purchaseBookRequest.getAuthorLastName());
    }

}
