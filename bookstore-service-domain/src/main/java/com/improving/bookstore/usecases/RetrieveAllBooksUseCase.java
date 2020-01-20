package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RetrieveAllBooksUseCase implements EntityRetrievalUseCase<Book> {

    private BookRepository bookRepository;

    public RetrieveAllBooksUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> invoke() {
        return bookRepository.getAllBooks();
    }

}
