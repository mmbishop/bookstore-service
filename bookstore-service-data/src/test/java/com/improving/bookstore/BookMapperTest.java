package com.improving.bookstore;

import com.improving.bookstore.mappers.AuthorMapper;
import com.improving.bookstore.mappers.BookMapper;
import com.improving.bookstore.mappers.GenreMapper;
import com.improving.bookstore.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class BookMapperTest {

    private Author authorDomainEntity;
    private AuthorData authorDataEntity;
    private AuthorMapper authorMapper;
    private Book bookDomainEntity;
    private BookData bookDataEntity;
    private BookMapper bookMapper;
    private Genre genreDomainEntity;
    private GenreData genreDataEntity;
    private GenreMapper genreMapper;

    @Test
    void mapper_maps_data_entity_to_domain_entity() {
        given_a_set_of_mappers();
        given_an_author_data_entity();
        given_an_author_domain_entity();
        given_a_genre_data_entity();
        given_a_genre_domain_entity();
        given_a_book_data_entity();
        when_the_book_data_entity_is_mapped_to_a_domain_entity();
        then_the_book_domain_entity_is_produced();
    }

    @Test
    void mapper_maps_domain_entity_to_data_entity() {
        given_a_set_of_mappers();
        given_an_author_data_entity();
        given_an_author_domain_entity();
        given_a_genre_data_entity();
        given_a_genre_domain_entity();
        given_a_book_domain_entity();
        when_the_book_domain_entity_is_mapped_to_a_data_entity();
        then_the_book_data_entity_is_produced();
    }

    @Test
    void mapper_returns_null_when_data_entity_is_null() {
        given_a_set_of_mappers();
        when_a_null_book_data_entity_is_mapped_to_a_domain_entity();
        then_the_book_domain_entity_is_null();
    }

    @Test
    void mapper_returns_null_when_domain_entity_is_null() {
        given_a_set_of_mappers();
        when_the_book_domain_entity_is_mapped_to_a_data_entity();
        then_the_book_data_entity_is_null();
    }

    private void given_a_set_of_mappers() {
        authorMapper = Mockito.mock(AuthorMapper.class);
        genreMapper = Mockito.mock(GenreMapper.class);
        bookMapper = new BookMapper(authorMapper, genreMapper);
    }

    private void given_a_book_data_entity() {
        bookDataEntity = new BookData();
        bookDataEntity.setId(1);
        bookDataEntity.setTitle("The Tales of Edgar Allan Poe");
        bookDataEntity.setPublisher("The Publisher");
        bookDataEntity.setPublishYear(1840);
        bookDataEntity.setIsbn("An ISBN");
        bookDataEntity.setNumberOfPages(360);
        bookDataEntity.setPrice(BigDecimal.valueOf(12.0));
        bookDataEntity.setAuthor(authorDataEntity);
        bookDataEntity.setGenre(genreDataEntity);
    }

    private void given_a_book_domain_entity() {
        bookDomainEntity = new Book(1,"The Tales of Edgar Allan Poe", authorDomainEntity, "The Publisher", 1840, "An ISBN", 360,
                genreDomainEntity);
    }

    private void given_an_author_data_entity() {
        authorDataEntity = new AuthorData();
        authorDataEntity.setId(1);
        authorDataEntity.setFirstName("Edgar");
        authorDataEntity.setMiddleName("Allan");
        authorDataEntity.setLastName("Poe");
    }

    private void given_an_author_domain_entity() {
        authorDomainEntity = new Author("Edgar", "Allan", "Poe");
    }

    private void given_a_genre_data_entity() {
        genreDataEntity = new GenreData();
        genreDataEntity.setId(1);
        genreDataEntity.setName("Fiction");
        genreDataEntity.setPricingFactor(1.0);
    }

    private void given_a_genre_domain_entity() {
        genreDomainEntity = new Genre("Fiction", 1.0);
    }

    private void when_the_book_data_entity_is_mapped_to_a_domain_entity() {
        when(authorMapper.mapFrom(authorDataEntity)).thenReturn(authorDomainEntity);
        when(genreMapper.mapFrom(genreDataEntity)).thenReturn(genreDomainEntity);
        bookDomainEntity = bookMapper.mapFrom(bookDataEntity);
    }

    private void when_the_book_domain_entity_is_mapped_to_a_data_entity() {
        when(authorMapper.mapFrom(authorDomainEntity)).thenReturn(authorDataEntity);
        when(genreMapper.mapFrom(genreDomainEntity)).thenReturn(genreDataEntity);
        bookDataEntity = bookMapper.mapFrom(bookDomainEntity);
    }

    private void when_a_null_book_domain_entity_is_mapped_to_a_data_entity() {
        bookDataEntity = bookMapper.mapFrom((Book) null);
    }

    private void when_a_null_book_data_entity_is_mapped_to_a_domain_entity() {
        bookDomainEntity = bookMapper.mapFrom((BookData) null);
    }

    private void then_the_book_domain_entity_is_produced() {
        assertThat(bookDomainEntity.getId(), is(1));
        assertThat(bookDomainEntity.getTitle(), is("The Tales of Edgar Allan Poe"));
        assertThat(bookDomainEntity.getPublisher(), is("The Publisher"));
        assertThat(bookDomainEntity.getPublishYear(), is(1840));
        assertThat(bookDomainEntity.getIsbn(), is("An ISBN"));
        assertThat(bookDomainEntity.getNumberOfPages(), is(360));
        assertThat(bookDomainEntity.getPrice(), is(BigDecimal.valueOf(12.0)));
        Author author = bookDomainEntity.getAuthor();
        assertThat(author.getFirstName(), is("Edgar"));
        assertThat(author.getMiddleName(), is("Allan"));
        assertThat(author.getLastName(), is("Poe"));
        Genre genre = bookDomainEntity.getGenre();
        assertThat(genre.getName(), is("Fiction"));
        assertThat(genre.getPricingFactor(), is(1.0));
    }

    private void then_the_book_data_entity_is_produced() {
        assertThat(bookDataEntity.getId(), is(1));
        assertThat(bookDataEntity.getTitle(), is("The Tales of Edgar Allan Poe"));
        assertThat(bookDataEntity.getPublisher(), is("The Publisher"));
        assertThat(bookDataEntity.getPublishYear(), is(1840));
        assertThat(bookDataEntity.getIsbn(), is("An ISBN"));
        assertThat(bookDataEntity.getNumberOfPages(), is(360));
        assertThat(bookDataEntity.getPrice(), is(BigDecimal.valueOf(12.0)));
        AuthorData authorData = bookDataEntity.getAuthor();
        assertThat(authorData.getFirstName(), is("Edgar"));
        assertThat(authorData.getMiddleName(), is("Allan"));
        assertThat(authorData.getLastName(), is("Poe"));
        GenreData genreData = bookDataEntity.getGenre();
        assertThat(genreData.getName(), is("Fiction"));
        assertThat(genreData.getPricingFactor(), is(1.0));
    }

    private void then_the_book_domain_entity_is_null() {
        assertThat(bookDomainEntity, is(nullValue()));
    }

    private void then_the_book_data_entity_is_null() {
        assertThat(bookDataEntity, is(nullValue()));
    }

}
