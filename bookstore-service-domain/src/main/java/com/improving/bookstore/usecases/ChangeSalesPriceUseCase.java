package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class ChangeSalesPriceUseCase {

    private BigDecimal newPrice;
    private BookRepository bookRepository;
    private int bookId;

    public ChangeSalesPriceUseCase(int bookId, BigDecimal newPrice, BookRepository bookRepository) {
        this.newPrice = newPrice;
        this.bookRepository = bookRepository;
        this.bookId = bookId;
    }

    public void invoke() {
        Optional<Book> book = bookRepository.getBookById(bookId);
        if (book.isPresent()) {
            book.get().setPrice(newPrice);
            bookRepository.saveBook(book.get());
        }
        else {
            throw new BookNotFoundException("No book found for ID " + bookId);
        }
    }

}
