package com.improving.bookstore;

import com.improving.bookstore.interactors.DeleteGenreInteractor;
import com.improving.bookstore.interactors.GenreDeletionException;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.repositories.GenreRepository;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class DeleteGenreTest {

    private GwtTest<DeleteGenreTestContext> gwt = new GwtTest<>(DeleteGenreTestContext.class);

    @Test
    void genre_is_deleted_when_it_exists() {
        gwt.test()
                .given(a_genre_repository)
                .and(a_book_repository)
                .and(a_genre)
                .when(requesting_to_delete_an_existing_genre)
                .then(the_genre_is_deleted);
    }

    @Test
    void no_attempt_is_made_to_delete_a_genre_that_does_not_exist() {
        gwt.test()
                .given(a_genre_repository)
                .and(a_book_repository)
                .and(a_genre)
                .when(requesting_to_delete_a_nonexistent_genre)
                .then(no_genre_is_deleted);
    }

    @Test
    void an_error_occurs_when_a_genre_is_deleted_for_which_books_exist() {
        gwt.test()
                .given(a_genre_repository)
                .and(a_book_repository)
                .and(a_genre)
                .when(requesting_to_delete_a_genre_for_which_books_exist)
                .then(an_exception_is_thrown);
    }

    private final GwtFunction<DeleteGenreTestContext> a_genre_repository
            = context -> context.genreRepository = Mockito.mock(GenreRepository.class);

    private final GwtFunction<DeleteGenreTestContext> a_genre = context -> context.genre = new Genre("Travel", 1.0);

    private final GwtFunction<DeleteGenreTestContext> a_book_repository
            = context -> context.bookRepository = Mockito.mock(BookRepository.class);

    private final GwtFunction<DeleteGenreTestContext> requesting_to_delete_an_existing_genre = context -> {
        when(context.genreRepository.getGenreByName("Travel")).thenReturn(Optional.of(context.genre));
        when(context.bookRepository.getBooksByGenre("Travel")).thenReturn(Collections.emptyList());
        new DeleteGenreInteractor(context.genreRepository, context.bookRepository).deleteGenre("Travel");
    };

    private final GwtFunction<DeleteGenreTestContext> requesting_to_delete_a_nonexistent_genre = context -> {
        when(context.genreRepository.getGenreByName("Travel")).thenReturn(Optional.empty());
        new DeleteGenreInteractor(context.genreRepository, context.bookRepository).deleteGenre("Travel");
    };

    private final GwtFunction<DeleteGenreTestContext> requesting_to_delete_a_genre_for_which_books_exist = context -> {
        when(context.genreRepository.getGenreByName("Travel")).thenReturn(Optional.of(context.genre));
        when(context.bookRepository.getBooksByGenre("Travel")).thenReturn(Collections.singletonList(createBook()));
        new DeleteGenreInteractor(context.genreRepository, context.bookRepository).deleteGenre("Travel");
    };

    private final GwtFunction<DeleteGenreTestContext> the_genre_is_deleted
            = context -> verify(context.genreRepository).deleteGenre(context.genre);

    private final GwtFunction<DeleteGenreTestContext> no_genre_is_deleted
            = context -> verify(context.genreRepository, never()).deleteGenre(context.genre);

    private final GwtFunction<DeleteGenreTestContext> an_exception_is_thrown = context -> {
        assertThat(context.thrownException, is(not(nullValue())));
        assertThat(context.thrownException, instanceOf(GenreDeletionException.class));
    };

    private Book createBook() {
        Book book = new Book();
        book.setTitle("A Title");
        book.setAuthor(new Author("An", "Unknown", "Author"));
        book.setNumberOfPages(300);
        return book;
    }

    public static final class DeleteGenreTestContext extends Context {
        BookRepository bookRepository;
        Genre genre;
        GenreRepository genreRepository;
    }

}
