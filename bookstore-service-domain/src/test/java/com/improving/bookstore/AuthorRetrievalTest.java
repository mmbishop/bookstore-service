package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.repositories.AuthorRepository;
import com.improving.bookstore.interactors.RetrieveAllAuthorsInteractor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

public class AuthorRetrievalTest {

    private AuthorRepository authorRepository;
    private List<Author> allAuthors = getAllAuthors();
    private List<Author> authorList;

    @Test
    void all_authors_are_retrieved() {
        given_an_author_repository();
        when_all_authors_are_requested();
        then_all_authors_are_retrieved();
    }

    private void given_an_author_repository() {
        authorRepository = Mockito.mock(AuthorRepository.class);
    }

    private void when_all_authors_are_requested() {
        when(authorRepository.getAllAuthors()).thenReturn(allAuthors);
        authorList = new RetrieveAllAuthorsInteractor(authorRepository).invoke();
    }

    private void then_all_authors_are_retrieved() {
        assertThat(authorList.size(), is(allAuthors.size()));
    }

    private List<Author> getAllAuthors() {
        return List.of(
                new Author("Arthur", "C", "Clarke"),
                new Author("Stephen", "R", "Donaldson"),
                new Author("Peter", "F", "Hamilton")
        );
    }

}
