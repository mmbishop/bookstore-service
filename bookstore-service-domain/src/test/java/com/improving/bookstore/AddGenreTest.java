package com.improving.bookstore;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;
import com.improving.bookstore.usecases.AddGenreInteractor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class AddGenreTest {

    private Genre genre;
    private GenreRepository genreRepository;

    @Test
    void genre_is_added() {
        given_a_genre_repository();
        given_a_genre();
        when_a_request_is_made_to_add_the_genre();
        then_the_genre_is_added();
    }

    private void given_a_genre_repository() {
        genreRepository = Mockito.mock(GenreRepository.class);
    }

    private void given_a_genre() {
        genre = new Genre("Science Fiction", 1.1);
    }

    private void when_a_request_is_made_to_add_the_genre() {
        new AddGenreInteractor(genreRepository).addGenre(genre);
    }

    private void then_the_genre_is_added() {
        verify(genreRepository).addGenre(genre);
    }

}
