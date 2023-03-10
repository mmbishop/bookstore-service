package com.improving.bookstore;

import com.improving.bookstore.mappers.AuthorMapper;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.AuthorData;
import com.improving.bookstore.repositories.AuthorDataSource;
import com.improving.bookstore.repositories.AuthorRepositoryImpl;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorRepositoryImplTest {

    private final GwtTest<AuthorRepositoryTestContext> gwt = new GwtTest<>(AuthorRepositoryTestContext.class);

    @Test
    void repository_adds_author() {
        gwt.test()
                .given(an_author_data_source)
                .and(an_author_mapper)
                .and(an_author_repository)
                .when(adding_an_author_to_the_repository)
                .then(the_repository_adds_the_author_via_the_data_source);
    }

    @Test
    void repository_returns_author_by_example_when_author_is_found() {
        gwt.test()
                .given(an_author_data_source)
                .and(an_author_mapper)
                .and(an_author_repository)
                .when(searching_the_repository_for_an_author_that_exists)
                .then(the_author_is_returned);
    }

    @Test
    void repository_returns_empty_object_when_author_is_not_found() {
        gwt.test()
                .given(an_author_data_source)
                .and(an_author_mapper)
                .and(an_author_repository)
                .when(searching_the_repository_for_an_author_that_does_not_exist)
                .then(nothing_is_returned);
    }

    @Test
    void repository_returns_all_authors_in_the_repository() {
        gwt.test()
                .given(an_author_data_source)
                .and(an_author_mapper)
                .and(an_author_repository)
                .when(requesting_all_authors_from_the_repository)
                .then(all_authors_are_returned);
    }

    private final GwtFunction<AuthorRepositoryTestContext> an_author_data_source
            = context -> context.authorDataSource = Mockito.mock(AuthorDataSource.class);

    private final GwtFunction<AuthorRepositoryTestContext> an_author_mapper
            = context -> context.authorMapper = Mockito.mock(AuthorMapper.class);

    private final GwtFunction<AuthorRepositoryTestContext> an_author_repository
            = context -> context.authorRepository = new AuthorRepositoryImpl(context.authorDataSource, context.authorMapper);

    private final GwtFunction<AuthorRepositoryTestContext> adding_an_author_to_the_repository = context -> {
        Author author = getAuthor();
        context.authorData = getAuthorData();
        when(context.authorMapper.mapFrom(author)).thenReturn(context.authorData);
        context.authorRepository.addAuthor(getAuthor());
    };

    private final GwtFunction<AuthorRepositoryTestContext> searching_the_repository_for_an_author_that_exists = context -> {
        Author author = getAuthor();
        context.authorData = getAuthorData();
        when(context.authorDataSource.findByFirstNameAndMiddleNameAndLastName("Edgar", "Allan", "Poe"))
                .thenReturn(Collections.singletonList(context.authorData));
        when(context.authorMapper.mapFrom(context.authorData)).thenReturn(author);
        context.searchResult = context.authorRepository.getAuthorByExample(author);
    };

    private final GwtFunction<AuthorRepositoryTestContext> searching_the_repository_for_an_author_that_does_not_exist = context -> {
        Author author = getAuthor();
        when(context.authorDataSource.findByFirstNameAndMiddleNameAndLastName("Edgar", "Allan", "Poe"))
                .thenReturn(Collections.emptyList());
        context.searchResult = context.authorRepository.getAuthorByExample(author);
    };

    private final GwtFunction<AuthorRepositoryTestContext> requesting_all_authors_from_the_repository  = context -> {
        Iterable<AuthorData> authorDataIterable = getAuthorDataList();
        when(context.authorDataSource.findAll()).thenReturn(authorDataIterable);
        authorDataIterable.forEach(authorData -> {
            when(context.authorMapper.mapFrom(authorData)).thenReturn(new Author(authorData.getFirstName(), authorData.getMiddleName(), authorData.getLastName()));
        });
        context.authorList = context.authorRepository.getAllAuthors();
    };

    private final GwtFunction<AuthorRepositoryTestContext> the_repository_adds_the_author_via_the_data_source
            = context -> verify(context.authorDataSource).save(context.authorData);

    private final GwtFunction<AuthorRepositoryTestContext> the_author_is_returned = context -> {
        assert context.searchResult.isPresent();
        Author author = context.searchResult.get();
        assertThat(author.getFirstName(), is("Edgar"));
        assertThat(author.getMiddleName(), is("Allan"));
        assertThat(author.getLastName(), is("Poe"));
    };

    private final GwtFunction<AuthorRepositoryTestContext> nothing_is_returned = context -> {
        assert context.searchResult.isEmpty();
    };

    private final GwtFunction<AuthorRepositoryTestContext> all_authors_are_returned = context -> {
        assertThat(context.authorList.size(), is(2));
        Author author = context.authorList.get(0);
        assertThat(author.getLastName(), is("Poe"));
        author = context.authorList.get(1);
        assertThat(author.getLastName(), is("Robinson"));
    };

    private Author getAuthor() {
        return new Author("Edgar", "Allan", "Poe");
    }

    private AuthorData getAuthorData() {
        return createAuthorData(1, "Edgar", "Allan", "Poe");
    }

    private AuthorData createAuthorData(int id, String firstName, String middleName, String lastName) {
        AuthorData authorData = new AuthorData();
        authorData.setId(1);
        authorData.setFirstName(firstName);
        authorData.setMiddleName(middleName);
        authorData.setLastName(lastName);
        return authorData;
    }

    private Iterable<AuthorData> getAuthorDataList() {
        return List.of(
                createAuthorData(1, "Edgar", "Allan", "Poe"),
                createAuthorData(2, "Kim", "Stanley", "Robinson")
        );
    }

    public static final class AuthorRepositoryTestContext extends Context {
        AuthorData authorData;
        AuthorDataSource authorDataSource;
        AuthorMapper authorMapper;
        AuthorRepositoryImpl authorRepository;
        List<Author> authorList;
        Optional<Author> searchResult;
    }

}
