package com.improving.bookstore;

import com.improving.bookstore.interactors.DeleteGenreInteractor;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class DeleteGenreTest {

    private Genre genre;
    private GenreRepository genreRepository;

    @Test
    void genre_is_deleted_when_it_exists() {
        given_a_genre_repository();
        given_a_genre();
        when_a_request_is_made_to_delete_an_existing_genre();
        then_the_genre_is_deleted();
    }

    @Test
    void no_attempt_is_made_to_delete_a_genre_that_does_not_exist() {
        given_a_genre_repository();
        given_a_genre();
        when_a_request_is_made_to_delete_an_nonexistent_genre();
        then_no_genre_is_deleted();
    }

    private void given_a_genre_repository() {
        genreRepository = Mockito.mock(GenreRepository.class);
    }

    private void given_a_genre() {
        genre = new Genre("Travel", 1.0);
    }

    private void when_a_request_is_made_to_delete_an_existing_genre() {
        when(genreRepository.getGenreByName("Travel")).thenReturn(Optional.of(genre));
        new DeleteGenreInteractor(genreRepository).deleteGenre("Travel");
    }

    private void when_a_request_is_made_to_delete_an_nonexistent_genre() {
        when(genreRepository.getGenreByName("Travel")).thenReturn(Optional.empty());
        new DeleteGenreInteractor(genreRepository).deleteGenre("Travel");
    }

    private void then_the_genre_is_deleted() {
        verify(genreRepository).deleteGenre(genre);
    }

    private void then_no_genre_is_deleted() {
        verify(genreRepository, never()).deleteGenre(genre);
    }

}
