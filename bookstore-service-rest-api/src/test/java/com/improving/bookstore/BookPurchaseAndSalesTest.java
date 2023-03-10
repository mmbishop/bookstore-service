package com.improving.bookstore;

import com.improving.bookstore.interactors.BookSaleInteractor;
import com.improving.bookstore.interactors.PurchaseBookInteractor;
import com.improving.bookstore.interactors.SellBookInteractor;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.BookPurchaseInvoice;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookPurchaseAndSalesTest {

    private final GwtTest<PurchaseTestContext> gwt = new GwtTest<>(PurchaseTestContext.class);

    @Test
    void book_is_purchased_from_seller() {
        gwt.test()
                .given(a_bookstore_service)
                .and(an_iteractor_for_purchasing_books)
                .when(purchasing_a_book_from_a_seller)
                .then(a_purchase_invoice_is_produced_for_the_seller);
    }

    @Test
    void book_sales_price_is_changed() {
        gwt.test()
                .given(a_bookstore_service)
                .and(an_iteractor_for_changing_the_sales_price)
                .when(requesting_a_book_sales_price_change)
                .then(the_book_sales_price_is_changed);
    }

    @Test
    void book_is_sold_to_a_customer() {
        gwt.test()
                .given(a_bookstore_service)
                .and(an_interactor_for_selling_a_book)
                .when(selling_a_book)
                .then(the_book_is_removed_from_the_inventory);
    }

    private final GwtFunction<PurchaseTestContext> a_bookstore_service = context -> context.bookstoreService = new BookstoreService();

    private final GwtFunction<PurchaseTestContext> an_iteractor_for_purchasing_books = context -> {
        context.purchaseBookInteractor = Mockito.mock(PurchaseBookInteractor.class);
        context.bookstoreService.setPurchaseBookInteractor(context.purchaseBookInteractor);
    };

    private final GwtFunction<PurchaseTestContext> an_iteractor_for_changing_the_sales_price = context -> {
        context.bookSaleInteractor = Mockito.mock(BookSaleInteractor.class);
        context.bookstoreService.setBookSaleInteractor(context.bookSaleInteractor);
    };

    private final GwtFunction<PurchaseTestContext> an_interactor_for_selling_a_book = context -> {
        context.sellBookInteractor = Mockito.mock(SellBookInteractor.class);
        context.bookstoreService.setSellBookInteractor(context.sellBookInteractor);
    };

    private final GwtFunction<PurchaseTestContext> purchasing_a_book_from_a_seller = context -> {
        context.book = getRedMarsBook();
        context.author = getAuthorKimStanleyRobinson();
        when(context.purchaseBookInteractor.purchaseBook(context.book, context.author, "Science Fiction"))
                .thenReturn(getBookPurchaseInvoice(context.book));
        context.bookPurchaseInvoice = context.bookstoreService.purchaseBook(context.book, context.author,"Science Fiction");
    };

    private final GwtFunction<PurchaseTestContext> requesting_a_book_sales_price_change
            = context -> context.bookstoreService.putBookOnSale(1, BigDecimal.valueOf(12.0));

    private final GwtFunction<PurchaseTestContext> selling_a_book = context -> context.bookstoreService.sellBook(1);

    private final GwtFunction<PurchaseTestContext> a_purchase_invoice_is_produced_for_the_seller = context -> {
        assertThat(context.bookPurchaseInvoice.getBook(), is(context.book));
        assertThat(context.bookPurchaseInvoice.getPurchasePrice().toString(), is("6.30"));
    };

    private final GwtFunction<PurchaseTestContext> the_book_sales_price_is_changed
            = context -> verify(context.bookSaleInteractor).putBookOnSale(1, BigDecimal.valueOf(12.0));

    private final GwtFunction<PurchaseTestContext> the_book_is_removed_from_the_inventory
            = context -> verify(context.sellBookInteractor).sellBook(1);

    private Book getRedMarsBook() {
        return createBook("Red Mars", getAuthorKimStanleyRobinson(), "A Publisher", 1995,
                "An ISBN", 450, new Genre("Science Fiction", 1.0));
    }

    private Author getAuthorKimStanleyRobinson() {
        return new Author("Kim", "Stanley", "Robinson");
    }

    private Book createBook(String title, Author author, String publisher, int publishYear, String isbn, int numberOfPages, Genre genre) {
        return new Book(title, author, publisher, publishYear, isbn, numberOfPages, genre);
    }

    private BookPurchaseInvoice getBookPurchaseInvoice(Book book) {
        return new BookPurchaseInvoice(book);
    }

    public static final class PurchaseTestContext extends Context {
        Author author;
        Book book;
        BookPurchaseInvoice bookPurchaseInvoice;
        BookstoreService bookstoreService;
        BookSaleInteractor bookSaleInteractor;
        PurchaseBookInteractor purchaseBookInteractor;
        SellBookInteractor sellBookInteractor;
    }

}
