package com.improving.bookstore.repositories;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;

import java.math.BigDecimal;
import java.util.List;

public interface BookRepository {

    List<Book> getAllBooks();

    List<Book> getBooksByTitle(String title);

    List<Book> getBooksByAuthor(Author author);

    List<Book> getBooksByGenre(String genreName);

    void setSalesPrice(Book book, BigDecimal newPrice);

    void addBook(Book book);

    void deleteBook(Book book);

}
