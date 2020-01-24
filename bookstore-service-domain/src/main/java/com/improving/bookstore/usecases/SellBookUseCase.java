package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class SellBookUseCase {

    private BookRepository bookRepository;

    @Autowired
    public SellBookUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void sellBook(int bookId) {
        Optional<Book> book = bookRepository.getBookById(bookId);
        if (book.isPresent()) {
            bookRepository.deleteBook(book.get());
        }
        else {
            throw new BookNotFoundException("No book found for ID " + bookId);
        }
    }

}
