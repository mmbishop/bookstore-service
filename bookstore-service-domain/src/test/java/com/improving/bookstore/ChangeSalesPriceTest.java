package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.interactors.BookNotFoundException;
import com.improving.bookstore.interactors.ChangeSalesPriceInteractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

public class ChangeSalesPriceTest {

    private Book book;
    private BookRepository bookRepository;

    @Test
    void book_sales_price_is_changed() {
        given_a_book_repository();
        given_a_book();
        when_a_sales_price_change_is_requested();
        then_the_sales_price_is_changed();
    }

    @Test
    void an_error_occurs_when_attempting_to_change_the_sales_price_of_a_book_we_do_not_have() {
        given_a_book_repository();
        Assertions.assertThrows(BookNotFoundException.class, this::when_a_sales_price_change_is_requested_for_a_book_we_do_not_have);
    }

    private void given_a_book_repository() {
        bookRepository = Mockito.mock(BookRepository.class);
    }

    private void given_a_book() {
        book = getBook();
    }

    private void when_a_sales_price_change_is_requested() {
        when(bookRepository.getBookById(1)).thenReturn(Optional.of(book));
        ChangeSalesPriceInteractor changeSalesPriceInteractor = new ChangeSalesPriceInteractor(bookRepository);
        changeSalesPriceInteractor.changeSalesPrice(1, BigDecimal.valueOf(35.0));
    }

    private void when_a_sales_price_change_is_requested_for_a_book_we_do_not_have() {
        ChangeSalesPriceInteractor changeSalesPriceInteractor = new ChangeSalesPriceInteractor(bookRepository);
        changeSalesPriceInteractor.changeSalesPrice(0, BigDecimal.valueOf(35.0));
    }

    private void then_the_sales_price_is_changed() {
        assertThat(book.getPrice(), is(BigDecimal.valueOf(35.0)));
        verify(bookRepository).saveBook(book);
    }

    private Book getBook() {
        Book book = new Book("War and Peace", new Author("Leo", null, "Tolstoy"),
                null, 1960, "An ISBN", 1200, new Genre("Fiction", 1.0));
        book.setPrice(BigDecimal.valueOf(40.0));
        return book;
    }

}
