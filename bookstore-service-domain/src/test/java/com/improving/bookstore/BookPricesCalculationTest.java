package com.improving.bookstore;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.BookPurchaseInvoice;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.functions.GwtFunctionWithArgument;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class BookPricesCalculationTest {

    private final GwtTest<PricesTestContext> gwt = new GwtTest<>(PricesTestContext.class);

    @Test
    public void book_sales_price_is_calculated_from_page_count() {
        gwt.test()
                .given(a_book_with_page_count, 300)
                .when(calculating_the_sales_price)
                .then(the_calculated_sales_price_is, "6.00");

        gwt.test()
                .given(a_book_with_page_count, 150)
                .when(calculating_the_sales_price)
                .then(the_calculated_sales_price_is, "3.00");
    }

    @Test
    public void book_offer_price_is_calculated_from_page_count() {
        gwt.test()
                .given(a_book_with_page_count, 300)
                .and(an_offer_to_buy_the_book)
                .when(calculating_the_offer_price)
                .then(the_calculated_offer_price_is, "4.20");

        gwt.test()
                .given(a_book_with_page_count, 450)
                .and(an_offer_to_buy_the_book)
                .when(calculating_the_offer_price)
                .then(the_calculated_offer_price_is, "6.30");
    }

    private final GwtFunctionWithArgument<PricesTestContext, Integer> a_book_with_page_count
            = (context, pageCount) -> context.book = new Book("A Book Title", null, "A Publisher",
            2019, "ISBN1", pageCount, getGenre());

    private final GwtFunction<PricesTestContext> an_offer_to_buy_the_book
            = context -> context.offer = new BookPurchaseInvoice(context.book);

    private final GwtFunction<PricesTestContext> calculating_the_sales_price = context -> context.salesPrice = context.book.getPrice();

    private final GwtFunction<PricesTestContext> calculating_the_offer_price = context -> context.offerPrice = context.offer.getPurchasePrice();

    private final GwtFunctionWithArgument<PricesTestContext, String> the_calculated_sales_price_is
            = (context, expectedSalesPrice) -> assertThat(context.salesPrice.toString(), is(expectedSalesPrice));

    private final GwtFunctionWithArgument<PricesTestContext, String> the_calculated_offer_price_is
            = (context, expectedOfferPrice) -> assertThat(context.offerPrice.toString(), is(expectedOfferPrice));

    private Genre getGenre() {
        return new Genre("Science Fiction", 1.0);
    }

    public static final class PricesTestContext extends Context {
        Book book;
        BigDecimal offerPrice;
        BigDecimal salesPrice;
        BookPurchaseInvoice offer;
    }

}
