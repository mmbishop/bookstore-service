package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class BookSaleInteractor {

    private BookRepository bookRepository;

    public BookSaleInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void putBookOnSale(int bookId, BigDecimal newPrice) {
        Optional<Book> book = bookRepository.getBookById(bookId);
        if (book.isPresent()) {
            book.get().setPrice(newPrice);
            bookRepository.saveBook(book.get());
        }
        else {
            throw new BookNotFoundException("No book found for ID " + bookId);
        }
    }

    public void takeBookOffSale(int bookId) {
        Optional<Book> book = bookRepository.getBookById(bookId);
        if (book.isPresent()) {
            book.get().resetPrice();
            bookRepository.saveBook(book.get());
        }
        else {
            throw new BookNotFoundException("No book found for ID " + bookId);
        }
    }

}
