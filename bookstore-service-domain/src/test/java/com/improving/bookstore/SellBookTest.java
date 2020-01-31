package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.interactors.BookNotFoundException;
import com.improving.bookstore.interactors.SellBookInteractor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SellBookTest {

    private Book book;
    private BookRepository bookRepository;

    @Test
    void book_is_removed_from_inventory_when_sold() {
        given_a_book_repository();
        given_a_book();
        when_the_book_is_sold();
        then_the_book_is_removed_from_the_inventory();
    }

    @Test
    void an_error_is_noted_when_an_attempt_is_made_to_sell_a_book_that_is_not_in_the_inventory() {
        given_a_book_repository();
        assertThrows(BookNotFoundException.class, this::when_an_attempt_is_made_to_sell_a_book_that_is_not_in_the_inventory);
    }

    private void given_a_book_repository() {
        bookRepository = Mockito.mock(BookRepository.class);
    }

    private void given_a_book() {
        book = new Book();
        book.setTitle("A Book Title");
        book.setAuthor(new Author("An", "Unknown", "Author"));
        book.setNumberOfPages(300);
    }

    private void when_the_book_is_sold() {
        when(bookRepository.getBookById(1)).thenReturn(Optional.of(book));
        new SellBookInteractor(bookRepository).sellBook(1);
    }

    private void when_an_attempt_is_made_to_sell_a_book_that_is_not_in_the_inventory() {
        new SellBookInteractor(bookRepository).sellBook(2);
    }

    private void then_the_book_is_removed_from_the_inventory() {
        verify(bookRepository).deleteBook(book);
    }

}
