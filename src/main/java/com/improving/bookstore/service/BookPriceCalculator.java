package com.improving.bookstore.service;

import com.improving.bookstore.model.Book;

import java.math.BigDecimal;

public class BookPriceCalculator {

    private static final double DESIRED_PROFIT_MARGIN_RATIO = 0.3;
    private static final double PAGES_PER_DOLLAR_RATIO = 30.0;

    public BigDecimal calculateSalesPriceFor(Book book) {
        return BigDecimal.valueOf(book.getNumberOfPages() / PAGES_PER_DOLLAR_RATIO);
    }

    public BigDecimal calculateOfferPriceFor(Book book) {
        BigDecimal salesPrice = book.getPrice();
        if (salesPrice == null) {
            salesPrice = calculateSalesPriceFor(book);
        }
        return BigDecimal.valueOf(salesPrice.doubleValue() * (1.0 - DESIRED_PROFIT_MARGIN_RATIO));
    }

}
