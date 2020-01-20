package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.repositories.AuthorRepository;

import java.util.List;

public class RetrieveAllAuthorsUseCase implements EntityRetrievalUseCase<Author> {

    private AuthorRepository authorRepository;

    public RetrieveAllAuthorsUseCase(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> invoke() {
        return authorRepository.getAllAuthors();
    }

}
