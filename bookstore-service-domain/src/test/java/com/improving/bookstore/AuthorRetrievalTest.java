package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.repositories.AuthorRepository;
import com.improving.bookstore.interactors.RetrieveAllAuthorsInteractor;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

public class AuthorRetrievalTest {

    private final GwtTest<AuthorTestContext> gwt = new GwtTest<>(AuthorTestContext.class);

    @Test
    void all_authors_are_retrieved() {
        gwt.test()
                .given(an_author_repository)
                .and(authors)
                .when(requesting_all_authors)
                .then(all_authors_are_retrieved);
    }

    private final GwtFunction<AuthorTestContext> an_author_repository = context -> context.authorRepository = Mockito.mock(AuthorRepository.class);

    private final GwtFunction<AuthorTestContext> authors = context -> context.allAuthors = getAllAuthors();

    private final GwtFunction<AuthorTestContext> requesting_all_authors = context -> {
        when(context.authorRepository.getAllAuthors()).thenReturn(context.allAuthors);
        context.authorList = new RetrieveAllAuthorsInteractor(context.authorRepository).retrieveAllAuthors();
    };

    private final GwtFunction<AuthorTestContext> all_authors_are_retrieved
            = context -> assertThat(context.authorList.size(), is(context.allAuthors.size()));

    private List<Author> getAllAuthors() {
        return List.of(
                new Author("Arthur", "C", "Clarke"),
                new Author("Stephen", "R", "Donaldson"),
                new Author("Peter", "F", "Hamilton")
        );
    }

    public static final class AuthorTestContext extends Context {
        AuthorRepository authorRepository;
        List<Author> allAuthors;
        List<Author> authorList;
    }

}
