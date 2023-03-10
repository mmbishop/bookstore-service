package com.improving.bookstore;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;
import com.improving.bookstore.interactors.RetrieveAllGenresInteractor;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

public class GenreRetrievalTest {

    private final GwtTest<GenreTestContext> gwt = new GwtTest<>(GenreTestContext.class);

    @Test
    void all_genres_are_retrieved() {
        gwt.test()
                .given(a_genre_repository)
                .and(genres)
                .when(requesting_all_genres)
                .then(all_genres_are_retrieved);
    }

    private final GwtFunction<GenreTestContext> a_genre_repository = context -> context.genreRepository = Mockito.mock(GenreRepository.class);

    private final GwtFunction<GenreTestContext> genres = context -> context.genreList = getAllGenres();

    private final GwtFunction<GenreTestContext> requesting_all_genres = context -> {
        when(context.genreRepository.getAllGenres()).thenReturn(getAllGenres());
        context.genreList = new RetrieveAllGenresInteractor(context.genreRepository).retrieveAllGenres();
    };

    private final GwtFunction<GenreTestContext> all_genres_are_retrieved = context -> {
        assertThat(context.genreList.size(), is(context.allGenres.size()));
    };

    private List<Genre> getAllGenres() {
        return List.of(
                new Genre("Science Fiction", 1.1),
                new Genre("Fantasy", 1.0),
                new Genre("Travel", 1.0),
                new Genre("Mystery", 1.1),
                new Genre("Music", 1.2)
        );
    }

    public static final class GenreTestContext extends Context {
        GenreRepository genreRepository;
        List<Genre> allGenres;
        List<Genre> genreList;
    }

}
