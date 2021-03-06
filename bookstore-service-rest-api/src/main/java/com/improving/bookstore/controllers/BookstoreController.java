package com.improving.bookstore.controllers;

import com.improving.bookstore.dto.BookSalesPriceRequest;
import com.improving.bookstore.dto.PurchaseBookRequest;
import com.improving.bookstore.dto.PurchaseBookResponse;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.BookPurchaseInvoice;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "/books/title/{title}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable("title") String title) {
        List<Book> booksByTitle = bookstoreService.getBooksByTitle(title);
        if (booksByTitle.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(booksByTitle);
    }

    @GetMapping(path = "/books/author", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam("firstName") String authorFirstName, @RequestParam("middleName") String authorMiddleName,
                                       @RequestParam("lastName") String authorLastName) {
        List<Book> booksByAuthor = bookstoreService.getBooksByAuthor(authorFirstName, authorMiddleName.length() > 0 ? authorMiddleName : null, authorLastName);
        if (booksByAuthor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(booksByAuthor);
    }

    @GetMapping(path = "/books/genre/{genre}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable("genre") String genreName) {
        List<Book> booksByGenre = bookstoreService.getBooksByGenre(genreName);
        if (booksByGenre.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(booksByGenre);
    }

    @PostMapping(path = "/genre", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        return ResponseEntity.ok(bookstoreService.addGenre(genre));
    }

    @DeleteMapping(path = "/genre/{genreName}")
    public ResponseEntity<?> deleteGenre(@PathVariable("genreName") String genreName) {
        bookstoreService.deleteGenre(genreName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/books/{bookId}/onsale", consumes = MediaType.APPLICATION_JSON)
    public ResponseEntity<?> putBookOnSale(@PathVariable("bookId") int bookId, @RequestBody BookSalesPriceRequest bookSalesPriceRequest) {
        bookstoreService.putBookOnSale(bookId, bookSalesPriceRequest.getNewPrice());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/books/{bookId}/offsale")
    public ResponseEntity<?> takeBookOffSale(@PathVariable("bookId") int bookId) {
        bookstoreService.takeBookOffSale(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/books", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PurchaseBookResponse> purchaseBook(@RequestBody PurchaseBookRequest purchaseBookRequest) {
        BookPurchaseInvoice invoice = bookstoreService.purchaseBook(getBookFromPurchaseRequest(purchaseBookRequest),
                getAuthorFromPurchaseRequest(purchaseBookRequest), purchaseBookRequest.getGenre());
        return ResponseEntity.ok(new PurchaseBookResponse(invoice));
    }

    @PostMapping(path = "/books/{bookId}/sell", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Book> sellBook(@PathVariable("bookId") int bookId) {
        Book soldBook = bookstoreService.sellBook(bookId);
        return ResponseEntity.ok(soldBook);
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
