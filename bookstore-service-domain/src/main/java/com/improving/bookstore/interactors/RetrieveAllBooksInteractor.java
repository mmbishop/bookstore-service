package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;

import java.util.List;

public class RetrieveAllBooksInteractor {

    private BookRepository bookRepository;

    public RetrieveAllBooksInteractor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> retrieveAllBooks() {
        return bookRepository.getAllBooks();
    }

}
