package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.Offer;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.repositories.GenreRepository;
import com.improving.bookstore.usecases.PurchaseBookUseCase;
import com.improving.bookstore.usecases.UnwantedGenreException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PurchaseBookTest {

    private Book book;
    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private Offer offer;

    @Test
    void book_is_purchased_when_genre_is_one_that_the_bookstore_wants() {
        given_a_book_repository();
        given_a_genre_repository();
        given_a_book();
        when_the_book_is_sold_to_the_bookstore_for_genre("Science Fiction");
        then_an_offer_is_generated();
        then_the_book_is_added_to_the_inventory();
    }

    @Test
    void book_is_rejected_when_genre_is_not_one_that_the_bookstore_wants() {
        given_a_book_repository();
        given_a_genre_repository();
        given_a_book();
        assertThrows(UnwantedGenreException.class, () -> {
            when_the_book_is_sold_to_the_bookstore_for_genre("Travel");
        });
    }

    private void given_a_book_repository() {
        bookRepository = Mockito.mock(BookRepository.class);
    }

    private void given_a_genre_repository() {
        genreRepository = Mockito.mock(GenreRepository.class);
        when(genreRepository.getGenreByName("Science Fiction")).thenReturn(getScienceFictionGenre());
    }

    private void given_a_book() {
        book = new Book();
        book.setTitle("A Book Title");
        book.setAuthor(new Author("An", "Unknown", "Author"));
        book.setNumberOfPages(300);
    }

    private void when_the_book_is_sold_to_the_bookstore_for_genre(String genreName) {
        PurchaseBookUseCase purchaseBookUseCase = new PurchaseBookUseCase(book, genreName, bookRepository, genreRepository);
        offer = purchaseBookUseCase.invoke();
    }

    private void then_an_offer_is_generated() {
        assertThat(offer, is(not(nullValue())));
        assertThat(offer.getOfferPrice(), is(BigDecimal.valueOf(7.0)));
    }

    private void then_the_book_is_added_to_the_inventory() {
        verify(bookRepository).addBook(book);
    }

    private Optional<Genre> getScienceFictionGenre() {
        return Optional.of(new Genre("Science Fiction", 1.0));
    }

}
