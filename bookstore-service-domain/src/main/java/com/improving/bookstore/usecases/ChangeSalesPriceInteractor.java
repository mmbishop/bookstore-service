package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class ChangeSalesPriceInteractor {

    private BookRepository bookRepository;

    public ChangeSalesPriceInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void changeSalesPrice(int bookId, BigDecimal newPrice) {
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
