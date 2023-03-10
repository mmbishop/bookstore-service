package com.improving.bookstore;

import com.improving.bookstore.mappers.AuthorMapper;
import com.improving.bookstore.mappers.BookMapper;
import com.improving.bookstore.mappers.GenreMapper;
import com.improving.bookstore.model.*;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

public class BookMapperTest {

    private final GwtTest<BookMapperTestContext> gwt = new GwtTest<>(BookMapperTestContext.class);

    @Test
    void mapper_maps_data_entity_to_domain_entity() {
        gwt.test()
                .given(
                        a_set_of_mappers,
                        an_author_data_entity,
                        an_author_domain_entity,
                        a_genre_data_entity,
                        a_genre_domain_entity,
                        a_book_data_entity
                )
                .when(mapping_the_book_data_entity_to_a_domain_entity)
                .then(the_book_domain_entity_is_produced);
    }

    @Test
    void mapper_maps_domain_entity_to_data_entity() {
        gwt.test()
                .given(
                        a_set_of_mappers,
                        an_author_domain_entity,
                        an_author_data_entity,
                        a_genre_data_entity,
                        a_genre_domain_entity,
                        a_book_domain_entity
                )
                .when(mapping_the_book_domain_entity_to_a_data_entity)
                .then(the_book_data_entity_is_produced);
    }

    @Test
    void mapper_returns_null_when_data_entity_is_null() {
        gwt.test()
                .given(a_set_of_mappers)
                .when(mapping_a_null_book_data_entity_to_a_domain_entity)
                .then(the_book_domain_entity_is_null);
    }

    @Test
    void mapper_returns_null_when_domain_entity_is_null() {
        gwt.test()
                .given(a_set_of_mappers)
                .when(mapping_a_null_book_domain_entity_to_a_data_entity)
                .then(the_book_data_entity_is_null);
    }

    private final GwtFunction<BookMapperTestContext> a_set_of_mappers = context -> {
        context.authorMapper = Mockito.mock(AuthorMapper.class);
        context.genreMapper = Mockito.mock(GenreMapper.class);
        context.bookMapper = new BookMapper(context.authorMapper, context.genreMapper);
    };

    private final GwtFunction<BookMapperTestContext> a_book_data_entity = context -> {
        context.bookDataEntity = new BookData();
        context.bookDataEntity.setId(1);
        context.bookDataEntity.setTitle("The Tales of Edgar Allan Poe");
        context.bookDataEntity.setPublisher("The Publisher");
        context.bookDataEntity.setPublishYear(1840);
        context.bookDataEntity.setIsbn("An ISBN");
        context.bookDataEntity.setNumberOfPages(360);
        context.bookDataEntity.setPrice(BigDecimal.valueOf(7.2));
        context.bookDataEntity.setAuthor(context.authorDataEntity);
        context.bookDataEntity.setGenre(context.genreDataEntity);
    };

    private final GwtFunction<BookMapperTestContext> a_book_domain_entity = context -> {
        context.bookDomainEntity = new Book(1,"The Tales of Edgar Allan Poe", context.authorDomainEntity, "The Publisher",
                1840, "An ISBN", 360, context.genreDomainEntity);
    };

    private final GwtFunction<BookMapperTestContext> an_author_data_entity = context -> {
        context.authorDataEntity = new AuthorData();
        context.authorDataEntity.setId(1);
        context.authorDataEntity.setFirstName("Edgar");
        context.authorDataEntity.setMiddleName("Allan");
        context.authorDataEntity.setLastName("Poe");
    };

    private final GwtFunction<BookMapperTestContext> an_author_domain_entity = context -> {
        context.authorDomainEntity = new Author("Edgar", "Allan", "Poe");
    };

    private final GwtFunction<BookMapperTestContext> a_genre_data_entity = context -> {
        context.genreDataEntity = new GenreData();
        context.genreDataEntity.setId(1);
        context.genreDataEntity.setName("Fiction");
        context.genreDataEntity.setPricingFactor(1.0);
    };

    private final GwtFunction<BookMapperTestContext> a_genre_domain_entity = context -> {
        context.genreDomainEntity = new Genre("Fiction", 1.0);
    };

