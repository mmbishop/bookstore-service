package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.interactors.BookNotFoundException;
import com.improving.bookstore.interactors.SellBookInteractor;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SellBookTest {

    private final GwtTest<SellBookTestContext> gwt = new GwtTest<>(SellBookTestContext.class);

    @Test
    void book_is_removed_from_inventory_when_sold() {
        gwt.test()
                .given(a_book_repository)
                .and(a_book)
                .when(selling_the_book)
                .then(the_book_is_removed_from_the_inventory);
    }

    @Test
    void an_error_is_noted_when_an_attempt_is_made_to_sell_a_book_that_is_not_in_the_inventory() {
        gwt.test()
                .given(a_book_repository)
                .when(attempting_to_sell_a_book_that_is_not_in_the_inventory)
                .then(the_sale_is_rejected_due_to_book_not_found);
    }

    private final GwtFunction<SellBookTestContext> a_book_repository = context -> context.bookRepository = Mockito.mock(BookRepository.class);

    private final GwtFunction<SellBookTestContext> a_book = context -> {
        context.book = new Book();
        context.book.setTitle("A Book Title");
        context.book.setAuthor(new Author("An", "Unknown", "Author"));
        context.book.setNumberOfPages(300);
    };

    private final GwtFunction<SellBookTestContext> selling_the_book = context -> {
        when(context.bookRepository.getBookById(1)).thenReturn(Optional.of(context.book));
        new SellBookInteractor(context.bookRepository).sellBook(1);
    };

    private final GwtFunction<SellBookTestContext> attempting_to_sell_a_book_that_is_not_in_the_inventory
            = context -> new SellBookInteractor(context.bookRepository).sellBook(2);

    private final GwtFunction<SellBookTestContext> the_book_is_removed_from_the_inventory
            = context -> verify(context.bookRepository).deleteBook(context.book);

    private final GwtFunction<SellBookTestContext> the_sale_is_rejected_due_to_book_not_found = context -> {
        assertThat(context.thrownException, is(not(nullValue())));
        assertThat(context.thrownException, instanceOf(BookNotFoundException.class));
    };

    public static final class SellBookTestContext extends Context {
        Book book;
        BookRepository bookRepository;
    }

}
