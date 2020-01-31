package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.List;

public class RetrieveBooksByTitleInteractor {

    private BookRepository bookRepository;

    public RetrieveBooksByTitleInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> retrieveBooksByTitle(String title) {
        return bookRepository.getBooksByTitle(title);
    }

}
