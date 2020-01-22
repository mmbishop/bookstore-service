package com.improving.bookstore.repositories;

import com.improving.bookstore.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    void addAuthor(Author author);

    Optional<Author> getAuthorByExample(Author author);

    List<Author> getAllAuthors();

}
