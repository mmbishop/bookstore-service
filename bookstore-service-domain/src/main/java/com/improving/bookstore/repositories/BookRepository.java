package com.improving.bookstore.repositories;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    List<Book> getAllBooks();

    Optional<Book> getBookById(int bookId);

    List<Book> getBooksByTitle(String title);

    List<Book> getBooksByAuthor(Author author);

    List<Book> getBooksByGenre(String genreName);

    Book addBook(Book book);

    void deleteBook(Book book);

    Book saveBook(Book book);

}
