package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.List;

public class RetrieveBooksByGenreUseCase implements EntityRetrievalUseCase<Book> {

    private BookRepository bookRepository;
    private String genreName;

    public RetrieveBooksByGenreUseCase(String genreName, BookRepository bookRepository) {
        this.genreName = genreName;
        this.bookRepository = bookRepository;
    }

    public List<Book> invoke() {
        return bookRepository.getBooksByGenre(genreName);
    }

}
