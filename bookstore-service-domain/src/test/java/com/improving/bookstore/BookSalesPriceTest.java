package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.interactors.BookNotFoundException;
import com.improving.bookstore.interactors.BookSaleInteractor;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.*;

public class BookSalesPriceTest {

    private final GwtTest<PriceTestContext> gwt = new GwtTest<>(PriceTestContext.class);

    @Test
    void book_is_put_on_sale() {
        gwt.test()
                .given(a_book_repository)
                .and(a_book)
                .when(putting_the_book_on_sale)
                .then(the_sales_price_is_reduced);
    }

    @Test
    void book_is_taken_off_sale() {
        gwt.test()
                .given(a_book_repository)
                .and(a_book)
                .when(taking_the_book_off_sale)
                .then(the_sales_price_is_reset);
    }

    @Test
    void an_error_occurs_when_attempting_to_change_the_sales_price_of_a_book_we_do_not_have() {
        gwt.test()
                .given(a_book_repository)
                .when(requesting_a_sales_price_change_for_a_book_we_do_not_have)
                .then(an_exception_is_thrown);
    }

    private final GwtFunction<PriceTestContext> a_book_repository = context -> context.bookRepository = Mockito.mock(BookRepository.class);

    private final GwtFunction<PriceTestContext> a_book = context -> context.book = getBook();

    private final GwtFunction<PriceTestContext> putting_the_book_on_sale = context -> {
        when(context.bookRepository.getBookById(1)).thenReturn(Optional.of(context.book));
        BookSaleInteractor bookSaleInteractor = new BookSaleInteractor(context.bookRepository);
        bookSaleInteractor.putBookOnSale(1, BigDecimal.valueOf(20.0));
    };

    private final GwtFunction<PriceTestContext> taking_the_book_off_sale = context -> {
        when(context.bookRepository.getBookById(1)).thenReturn(Optional.of(context.book));
        BookSaleInteractor bookSaleInteractor = new BookSaleInteractor(context.bookRepository);
        bookSaleInteractor.putBookOnSale(1, BigDecimal.valueOf(20.0));
        bookSaleInteractor.takeBookOffSale(1);
    };

    private final GwtFunction<PriceTestContext> requesting_a_sales_price_change_for_a_book_we_do_not_have = context -> {
        BookSaleInteractor bookSaleInteractor = new BookSaleInteractor(context.bookRepository);
        bookSaleInteractor.putBookOnSale(0, BigDecimal.valueOf(20.0));
    };

    private final GwtFunction<PriceTestContext> the_sales_price_is_reduced = context -> {
        assertThat(context.book.getPrice().toString(), is("20.00"));
        verify(context.bookRepository).saveBook(context.book);
    };

    private final GwtFunction<PriceTestContext> the_sales_price_is_reset = context -> {
        assertThat(context.book.getPrice().toString(), is("24.00"));
        verify(context.bookRepository, times(2)).saveBook(context.book);
    };

    private final GwtFunction<PriceTestContext> an_exception_is_thrown = context -> {
        assertThat(context.thrownException, is(not(nullValue())));
        assertThat(context.thrownException, instanceOf(BookNotFoundException.class));
    };

    private Book getBook() {
        Book book = new Book("War and Peace", new Author("Leo", null, "Tolstoy"),
                null, 1960, "An ISBN", 1200, new Genre("Fiction", 1.0));
        book.setPrice(BigDecimal.valueOf(40.0));
        return book;
    }

    public static final class PriceTestContext extends Context {
        Book book;
        BookRepository bookRepository;
    }

}
