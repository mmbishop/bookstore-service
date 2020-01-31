package com.improving.bookstore;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;
import com.improving.bookstore.interactors.RetrieveAllGenresInteractor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

public class GenreRetrievalTest {

    private GenreRepository genreRepository;
    private List<Genre> allGenres = getAllGenres();
    private List<Genre> genreList;

    @Test
    void all_genres_are_retrieved() {
        given_a_genre_repository();
        when_all_genres_are_requested();
        then_all_genres_are_retrieved();
    }

    private void given_a_genre_repository() {
        genreRepository = Mockito.mock(GenreRepository.class);
    }

    private void when_all_genres_are_requested() {
        when(genreRepository.getAllGenres()).thenReturn(getAllGenres());
        genreList = new RetrieveAllGenresInteractor(genreRepository).invoke();
    }

    private void then_all_genres_are_retrieved() {
        assertThat(genreList.size(), is(allGenres.size()));
    }

    private List<Genre> getAllGenres() {
        return List.of(
                new Genre("Science Fiction", 1.1),
                new Genre("Fantasy", 1.0),
                new Genre("Travel", 1.0),
                new Genre("Mystery", 1.1),
                new Genre("Music", 1.2)
        );
    }

}
