package com.improving.bookstore;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;
import com.improving.bookstore.interactors.AddGenreInteractor;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class AddGenreTest {

    private final GwtTest<GenreTestContext> gwt = new GwtTest<>(GenreTestContext.class);

    @Test
    void genre_is_added() {
        gwt.test()
                .given(a_genre_repository)
                .and(a_genre)
                .when(requesting_to_add_the_genre)
                .then(the_genre_is_added);
    }

    private final GwtFunction<GenreTestContext> a_genre_repository = context -> context.genreRepository = Mockito.mock(GenreRepository.class);

    private final GwtFunction<GenreTestContext> a_genre = context -> context.genre = new Genre("Science Fiction", 1.1);

    private final GwtFunction<GenreTestContext> requesting_to_add_the_genre
            = context -> new AddGenreInteractor(context.genreRepository).addGenre(context.genre);

    private final GwtFunction<GenreTestContext> the_genre_is_added = context -> verify(context.genreRepository).addGenre(context.genre);

    public static final class GenreTestContext extends Context {
        Genre genre;
        GenreRepository genreRepository;
    }

}
