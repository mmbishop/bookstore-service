package com.improving.bookstore.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Offer {

    private Book book;
    private String genreName;
    private BigDecimal offerPrice;

    public Offer(Book book, String genreName, BigDecimal offerPrice) {
        this.book = book;
        this.genreName = genreName;
        this.offerPrice = offerPrice;
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

}
