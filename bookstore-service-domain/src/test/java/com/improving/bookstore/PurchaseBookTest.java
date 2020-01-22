package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.Offer;
import com.improving.bookstore.repositories.AuthorRepository;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.repositories.GenreRepository;
import com.improving.bookstore.usecases.PurchaseBookUseCase;
import com.improving.bookstore.usecases.UnwantedGenreException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PurchaseBookTest {

    private Author newAuthor;
    private AuthorRepository authorRepository;
    private Book book;
    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private Offer offer;

    @Test
    void book_is_purchased_when_genre_is_one_that_the_bookstore_wants() {
        given_a_book_repository();
        given_a_genre_repository();
        given_an_author_repository();
        given_a_book_with_author_that_is_in_the_repository();
        when_the_book_is_sold_to_the_bookstore_for_genre("Science Fiction");
        then_an_offer_is_generated();
        then_the_book_is_added_to_the_inventory();
    }

    @Test
    void book_by_new_author_is_purchased_when_genre_is_one_that_the_bookstore_wants() {
        given_a_book_repository();
        given_an_author_repository();
        given_a_genre_repository();
        given_a_new_author();
        given_a_book_with_a_new_author();
        when_the_book_is_sold_to_the_bookstore_for_genre("Science Fiction");
        then_an_offer_is_generated();
        then_the_author_is_added_to_the_repository();
        then_the_book_is_added_to_the_inventory();
    }

    @Test
    void book_is_rejected_when_genre_is_not_one_that_the_bookstore_wants() {
        given_a_book_repository();
        given_a_genre_repository();
        given_a_book_with_author_that_is_in_the_repository();
        assertThrows(UnwantedGenreException.class, () -> {
            when_the_book_is_sold_to_the_bookstore_for_genre("Travel");
        });
    }

    private void given_an_author_repository() {
        authorRepository = Mockito.mock(AuthorRepository.class);
        Author edgarAllanPoe = new Author("Edgar", "Allan", "Poe");
        when(authorRepository.getAuthorByExample(edgarAllanPoe)).thenReturn(Optional.of(edgarAllanPoe));
    }

    private void given_a_book_repository() {
        bookRepository = Mockito.mock(BookRepository.class);
    }

    private void given_a_genre_repository() {
        genreRepository = Mockito.mock(GenreRepository.class);
        when(genreRepository.getGenreByName("Science Fiction")).thenReturn(getScienceFictionGenre());
    }

    private void given_a_book_with_author_that_is_in_the_repository() {
        book = new Book();
        book.setTitle("The Raven");
        book.setAuthor(new Author("Edgar", "Allan", "Poe"));
        book.setNumberOfPages(300);
    }

    private void given_a_book_with_a_new_author() {
        book = new Book();
        book.setTitle("A Title");
        book.setAuthor(newAuthor);
        book.setNumberOfPages(300);
    }

    private void given_a_new_author() {
        newAuthor = new Author("An", "Unknown", "Author");
    }

    private void when_the_book_is_sold_to_the_bookstore_for_genre(String genreName) {
        PurchaseBookUseCase purchaseBookUseCase = new PurchaseBookUseCase(book, genreName, bookRepository, authorRepository,
                genreRepository);
        offer = purchaseBookUseCase.invoke();
    }

    private void then_an_offer_is_generated() {
        assertThat(offer, is(not(nullValue())));
        assertThat(offer.getOfferPrice(), is(BigDecimal.valueOf(7.0)));
    }

    private void then_the_book_is_added_to_the_inventory() {
        verify(bookRepository).addBook(book);
    }

    private void then_the_author_is_added_to_the_repository() {
        verify(authorRepository).addAuthor(newAuthor);
    }

    private Optional<Genre> getScienceFictionGenre() {
        return Optional.of(new Genre("Science Fiction", 1.0));
    }

}
