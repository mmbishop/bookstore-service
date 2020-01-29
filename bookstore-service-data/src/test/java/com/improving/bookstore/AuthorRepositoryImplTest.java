package com.improving.bookstore;

import com.improving.bookstore.mappers.AuthorMapper;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.AuthorData;
import com.improving.bookstore.repositories.AuthorDataSource;
import com.improving.bookstore.repositories.AuthorRepositoryImpl;
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

    private AuthorData authorData;
    private AuthorDataSource authorDataSource;
    private AuthorMapper authorMapper;
    private AuthorRepositoryImpl authorRepository;
    private List<Author> authorList;
    private Optional<Author> searchResult;

    @Test
    void repository_adds_author() {
        given_an_author_data_source();
        given_an_author_mapper();
        given_an_author_repository();
        when_an_author_is_added_to_the_repository();
        then_the_repository_adds_the_author_via_the_data_source();
    }

    @Test
    void repository_returns_author_by_example_when_author_is_found() {
        given_an_author_data_source();
        given_an_author_mapper();
        given_an_author_repository();
        when_the_repository_is_searched_for_an_author_that_exists();
        then_the_author_is_returned();
    }

    @Test
    void repository_returns_empty_object_when_author_is_not_found() {
        given_an_author_data_source();
        given_an_author_repository();
        when_the_repository_is_searched_for_an_author_that_does_not_exist();
        then_nothing_is_returned();
    }

    @Test
    void repository_returns_all_authors_in_the_repository() {
        given_an_author_data_source();
        given_an_author_mapper();
        given_an_author_repository();
        when_all_authors_are_requested_from_the_repository();
        then_all_authors_are_returned();
    }

    private void given_an_author_data_source() {
        authorDataSource = Mockito.mock(AuthorDataSource.class);
    }

    private void given_an_author_mapper() {
        authorMapper = Mockito.mock(AuthorMapper.class);
    }

    private void given_an_author_repository() {
        authorRepository = new AuthorRepositoryImpl(authorDataSource, authorMapper);
    }

    private void when_an_author_is_added_to_the_repository() {
        Author author = getAuthor();
        authorData = getAuthorData();
        when(authorMapper.mapFrom(author)).thenReturn(authorData);
        authorRepository.addAuthor(getAuthor());
    }

    private void when_the_repository_is_searched_for_an_author_that_exists() {
        Author author = getAuthor();
        authorData = getAuthorData();
        when(authorDataSource.findByFirstNameAndMiddleNameAndLastName("Edgar", "Allan", "Poe"))
                .thenReturn(Collections.singletonList(authorData));
        when(authorMapper.mapFrom(authorData)).thenReturn(author);
        searchResult = authorRepository.getAuthorByExample(author);
    }

    private void when_the_repository_is_searched_for_an_author_that_does_not_exist() {
        Author author = getAuthor();
        when(authorDataSource.findByFirstNameAndMiddleNameAndLastName("Edgar", "Allan", "Poe"))
                .thenReturn(Collections.emptyList());
        searchResult = authorRepository.getAuthorByExample(author);
    }

    private void when_all_authors_are_requested_from_the_repository() {
        Iterable<AuthorData> authorDataIterable = getAuthorDataList();
        when(authorDataSource.findAll()).thenReturn(authorDataIterable);
        authorDataIterable.forEach(authorData -> {
            when(authorMapper.mapFrom(authorData)).thenReturn(new Author(authorData.getFirstName(), authorData.getMiddleName(), authorData.getLastName()));
        });
        authorList = authorRepository.getAllAuthors();
    }

    private void then_the_repository_adds_the_author_via_the_data_source() {
        verify(authorDataSource).save(authorData);
    }

    private void then_the_author_is_returned() {
        assert searchResult.isPresent();
        Author author = searchResult.get();
        assertThat(author.getFirstName(), is("Edgar"));
        assertThat(author.getMiddleName(), is("Allan"));
        assertThat(author.getLastName(), is("Poe"));
    }

    private void then_nothing_is_returned() {
        assert searchResult.isEmpty();
    }

    private void then_all_authors_are_returned() {
        assertThat(authorList.size(), is(2));
        Author author = authorList.get(0);
        assertThat(author.getLastName(), is("Poe"));
        author = authorList.get(1);
        assertThat(author.getLastName(), is("Robinson"));
    }

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

}
