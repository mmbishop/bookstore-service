package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.List;

public class RetrieveBooksByAuthorUseCase implements EntityRetrievalUseCase<Book> {

    private Author author;
    private BookRepository bookRepository;

    public RetrieveBooksByAuthorUseCase(Author author, BookRepository bookRepository) {
        this.author = author;
        this.bookRepository = bookRepository;
    }

    public List<Book> invoke() {
        return bookRepository.getBooksByAuthor(author);
    }

}
