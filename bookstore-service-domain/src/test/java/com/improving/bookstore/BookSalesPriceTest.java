package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.interactors.BookNotFoundException;
import com.improving.bookstore.interactors.BookSaleInteractor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

public class BookSalesPriceTest {

    private Book book;
    private BookRepository bookRepository;

    @Test
    void book_is_put_on_sale() {
        given_a_book_repository();
        given_a_book();
        when_the_book_is_put_on_sale();
        then_the_sales_price_is_reduced();
    }

    @Test
    void book_is_taken_off_sale() {
        given_a_book_repository();
        given_a_book();
        when_the_book_is_taken_off_sale();
        then_the_sales_price_is_reset();
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

    private void when_the_book_is_put_on_sale() {
        when(bookRepository.getBookById(1)).thenReturn(Optional.of(book));
        BookSaleInteractor bookSaleInteractor = new BookSaleInteractor(bookRepository);
        bookSaleInteractor.putBookOnSale(1, BigDecimal.valueOf(20.0));
    }

    private void when_the_book_is_taken_off_sale() {
        when(bookRepository.getBookById(1)).thenReturn(Optional.of(book));
        BookSaleInteractor bookSaleInteractor = new BookSaleInteractor(bookRepository);
        bookSaleInteractor.putBookOnSale(1, BigDecimal.valueOf(20.0));
        bookSaleInteractor.takeBookOffSale(1);
    }

    private void when_a_sales_price_change_is_requested_for_a_book_we_do_not_have() {
        BookSaleInteractor bookSaleInteractor = new BookSaleInteractor(bookRepository);
        bookSaleInteractor.putBookOnSale(0, BigDecimal.valueOf(20.0));
    }

    private void then_the_sales_price_is_reduced() {
        assertThat(book.getPrice().toString(), is("20.00"));
        verify(bookRepository).saveBook(book);
    }

    private void then_the_sales_price_is_reset() {
        assertThat(book.getPrice().toString(), is("24.00"));
        verify(bookRepository, times(2)).saveBook(book);
    }

    private Book getBook() {
        Book book = new Book("War and Peace", new Author("Leo", null, "Tolstoy"),
                null, 1960, "An ISBN", 1200, new Genre("Fiction", 1.0));
        book.setPrice(BigDecimal.valueOf(40.0));
        return book;
    }

}
