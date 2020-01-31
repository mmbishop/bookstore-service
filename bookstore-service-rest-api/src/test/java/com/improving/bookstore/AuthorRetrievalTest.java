package com.improving.bookstore;

import com.improving.bookstore.interactors.RetrieveAllAuthorsInteractor;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.services.BookstoreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

public class AuthorRetrievalTest {

    private BookstoreService bookstoreService;
    private List<Author> authorList;
    private RetrieveAllAuthorsInteractor retrieveAllAuthorsInteractor;

    @Test
    void all_authors_are_retrieved() {
        given_a_bookstore_service();
        given_an_interactor_for_retrieving_all_authors();
        when_all_authors_are_requested();
        then_all_authors_are_returned();
    }

    private void given_a_bookstore_service() {
        bookstoreService = new BookstoreService();
    }

    private void given_an_interactor_for_retrieving_all_authors() {
        retrieveAllAuthorsInteractor = Mockito.mock(RetrieveAllAuthorsInteractor.class);
        bookstoreService.setRetrieveAllAuthorsInteractor(retrieveAllAuthorsInteractor);
    }

    private void when_all_authors_are_requested() {
        when(retrieveAllAuthorsInteractor.retrieveAllAuthors()).thenReturn(getAuthorList());
        authorList = bookstoreService.getAllAuthors();
    }

    private void then_all_authors_are_returned() {
        assertThat(authorList.size(), is(2));
        assertThat(authorList.get(0).getLastName(), is("Robinson"));
        assertThat(authorList.get(1).getLastName(), is("Poe"));
    }

    private List<Author> getAuthorList() {
        return List.of(
                new Author("Kim", "Stanley", "Robinson"),
                new Author("Edgar", "Allan", "Poe")
        );
    }

}
