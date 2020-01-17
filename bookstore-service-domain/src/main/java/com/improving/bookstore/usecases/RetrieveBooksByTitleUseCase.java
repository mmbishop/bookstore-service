package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.List;

public class RetrieveBooksByTitleUseCase {

    private BookRepository bookRepository;

    public RetrieveBooksByTitleUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> invoke(String title) {
        return bookRepository.getBooksByTitle(title);
    }

}
