package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.List;

public class RetrieveBooksByGenreUseCase {

    private BookRepository bookRepository;

    public RetrieveBooksByGenreUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> invoke(String genreName) {
        return bookRepository.getBooksByGenre(genreName);
    }

}
