package com.improving.bookstore.model;

import com.improving.bookstore.util.PriceNormalizer;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class BookPurchaseInvoice {

    private static final double DESIRED_PROFIT_MARGIN_RATIO = 0.3;

    private Book book;
    private BigDecimal purchasePrice;

    public BookPurchaseInvoice(Book book) {
        this.book = book;
        this.purchasePrice = calculatePurchasePrice();
    }

    public Book getBook() {
        return book;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    private BigDecimal calculatePurchasePrice() {
        return PriceNormalizer.normalizePrice(BigDecimal.valueOf(book.getPrice().doubleValue() * (1.0 - DESIRED_PROFIT_MARGIN_RATIO)));
    }

}
