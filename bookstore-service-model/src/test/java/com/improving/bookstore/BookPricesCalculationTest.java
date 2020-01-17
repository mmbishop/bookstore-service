package com.improving.bookstore;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.Offer;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class BookPricesCalculationTest {

    private Book book;
    private BigDecimal offerPrice;
    private BigDecimal salesPrice;
    private Offer offer;

    @Test
    public void book_sales_price_is_calculated() {
        given_a_book_with_page_count(300);
        when_its_sales_price_is_calculated();
        then_the_calculated_sales_price_is(BigDecimal.valueOf(10.0));

        given_a_book_with_page_count(150);
        when_its_sales_price_is_calculated();
        then_the_calculated_sales_price_is(BigDecimal.valueOf(5.0));
    }

    @Test
    public void book_offer_price_is_calculated_from_sales_price() {
        given_a_book_with_sales_price(BigDecimal.valueOf(10.0));
        given_an_offer_to_buy_the_book();
        when_its_offer_price_is_calculated();
        then_the_calculated_offer_price_is(BigDecimal.valueOf(7.0));

        given_a_book_with_sales_price(BigDecimal.valueOf(15.0));
        given_an_offer_to_buy_the_book();
        when_its_offer_price_is_calculated();
        then_the_calculated_offer_price_is(BigDecimal.valueOf(10.5));
    }

    @Test
    public void book_offer_price_is_calculated_from_page_count() {
        given_a_book_with_page_count(300);
        given_an_offer_to_buy_the_book();
        when_its_offer_price_is_calculated();
        then_the_calculated_offer_price_is(BigDecimal.valueOf(7.0));

        given_a_book_with_page_count(450);
        given_an_offer_to_buy_the_book();
        when_its_offer_price_is_calculated();
        then_the_calculated_offer_price_is(BigDecimal.valueOf(10.5));
    }

    private void given_a_book_with_page_count(int pageCount) {
        book = new Book("A Book Title", null, "A Publisher", 2019, "ISBN1", pageCount, getGenre());
    }

    private void given_a_book_with_sales_price(BigDecimal salesPrice) {
        book = new Book("A Book Title", null, "A Publisher", 2019, "ISBN1", 300, getGenre(), salesPrice);
    }

    private void given_an_offer_to_buy_the_book() {
        offer = new Offer(book, "Science Fiction");
    }

    private void when_its_sales_price_is_calculated() {
        salesPrice = book.getPrice();
    }

    private void when_its_offer_price_is_calculated() {
        offerPrice = offer.getOfferPrice();
    }

    private void then_the_calculated_sales_price_is(BigDecimal expectedSalesPrice) {
        MatcherAssert.assertThat(salesPrice, CoreMatchers.is(expectedSalesPrice));
    }

    private void then_the_calculated_offer_price_is(BigDecimal expectedOfferPrice) {
        MatcherAssert.assertThat(offerPrice, CoreMatchers.is(expectedOfferPrice));
    }

    private Genre getGenre() {
        return new Genre("Science Fiction", 1.0);
    }

}
