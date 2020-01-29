package com.improving.bookstore;

import com.improving.bookstore.mappers.GenreMapper;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.GenreData;
import com.improving.bookstore.repositories.GenreDataSource;
import com.improving.bookstore.repositories.GenreRepositoryImpl;
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

    private GenreData genreData;
    private GenreDataSource genreDataSource;
    private GenreMapper genreMapper;
    private GenreRepositoryImpl genreRepository;
    private List<Genre> searchResultList;
    private Optional<Genre> searchResult;

    @Test
    void repository_adds_genre() {
        given_a_data_source();
        given_a_genre_mapper();
        given_a_genre_repository();
        when_a_genre_is_added_to_the_repository();
        then_the_repository_adds_the_genre_via_the_data_source();
    }

    @Test
    void repository_finds_genre_by_name() {
        given_a_data_source();
        given_a_genre_mapper();
        given_a_genre_repository();
        when_a_genre_is_requested_by_name();
        then_the_genre_with_that_name_is_returned();
    }

    @Test
    void repository_retrieves_all_genres() {
        given_a_data_source();
        given_a_genre_mapper();
        given_a_genre_repository();
        when_all_genres_are_requested();
        then_all_genres_are_returned();
    }

    private void given_a_data_source() {
        genreDataSource = Mockito.mock(GenreDataSource.class);
    }

    private void given_a_genre_mapper() {
        genreMapper = Mockito.mock(GenreMapper.class);
    }

    private void given_a_genre_repository() {
        genreRepository = new GenreRepositoryImpl(genreDataSource, genreMapper);
    }

    private void when_a_genre_is_added_to_the_repository() {
        Genre genre = createGenre("Science Fiction", 1.1);
        genreData = getGenreData(genre);
        when(genreMapper.mapFrom(genre)).thenReturn(genreData);
        genreRepository.addGenre(genre);
    }

    private void when_a_genre_is_requested_by_name() {
        Genre genre = createGenre("Science Fiction", 1.1);
        genreData = getGenreData(genre);
        when(genreMapper.mapFrom(genreData)).thenReturn(genre);
        when(genreDataSource.findByName("Science Fiction")).thenReturn(Collections.singletonList(genreData));
        searchResult = genreRepository.getGenreByName("Science Fiction");
    }

    private void when_all_genres_are_requested() {
        List<GenreData> genreDataList = List.of(
                getGenreData(createGenre("Science Fiction", 1.1)),
                getGenreData(createGenre("Music", 1.0))
        );
        genreDataList.forEach(genreData -> {
            when(genreMapper.mapFrom(genreData)).thenReturn(getGenre(genreData));
        });
        when(genreDataSource.findAll()).thenReturn(genreDataList);
        searchResultList = genreRepository.getAllGenres();
    }

    private void then_the_repository_adds_the_genre_via_the_data_source() {
        verify(genreDataSource).save(genreData);
    }

    private void then_the_genre_with_that_name_is_returned() {
        assert searchResult.isPresent();
        Genre genre = searchResult.get();
        assertThat(genre.getName(), is("Science Fiction"));
    }

    private void then_all_genres_are_returned() {
        assertThat(searchResultList.size(), is(2));
        Genre genre = searchResultList.get(0);
        assertThat(genre.getName(), is("Science Fiction"));
        genre = searchResultList.get(1);
        assertThat(genre.getName(), is("Music"));
    }

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

}
