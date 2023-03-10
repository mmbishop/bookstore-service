package com.improving.bookstore;

import com.improving.bookstore.interactors.AddGenreInteractor;
import com.improving.bookstore.interactors.DeleteGenreInteractor;
import com.improving.bookstore.interactors.RetrieveAllGenresInteractor;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

public class GenreTest {

    private final GwtTest<GenreTestContext> gwt = new GwtTest<>(GenreTestContext.class);

    @Test
    void all_genres_are_retrieved() {
        gwt.test()
                .given(a_bookstore_service)
                .and(an_interactor_for_retrieving_all_genres)
                .when(requesting_all_genres)
                .then(all_genres_are_returned);
    }

    @Test
    void genre_is_added() {
        gwt.test()
                .given(a_bookstore_service)
                .and(an_interactor_for_adding_genres)
                .when(adding_a_genre)
                .then(the_genre_is_one_for_which_books_will_be_accepted);
    }

    @Test
    void genre_is_deleted() {
        gwt.test()
                .given(a_bookstore_service)
                .and(an_interactor_for_deleting_genres)
                .when(deleting_a_genre)
                .then(the_genre_is_not_one_for_which_books_will_be_accepted);
    }

    private final GwtFunction<GenreTestContext> a_bookstore_service = context -> context.bookstoreService = new BookstoreService();

    private final GwtFunction<GenreTestContext> an_interactor_for_retrieving_all_genres = context -> {
        context.retrieveAllGenresInteractor = Mockito.mock(RetrieveAllGenresInteractor.class);
        context.bookstoreService.setRetrieveAllGenresInteractor(context.retrieveAllGenresInteractor);
    };

    private final GwtFunction<GenreTestContext> an_interactor_for_adding_genres = context -> {
        context.addGenreInteractor = Mockito.mock(AddGenreInteractor.class);
        context.bookstoreService.setAddGenreInteractor(context.addGenreInteractor);
    };

    private final GwtFunction<GenreTestContext> an_interactor_for_deleting_genres = context -> {
        context.deleteGenreInteractor = Mockito.mock(DeleteGenreInteractor.class);
        context.bookstoreService.setDeleteGenreInteractor(context.deleteGenreInteractor);
    };

    private final GwtFunction<GenreTestContext> requesting_all_genres = context -> {
        when(context.retrieveAllGenresInteractor.retrieveAllGenres()).thenReturn(getGenreList());
        context.genreList = context.bookstoreService.getAllGenres();
    };

    private final GwtFunction<GenreTestContext> adding_a_genre = context -> {
        context.newGenre = new Genre("History", 1.0);
        context.bookstoreService.addGenre(context.newGenre);
    };

    private final GwtFunction<GenreTestContext> deleting_a_genre = context -> context.bookstoreService.deleteGenre("Travel");

    private final GwtFunction<GenreTestContext> all_genres_are_returned = context -> {
        assertThat(context.genreList.size(), is(2));
        assertThat(context.genreList.get(0).getName(), is("Science Fiction"));
        assertThat(context.genreList.get(1).getName(), is("Music"));
    };

    private final GwtFunction<GenreTestContext> the_genre_is_one_for_which_books_will_be_accepted = context -> {
        verify(context.addGenreInteractor).addGenre(context.newGenre);
    };

    private final GwtFunction<GenreTestContext> the_genre_is_not_one_for_which_books_will_be_accepted = context -> {
        verify(context.deleteGenreInteractor).deleteGenre("Travel");
    };

    private List<Genre> getGenreList() {
        return List.of(
                new Genre("Science Fiction", 1.1),
                new Genre("Music", 1.0)
        );
    }

    public static final class GenreTestContext extends Context {
        AddGenreInteractor addGenreInteractor;
        BookstoreService bookstoreService;
        DeleteGenreInteractor deleteGenreInteractor;
        Genre newGenre;
        List<Genre> genreList;
        RetrieveAllGenresInteractor retrieveAllGenresInteractor;
    }

}
