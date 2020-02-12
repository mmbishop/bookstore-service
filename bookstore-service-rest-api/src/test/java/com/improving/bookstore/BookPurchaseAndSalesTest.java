package com.improving.bookstore;

import com.improving.bookstore.interactors.ChangeSalesPriceInteractor;
import com.improving.bookstore.interactors.PurchaseBookInteractor;
import com.improving.bookstore.interactors.SellBookInteractor;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.BookPurchaseInvoice;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookPurchaseAndSalesTest {

    private Author author;
    private Book book;
    private BookPurchaseInvoice bookPurchaseInvoice;
    private BookstoreService bookstoreService;
    private ChangeSalesPriceInteractor changeSalesPriceInteractor;
    private PurchaseBookInteractor purchaseBookInteractor;
    private SellBookInteractor sellBookInteractor;

    @Test
    void book_is_purchased_from_seller() {
        given_a_bookstore_service();
        given_an_interactor_for_purchasing_books();
        when_a_book_is_purchased_from_a_seller();
        then_a_purchase_invoice_is_produced_for_the_seller();
    }

    @Test
    void book_sales_price_is_changed() {
        given_a_bookstore_service();
        given_an_interactor_for_changing_the_sales_price();
        when_a_book_sales_price_change_is_requested();
        then_the_book_sales_price_is_changed();
    }

    @Test
    void book_is_sold_to_a_customer() {
        given_a_bookstore_service();
        given_an_interactor_for_selling_a_book();
        when_a_book_is_sold();
        then_the_book_is_removed_from_the_inventory();
    }

    private void given_a_bookstore_service() {
        bookstoreService = new BookstoreService();
    }

    private void given_an_interactor_for_purchasing_books() {
        purchaseBookInteractor = Mockito.mock(PurchaseBookInteractor.class);
        bookstoreService.setPurchaseBookInteractor(purchaseBookInteractor);
    }

    private void given_an_interactor_for_changing_the_sales_price() {
        changeSalesPriceInteractor = Mockito.mock(ChangeSalesPriceInteractor.class);
        bookstoreService.setChangeSalesPriceInteractor(changeSalesPriceInteractor);
    }

    private void given_an_interactor_for_selling_a_book() {
        sellBookInteractor = Mockito.mock(SellBookInteractor.class);
        bookstoreService.setSellBookInteractor(sellBookInteractor);
    }

    private void when_a_book_is_purchased_from_a_seller() {
        book = getRedMarsBook();
        author = getAuthorKimStanleyRobinson();
        when(purchaseBookInteractor.purchaseBook(book, author, "Science Fiction")).thenReturn(getBookPurchaseInvoice(book));
        bookPurchaseInvoice = bookstoreService.purchaseBook(book, author,"Science Fiction");
    }

    private void when_a_book_sales_price_change_is_requested() {
        bookstoreService.changeSalesPrice(1, BigDecimal.valueOf(12.0));
    }

    private void when_a_book_is_sold() {
        bookstoreService.sellBook(1);
    }

    private void then_a_purchase_invoice_is_produced_for_the_seller() {
        assertThat(bookPurchaseInvoice.getBook(), is(book));
        assertThat(bookPurchaseInvoice.getPurchasePrice().toString(), is("6.30"));
    }

    private void then_the_book_sales_price_is_changed() {
        verify(changeSalesPriceInteractor).changeSalesPrice(1, BigDecimal.valueOf(12.0));
    }

    private void then_the_book_is_removed_from_the_inventory() {
        verify(sellBookInteractor).sellBook(1);
    }

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

}
