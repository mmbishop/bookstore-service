package com.improving.bookstore;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.service.BookPriceCalculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class BookPricesCalculationTest {

    private Book book;
    private BigDecimal offerPrice;
    private BigDecimal salesPrice;

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
        when_its_offer_price_is_calculated();
        then_the_calculated_offer_price_is(BigDecimal.valueOf(7.0));

        given_a_book_with_sales_price(BigDecimal.valueOf(15.0));
        when_its_offer_price_is_calculated();
        then_the_calculated_offer_price_is(BigDecimal.valueOf(10.5));
    }

    @Test
    public void book_offer_price_is_calculated_from_page_count() {
        given_a_book_with_page_count(300);
        when_its_offer_price_is_calculated();
        then_the_calculated_offer_price_is(BigDecimal.valueOf(7.0));

        given_a_book_with_page_count(450);
        when_its_offer_price_is_calculated();
        then_the_calculated_offer_price_is(BigDecimal.valueOf(10.5));
    }

    private void given_a_book_with_page_count(int pageCount) {
        book = new Book("A Book Title", null, "A Publisher", 2019, "ISBN1", pageCount, null);
    }

    private void given_a_book_with_sales_price(BigDecimal salesPrice) {
        book = new Book("A Book Title", null, "A Publisher", 2019, "ISBN1", 300, salesPrice);
    }

    private void when_its_sales_price_is_calculated() {
        salesPrice = new BookPriceCalculator().calculateSalesPriceFor(book);
    }

    private void when_its_offer_price_is_calculated() {
        offerPrice = new BookPriceCalculator().calculateOfferPriceFor(book);
    }

    private void then_the_calculated_sales_price_is(BigDecimal expectedSalesPrice) {
        assertThat(salesPrice, is(expectedSalesPrice));
    }

    private void then_the_calculated_offer_price_is(BigDecimal expectedOfferPrice) {
        assertThat(offerPrice, is(expectedOfferPrice));
    }

}
