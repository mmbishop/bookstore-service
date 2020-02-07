package com.improving.bookstore.controllers;

import com.improving.bookstore.dto.PurchaseBookRequest;
import com.improving.bookstore.dto.PurchaseBookResponse;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.BookPurchaseInvoice;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookstoreService.getAllBooks());
    }

    @GetMapping(path = "/authors", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(bookstoreService.getAllAuthors());
    }

    @GetMapping(path = "/genres", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(bookstoreService.getAllGenres());
    }

    @GetMapping(path = "/booksbytitle", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam("title") String title) {
        List<Book> booksByTitle = bookstoreService.getBooksByTitle(title);
        if (booksByTitle.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(booksByTitle);
    }

    @GetMapping(path = "/booksbyauthor", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("firstName") String authorFirstName, @RequestParam("middleName") String authorMiddleName,
                                       @RequestParam("lastName") String authorLastName) {
        List<Book> booksByAuthor = bookstoreService.getBooksByAuthor(authorFirstName, authorMiddleName.length() > 0 ? authorMiddleName : null, authorLastName);
        if (booksByAuthor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(booksByAuthor);
    }

    @GetMapping(path = "/booksbygenre", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Book>> getBooksByGenre(@RequestParam("genre") String genreName) {
        List<Book> booksByGenre = bookstoreService.getBooksByGenre(genreName);
        if (booksByGenre.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(booksByGenre);
    }

    @PostMapping(path = "/genre", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        bookstoreService.addGenre(genre);
        return ResponseEntity.ok(genre);
    }

    @PutMapping(path = "/books/{bookId}", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> changeSalesPrice(@PathVariable("bookId") int bookId, @RequestBody Book book) {
        bookstoreService.changeSalesPrice(bookId, book.getPrice());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/books", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PurchaseBookResponse> purchaseBook(@RequestBody PurchaseBookRequest purchaseBookRequest) {
        BookPurchaseInvoice invoice = bookstoreService.purchaseBook(getBookFromPurchaseRequest(purchaseBookRequest), purchaseBookRequest.getGenre());
        return ResponseEntity.ok(new PurchaseBookResponse(invoice));
    }

    @PostMapping(path = "/books/{bookId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Book> sellBook(@PathVariable("bookId") int bookId) {
        Book soldBook = bookstoreService.sellBook(bookId);
        return ResponseEntity.ok(soldBook);
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
