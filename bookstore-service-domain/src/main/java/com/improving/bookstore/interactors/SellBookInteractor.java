package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.Optional;

public class SellBookInteractor {

    private BookRepository bookRepository;

    public SellBookInteractor(BookRepository bookRepository) {
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
