package com.improving.bookstore.repositories;

import com.improving.bookstore.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author addAuthor(Author author);

    Optional<Author> getAuthorByExample(Author author);

    List<Author> getAllAuthors();

}
