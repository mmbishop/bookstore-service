package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.BookPurchaseInvoice;
import com.improving.bookstore.repositories.AuthorRepository;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.repositories.GenreRepository;
import com.improving.bookstore.interactors.PurchaseBookInteractor;
import com.improving.bookstore.interactors.UnwantedGenreException;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArguments;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PurchaseBookTest {

    private final GwtTest<PurchaseTestContext> gwt = new GwtTest<>(PurchaseTestContext.class);

    @Test
    void book_is_purchased_when_genre_is_one_that_the_bookstore_wants() {
        gwt.test()
                .given(a_book_repository)
                .and(a_genre_repository)
                .and(an_author_repository)
                .and(an_author, "Edgar", "Allan", "Poe")
                .and(a_book_with_title, "The Raven")
                .when(selling_the_book_to_the_bookstore_for_genre, "Science Fiction")
                .then(an_offer_is_generated)
                .and(the_book_is_added_to_the_inventory);
    }

    @Test
    void book_by_new_author_is_purchased_when_genre_is_one_that_the_bookstore_wants() {
        gwt.test()
                .given(a_book_repository)
                .and(an_author_repository)
                .and(a_genre_repository)
                .and(an_author, "An", "Unknown", "Author")
                .and(a_book_with_title, "A Title")
                .when(selling_the_book_with_new_author_to_the_bookstore)
                .then(an_offer_is_generated)
                .and(the_author_is_added_to_the_repository)
                .and(the_book_is_added_to_the_inventory);
    }

    @Test
    void book_is_rejected_when_genre_is_not_one_that_the_bookstore_wants() {
        gwt.test()
                .given(a_book_repository)
                .and(an_author_repository)
                .and(a_genre_repository)
                .and(an_author, "A", null, "Traveler")
                .and(a_book_with_title, "Travel the World")
                .when(selling_the_book_to_the_bookstore_for_genre, "Travel")
                .then(the_book_is_rejected_due_to_unwanted_genre);
    }

    private final GwtFunction<PurchaseTestContext> an_author_repository = context -> {
        context.authorRepository = Mockito.mock(AuthorRepository.class);
        Author edgarAllanPoe = new Author("Edgar", "Allan", "Poe");
        when(context.authorRepository.getAuthorByExample(edgarAllanPoe)).thenReturn(Optional.of(edgarAllanPoe));
    };

    private final GwtFunction<PurchaseTestContext> a_book_repository = context -> context.bookRepository = Mockito.mock(BookRepository.class);

    private final GwtFunction<PurchaseTestContext> a_genre_repository = context -> {
        context.genreRepository = Mockito.mock(GenreRepository.class);
        when(context.genreRepository.getGenreByName("Science Fiction")).thenReturn(getScienceFictionGenre());
    };

    private final GwtFunctionWithArgument<PurchaseTestContext, String> a_book_with_title = (context, title) -> {
        context.book = new Book();
        context.book.setTitle(title);
        context.book.setNumberOfPages(500);
    };

    private final GwtFunctionWithArguments<PurchaseTestContext, String> an_author = (context, nameParts) -> {
        context.author = new Author(nameParts[0], nameParts[1], nameParts[2]);
    };

    private final GwtFunctionWithArgument<PurchaseTestContext, String> selling_the_book_to_the_bookstore_for_genre = (context, genreName) -> {
        when(context.authorRepository.getAuthorByExample(context.author)).thenReturn(Optional.of(context.author));
        PurchaseBookInteractor purchaseBookInteractor
                = new PurchaseBookInteractor(context.bookRepository, context.authorRepository, context.genreRepository);
        context.offer = purchaseBookInteractor.purchaseBook(context.book, context.author, genreName);
    };

    private final GwtFunction<PurchaseTestContext> selling_the_book_with_new_author_to_the_bookstore = context -> {
        when(context.authorRepository.getAuthorByExample(context.author)).thenReturn(Optional.empty());
        when(context.authorRepository.addAuthor(context.author)).thenReturn(context.author);
        PurchaseBookInteractor purchaseBookInteractor
                = new PurchaseBookInteractor(context.bookRepository, context.authorRepository, context.genreRepository);
        context.offer = purchaseBookInteractor.purchaseBook(context.book, context.author, "Science Fiction");
    };

    private final GwtFunction<PurchaseTestContext> an_offer_is_generated = context -> {
        assertThat(context.offer, is(not(nullValue())));
        assertThat(context.offer.getPurchasePrice().toString(), is("7.00"));
    };

    private final GwtFunction<PurchaseTestContext> the_book_is_added_to_the_inventory
            = context -> verify(context.bookRepository).addBook(context.book);

    private final GwtFunction<PurchaseTestContext> the_author_is_added_to_the_repository
            = context -> verify(context.authorRepository).addAuthor(context.author);

    private final GwtFunction<PurchaseTestContext> the_book_is_rejected_due_to_unwanted_genre = context -> {
        assertThat(context.thrownException, is(not(nullValue())));
        assertThat(context.thrownException, instanceOf(UnwantedGenreException.class));
    };

    private Optional<Genre> getScienceFictionGenre() {
        return Optional.of(new Genre("Science Fiction", 1.0));
    }

    public static final class PurchaseTestContext extends Context {
        Author author;
        AuthorRepository authorRepository;
        Book book;
        BookRepository bookRepository;
        GenreRepository genreRepository;
        BookPurchaseInvoice offer;
    }

}