    private final GwtFunction<BookMapperTestContext> mapping_the_book_data_entity_to_a_domain_entity = context -> {
        when(context.authorMapper.mapFrom(context.authorDataEntity)).thenReturn(context.authorDomainEntity);
        when(context.genreMapper.mapFrom(context.genreDataEntity)).thenReturn(context.genreDomainEntity);
        context.bookDomainEntity = context.bookMapper.mapFrom(context.bookDataEntity);
    };

    private final GwtFunction<BookMapperTestContext> mapping_the_book_domain_entity_to_a_data_entity = context -> {
        when(context.authorMapper.mapFrom(context.authorDomainEntity)).thenReturn(context.authorDataEntity);
        when(context.genreMapper.mapFrom(context.genreDomainEntity)).thenReturn(context.genreDataEntity);
        context.bookDataEntity = context.bookMapper.mapFrom(context.bookDomainEntity);
    };

    private final GwtFunction<BookMapperTestContext> mapping_a_null_book_domain_entity_to_a_data_entity
            = context -> context.bookDataEntity = context.bookMapper.mapFrom((Book) null);

    private final GwtFunction<BookMapperTestContext> mapping_a_null_book_data_entity_to_a_domain_entity
            = context -> context.bookDomainEntity = context.bookMapper.mapFrom((BookData) null);

    private final GwtFunction<BookMapperTestContext> the_book_domain_entity_is_produced = context -> {
        assertThat(context.bookDomainEntity.getId(), is(1));
        assertThat(context.bookDomainEntity.getTitle(), is("The Tales of Edgar Allan Poe"));
        assertThat(context.bookDomainEntity.getPublisher(), is("The Publisher"));
        assertThat(context.bookDomainEntity.getPublishYear(), is(1840));
        assertThat(context.bookDomainEntity.getIsbn(), is("An ISBN"));
        assertThat(context.bookDomainEntity.getNumberOfPages(), is(360));
        assertThat(context.bookDomainEntity.getPrice(), is(new BigDecimal(7.2, new MathContext(3))));
        Author author = context.bookDomainEntity.getAuthor();
        assertThat(author.getFirstName(), is("Edgar"));
        assertThat(author.getMiddleName(), is("Allan"));
        assertThat(author.getLastName(), is("Poe"));
        Genre genre = context.bookDomainEntity.getGenre();
        assertThat(genre.getName(), is("Fiction"));
        assertThat(genre.getPricingFactor(), is(1.0));
    };

    private final GwtFunction<BookMapperTestContext> the_book_data_entity_is_produced = context -> {
        assertThat(context.bookDataEntity.getId(), is(1));
        assertThat(context.bookDataEntity.getTitle(), is("The Tales of Edgar Allan Poe"));
        assertThat(context.bookDataEntity.getPublisher(), is("The Publisher"));
        assertThat(context.bookDataEntity.getPublishYear(), is(1840));
        assertThat(context.bookDataEntity.getIsbn(), is("An ISBN"));
        assertThat(context.bookDataEntity.getNumberOfPages(), is(360));
        assertThat(context.bookDataEntity.getPrice(), is(new BigDecimal(7.2, new MathContext(3))));
        AuthorData authorData = context.bookDataEntity.getAuthor();
        assertThat(authorData.getFirstName(), is("Edgar"));
        assertThat(authorData.getMiddleName(), is("Allan"));
        assertThat(authorData.getLastName(), is("Poe"));
        GenreData genreData = context.bookDataEntity.getGenre();
        assertThat(genreData.getName(), is("Fiction"));
        assertThat(genreData.getPricingFactor(), is(1.0));
    };

    private final GwtFunction<BookMapperTestContext> the_book_domain_entity_is_null = context -> assertThat(context.bookDomainEntity, is(nullValue()));

    private final GwtFunction<BookMapperTestContext> the_book_data_entity_is_null = context -> assertThat(context.bookDataEntity, is(nullValue()));

    public static class BookMapperTestContext extends Context {
        Author authorDomainEntity;
        AuthorData authorDataEntity;
        AuthorMapper authorMapper;
        Book bookDomainEntity;
        BookData bookDataEntity;
        BookMapper bookMapper;
        Genre genreDomainEntity;
        GenreData genreDataEntity;
        GenreMapper genreMapper;
    }

}
