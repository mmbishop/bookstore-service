package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.List;

public class RetrieveBooksByGenreInteractor implements EntityRetrievalInteractor<Book> {

    private BookRepository bookRepository;
    private String genreName;

    public RetrieveBooksByGenreInteractor(String genreName, BookRepository bookRepository) {
        this.genreName = genreName;
        this.bookRepository = bookRepository;
    }

    public List<Book> invoke() {
        return bookRepository.getBooksByGenre(genreName);
    }

}
