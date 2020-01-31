package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.repositories.AuthorRepository;

import java.util.List;

public class RetrieveAllAuthorsInteractor {

    private AuthorRepository authorRepository;

    public RetrieveAllAuthorsInteractor(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> retrieveAllAuthors() {
        return authorRepository.getAllAuthors();
    }

}
