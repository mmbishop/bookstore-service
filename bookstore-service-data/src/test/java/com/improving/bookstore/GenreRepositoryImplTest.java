package com.improving.bookstore;

import com.improving.bookstore.mappers.GenreMapper;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.GenreData;
import com.improving.bookstore.repositories.GenreDataSource;
import com.improving.bookstore.repositories.GenreRepositoryImpl;
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

public class GenreRepositoryImplTest {

    private final GwtTest<GenreRepositoryTestContext> gwt = new GwtTest<>(GenreRepositoryTestContext.class);

    @Test
    void repository_adds_genre() {
        gwt.test()
                .given(a_data_source)
                .and(a_genre_mapper)
                .and(a_genre_repository)
                .when(adding_a_genre_to_the_repository)
                .then(the_repository_adds_the_genre_via_the_data_source);
    }

    @Test
    void repository_finds_genre_by_name() {
        gwt.test()
                .given(a_data_source)
                .and(a_genre_mapper)
                .and(a_genre_repository)
                .when(requesting_a_genre_by_name)
                .then(the_genre_with_that_name_is_returned);
    }

    @Test
    void repository_retrieves_all_genres() {
        gwt.test()
                .given(a_data_source)
                .and(a_genre_mapper)
                .and(a_genre_repository)
                .when(requesting_all_genres)
                .then(all_genres_are_returned);
    }

    private final GwtFunction<GenreRepositoryTestContext> a_data_source = context -> context.genreDataSource = Mockito.mock(GenreDataSource.class);

    private final GwtFunction<GenreRepositoryTestContext> a_genre_mapper = context -> context.genreMapper = Mockito.mock(GenreMapper.class);

    private final GwtFunction<GenreRepositoryTestContext> a_genre_repository
            = context -> context.genreRepository = new GenreRepositoryImpl(context.genreDataSource, context.genreMapper);

    private final GwtFunction<GenreRepositoryTestContext> adding_a_genre_to_the_repository = context -> {
        Genre genre = createGenre("Science Fiction", 1.1);
        context.genreData = getGenreData(genre);
        when(context.genreMapper.mapFrom(genre)).thenReturn(context.genreData);
        context.genreRepository.addGenre(genre);
    };

    private final GwtFunction<GenreRepositoryTestContext> requesting_a_genre_by_name = context -> {
        Genre genre = createGenre("Science Fiction", 1.1);
        context.genreData = getGenreData(genre);
        when(context.genreMapper.mapFrom(context.genreData)).thenReturn(genre);
        when(context.genreDataSource.findByName("Science Fiction")).thenReturn(Collections.singletonList(context.genreData));
        context.searchResult = context.genreRepository.getGenreByName("Science Fiction");
    };

    private final GwtFunction<GenreRepositoryTestContext> requesting_all_genres = context -> {
        List<GenreData> genreDataList = List.of(
                getGenreData(createGenre("Science Fiction", 1.1)),
                getGenreData(createGenre("Music", 1.0))
        );
        genreDataList.forEach(genreData -> {
            when(context.genreMapper.mapFrom(context.genreData)).thenReturn(getGenre(context.genreData));
        });
        when(context.genreDataSource.findAll()).thenReturn(genreDataList);
        context.searchResultList = context.genreRepository.getAllGenres();
    };

    private final GwtFunction<GenreRepositoryTestContext> the_repository_adds_the_genre_via_the_data_source = context -> {
        verify(context.genreDataSource).save(context.genreData);
    };

    private final GwtFunction<GenreRepositoryTestContext> the_genre_with_that_name_is_returned = context -> {
        assert context.searchResult.isPresent();
        Genre genre = context.searchResult.get();
        assertThat(genre.getName(), is("Science Fiction"));
    };

    private final GwtFunction<GenreRepositoryTestContext> all_genres_are_returned = context -> {
        assertThat(context.searchResultList.size(), is(2));
        Genre genre = context.searchResultList.get(0);
        assertThat(genre.getName(), is("Science Fiction"));
        genre = context.searchResultList.get(1);
        assertThat(genre.getName(), is("Music"));
    };

    private Genre createGenre(String genreName, double pricingFactor) {
        return new Genre(genreName, pricingFactor);
    }

    private GenreData getGenreData(Genre genre) {
        GenreData genreData = new GenreData();
        genreData.setName(genre.getName());
        genreData.setPricingFactor(genre.getPricingFactor());
        return genreData;
    }

    private Genre getGenre(GenreData genreData) {
        return new Genre(genreData.getName(), genreData.getPricingFactor());
    }

    public static final class GenreRepositoryTestContext extends Context {
        GenreData genreData;
        GenreDataSource genreDataSource;
        GenreMapper genreMapper;
        GenreRepositoryImpl genreRepository;
        List<Genre> searchResultList;
        Optional<Genre> searchResult;
    }

}
