package com.improving.bookstore.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class BookPurchaseInvoice {

    private static final double DESIRED_PROFIT_MARGIN_RATIO = 0.3;

    private Book book;
    private BigDecimal purchasePrice;

    public BookPurchaseInvoice(Book book) {
        this.book = book;
        this.purchasePrice = calculatePurchasePrice();
    }

    public BookPurchaseInvoice(Book book, BigDecimal purchasePrice) {
        this(book);
        this.purchasePrice = Objects.requireNonNullElseGet(purchasePrice, this::calculatePurchasePrice);
    }

    public Book getBook() {
        return book;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    private BigDecimal calculatePurchasePrice() {
        return BigDecimal.valueOf(book.getPrice().doubleValue() * (1.0 - DESIRED_PROFIT_MARGIN_RATIO));
    }

}