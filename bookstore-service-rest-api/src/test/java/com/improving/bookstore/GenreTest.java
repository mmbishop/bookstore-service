package com.improving.bookstore;

import com.improving.bookstore.interactors.AddGenreInteractor;
import com.improving.bookstore.interactors.DeleteGenreInteractor;
import com.improving.bookstore.interactors.RetrieveAllGenresInteractor;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GenreTest {

    private AddGenreInteractor addGenreInteractor;
    private BookstoreService bookstoreService;
    private DeleteGenreInteractor deleteGenreInteractor;
    private Genre newGenre;
    private List<Genre> genreList;
    private RetrieveAllGenresInteractor retrieveAllGenresInteractor;

    @Test
    void all_genres_are_retrieved() {
        given_a_bookstore_service();
        given_an_interactor_for_retrieving_all_genres();
        when_all_genres_are_requested();
        then_all_genres_are_returned();
    }

    @Test
    void genre_is_added() {
        given_a_bookstore_service();
        given_an_interactor_for_adding_genres();
        when_a_genre_is_added();
        then_the_genre_is_one_for_which_books_will_be_accepted();
    }

    @Test
    void genre_is_deleted() {
        given_a_bookstore_service();
        given_an_interactor_for_deleting_genres();
        when_a_genre_is_deleted();
        then_the_genre_is_not_one_for_which_books_will_be_accepted();
    }

    private void given_a_bookstore_service() {
        bookstoreService = new BookstoreService();
    }

    private void given_an_interactor_for_retrieving_all_genres() {
        retrieveAllGenresInteractor = Mockito.mock(RetrieveAllGenresInteractor.class);
        bookstoreService.setRetrieveAllGenresInteractor(retrieveAllGenresInteractor);
    }

    private void given_an_interactor_for_adding_genres() {
        addGenreInteractor = Mockito.mock(AddGenreInteractor.class);
        bookstoreService.setAddGenreInteractor(addGenreInteractor);
    }

    private void given_an_interactor_for_deleting_genres() {
        deleteGenreInteractor = Mockito.mock(DeleteGenreInteractor.class);
        bookstoreService.setDeleteGenreInteractor(deleteGenreInteractor);
    }

    private void when_all_genres_are_requested() {
        when(retrieveAllGenresInteractor.retrieveAllGenres()).thenReturn(getGenreList());
        genreList = bookstoreService.getAllGenres();
    }

    private void when_a_genre_is_added() {
        newGenre = new Genre("History", 1.0);
        bookstoreService.addGenre(newGenre);
    }

    private void when_a_genre_is_deleted() {
        bookstoreService.deleteGenre("Travel");
    }

    private void then_all_genres_are_returned() {
        assertThat(genreList.size(), is(2));
        assertThat(genreList.get(0).getName(), is("Science Fiction"));
        assertThat(genreList.get(1).getName(), is("Music"));
    }

    private void then_the_genre_is_one_for_which_books_will_be_accepted() {
        verify(addGenreInteractor).addGenre(newGenre);
    }

    private void then_the_genre_is_not_one_for_which_books_will_be_accepted() {
        verify(deleteGenreInteractor).deleteGenre("Travel");
    }

    private List<Genre> getGenreList() {
        return List.of(
                new Genre("Science Fiction", 1.1),
                new Genre("Music", 1.0)
        );
    }

}
