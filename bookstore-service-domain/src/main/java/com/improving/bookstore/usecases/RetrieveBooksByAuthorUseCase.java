package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.List;

public class RetrieveBooksByAuthorUseCase {

    private BookRepository bookRepository;

    public RetrieveBooksByAuthorUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> invoke(Author author) {
        return bookRepository.getBooksByAuthor(author);
    }

}
