package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.Optional;

public class SellBookUseCase {

    private BookRepository bookRepository;
    private int bookId;

    public SellBookUseCase(int bookId, BookRepository bookRepository) {
        this.bookId = bookId;
        this.bookRepository = bookRepository;
    }

    public void invoke() {
        Optional<Book> book = bookRepository.getBookById(bookId);
        if (book.isPresent()) {
            bookRepository.deleteBook(book.get());
        }
        else {
            throw new BookNotFoundException("No book found for ID " + bookId);
        }
    }

}
