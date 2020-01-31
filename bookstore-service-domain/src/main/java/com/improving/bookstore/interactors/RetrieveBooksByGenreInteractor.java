package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.List;

public class RetrieveBooksByGenreInteractor {

    private BookRepository bookRepository;

    public RetrieveBooksByGenreInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> retrieveBooksByGenre(String genreName) {
        return bookRepository.getBooksByGenre(genreName);
    }

}
