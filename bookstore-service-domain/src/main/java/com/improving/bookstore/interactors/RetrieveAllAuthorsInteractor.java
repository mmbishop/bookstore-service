package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.repositories.AuthorRepository;

import java.util.List;

public class RetrieveAllAuthorsInteractor implements EntityRetrievalInteractor<Author> {

    private AuthorRepository authorRepository;

    public RetrieveAllAuthorsInteractor(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> invoke() {
        return authorRepository.getAllAuthors();
    }

}
