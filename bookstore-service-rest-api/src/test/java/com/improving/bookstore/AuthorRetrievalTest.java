package com.improving.bookstore;

import com.improving.bookstore.interactors.RetrieveAllAuthorsInteractor;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.services.BookstoreService;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

public class AuthorRetrievalTest {

    private final GwtTest<AuthorTestContext> gwt = new GwtTest<>(AuthorTestContext.class);

    @Test
    void all_authors_are_retrieved() {
        gwt.test()
                .given(a_bookstore_service)
                .and(an_interactor_for_retrieving_all_authors)
                .when(requesting_all_authors)
                .then(all_authors_are_returned);
    }

    private final GwtFunction<AuthorTestContext> a_bookstore_service = context -> context.bookstoreService = new BookstoreService();

    private final GwtFunction<AuthorTestContext> an_interactor_for_retrieving_all_authors = context -> {
        context.retrieveAllAuthorsInteractor = Mockito.mock(RetrieveAllAuthorsInteractor.class);
        context.bookstoreService.setRetrieveAllAuthorsInteractor(context.retrieveAllAuthorsInteractor);
    };

    private final GwtFunction<AuthorTestContext> requesting_all_authors = context -> {
        when(context.retrieveAllAuthorsInteractor.retrieveAllAuthors()).thenReturn(getAuthorList());
        context.authorList = context.bookstoreService.getAllAuthors();
    };

    private final GwtFunction<AuthorTestContext> all_authors_are_returned = context -> {
        assertThat(context.authorList.size(), is(2));
        assertThat(context.authorList.get(0).getLastName(), is("Robinson"));
        assertThat(context.authorList.get(1).getLastName(), is("Poe"));
    };

    private List<Author> getAuthorList() {
        return List.of(
                new Author("Kim", "Stanley", "Robinson"),
                new Author("Edgar", "Allan", "Poe")
        );
    }

    public static final class AuthorTestContext extends Context {
        BookstoreService bookstoreService;
        List<Author> authorList;
        RetrieveAllAuthorsInteractor retrieveAllAuthorsInteractor;
    }

}
