package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.List;

public class RetrieveBooksByAuthorInteractor {

    private BookRepository bookRepository;

    public RetrieveBooksByAuthorInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> retrieveBooksByAuthor(Author author) {
        return bookRepository.getBooksByAuthor(author);
    }

}
