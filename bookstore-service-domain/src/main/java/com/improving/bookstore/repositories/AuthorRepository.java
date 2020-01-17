package com.improving.bookstore.repositories;

import com.improving.bookstore.model.Author;

import java.util.List;

public interface AuthorRepository {

    void addAuthor(Author author);

    void deleteAuthor(Author author);

    List<Author> getAllAuthors();

}
