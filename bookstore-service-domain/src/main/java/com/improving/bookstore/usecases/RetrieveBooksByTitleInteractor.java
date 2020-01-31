package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.List;

public class RetrieveBooksByTitleInteractor implements EntityRetrievalInteractor<Book> {

    private BookRepository bookRepository;
    private String title;

    public RetrieveBooksByTitleInteractor(String title, BookRepository bookRepository) {
        this.title = title;
        this.bookRepository = bookRepository;
    }

    public List<Book> invoke() {
        return bookRepository.getBooksByTitle(title);
    }

}
