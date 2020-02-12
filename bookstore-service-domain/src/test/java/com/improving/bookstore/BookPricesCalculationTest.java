package com.improving.bookstore;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.BookPurchaseInvoice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class BookPricesCalculationTest {

    private Book book;
    private BigDecimal offerPrice;
    private BigDecimal salesPrice;
    private BookPurchaseInvoice offer;

    @Test
    public void book_sales_price_is_calculated_from_page_count() {
        given_a_book_with_page_count(300);
        when_its_sales_price_is_calculated();
        then_the_calculated_sales_price_is("6.00");

        given_a_book_with_page_count(150);
        when_its_sales_price_is_calculated();
        then_the_calculated_sales_price_is("3.00");
    }

    @Test
    public void book_offer_price_is_calculated_from_page_count() {
        given_a_book_with_page_count(300);
        given_an_offer_to_buy_the_book();
        when_its_offer_price_is_calculated();
        then_the_calculated_offer_price_is("4.20");

        given_a_book_with_page_count(450);
        given_an_offer_to_buy_the_book();
        when_its_offer_price_is_calculated();
        then_the_calculated_offer_price_is("6.30");
    }

    private void given_a_book_with_page_count(int pageCount) {
        book = new Book("A Book Title", null, "A Publisher", 2019, "ISBN1", pageCount, getGenre());
    }

    private void given_an_offer_to_buy_the_book() {
        offer = new BookPurchaseInvoice(book);
    }

    private void when_its_sales_price_is_calculated() {
        salesPrice = book.getPrice();
    }

    private void when_its_offer_price_is_calculated() {
        offerPrice = offer.getPurchasePrice();
    }

    private void then_the_calculated_sales_price_is(String expectedSalesPrice) {
        assertThat(salesPrice.toString(), is(expectedSalesPrice));
    }

    private void then_the_calculated_offer_price_is(String expectedOfferPrice) {
        assertThat(offerPrice.toString(), is(expectedOfferPrice));
    }

    private Genre getGenre() {
        return new Genre("Science Fiction", 1.0);
    }

}
