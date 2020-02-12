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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PurchaseBookTest {

    private Author author;
    private AuthorRepository authorRepository;
    private Book book;
    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private BookPurchaseInvoice offer;

    @Test
    void book_is_purchased_when_genre_is_one_that_the_bookstore_wants() {
        given_a_book_repository();
        given_a_genre_repository();
        given_an_author_repository();
        given_an_author("Edgar", "Allan", "Poe");
        given_a_book_with_title("The Raven");
        when_the_book_is_sold_to_the_bookstore_for_genre("Science Fiction");
        then_an_offer_is_generated();
        then_the_book_is_added_to_the_inventory();
    }

    @Test
    void book_by_new_author_is_purchased_when_genre_is_one_that_the_bookstore_wants() {
        given_a_book_repository();
        given_an_author_repository();
        given_a_genre_repository();
        given_an_author("An", "Unknown", "Author");
        given_a_book_with_title("A Title");
        when_the_book_with_new_author_is_sold_to_the_bookstore();
        then_an_offer_is_generated();
        then_the_author_is_added_to_the_repository();
        then_the_book_is_added_to_the_inventory();
    }

    @Test
    void book_is_rejected_when_genre_is_not_one_that_the_bookstore_wants() {
        given_a_book_repository();
        given_an_author_repository();
        given_a_genre_repository();
        given_an_author("A", null, "Traveler");
        given_a_book_with_title("Travel the World");
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

    private void given_a_book_with_title(String title) {
        book = new Book();
        book.setTitle(title);
        book.setNumberOfPages(500);
    }

    private void given_an_author(String firstName, String middleName, String lastName) {
        author = new Author(firstName, middleName, lastName);
    }

    private void when_the_book_is_sold_to_the_bookstore_for_genre(String genreName) {
        when(authorRepository.getAuthorByExample(author)).thenReturn(Optional.of(author));
        PurchaseBookInteractor purchaseBookInteractor = new PurchaseBookInteractor(bookRepository, authorRepository, genreRepository);
        offer = purchaseBookInteractor.purchaseBook(book, author, genreName);
    }

    private void when_the_book_with_new_author_is_sold_to_the_bookstore() {
        when(authorRepository.getAuthorByExample(author)).thenReturn(Optional.empty());
        when(authorRepository.addAuthor(author)).thenReturn(author);
        PurchaseBookInteractor purchaseBookInteractor = new PurchaseBookInteractor(bookRepository, authorRepository, genreRepository);
        offer = purchaseBookInteractor.purchaseBook(book, author, "Science Fiction");
    }

    private void then_an_offer_is_generated() {
        assertThat(offer, is(not(nullValue())));
        assertThat(offer.getPurchasePrice().toString(), is("7.00"));
    }

    private void then_the_book_is_added_to_the_inventory() {
        verify(bookRepository).addBook(book);
    }

    private void then_the_author_is_added_to_the_repository() {
        verify(authorRepository).addAuthor(author);
    }

    private Optional<Genre> getScienceFictionGenre() {
        return Optional.of(new Genre("Science Fiction", 1.0));
    }

}
