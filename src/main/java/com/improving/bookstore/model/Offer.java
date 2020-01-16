package com.improving.bookstore.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Offer {

    private static final double DESIRED_PROFIT_MARGIN_RATIO = 0.3;

    private Book book;
    private String genreName;
    private BigDecimal offerPrice;

    public Offer(Book book, String genreName) {
        this.book = book;
        this.genreName = genreName;
        this.offerPrice = calculateOfferPrice();
    }

    public Offer(Book book, String genreName, BigDecimal offerPrice) {
        this(book, genreName);
        this.offerPrice = Objects.requireNonNullElseGet(offerPrice, this::calculateOfferPrice);
    }

    public Book getBook() {
        return book;
    }

    public String getGenreName() {
        return genreName;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    private BigDecimal calculateOfferPrice() {
        return BigDecimal.valueOf(book.getPrice().doubleValue() * (1.0 - DESIRED_PROFIT_MARGIN_RATIO));
    }

}
