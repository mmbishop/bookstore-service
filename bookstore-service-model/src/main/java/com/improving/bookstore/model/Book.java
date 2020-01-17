package com.improving.bookstore.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class Book {

    private static final double PAGES_PER_DOLLAR_RATIO = 30.0;

    private String title;
    private Author author;
    private String publisher;
    private int publishYear;
    private String isbn;
    private int numberOfPages;
    private BigDecimal price;
    private Genre genre;

    public Book(String title, Author author, String publisher, int publishYear, String isbn, int numberOfPages, Genre genre) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.isbn = isbn;
        this.numberOfPages = numberOfPages;
        this.genre = genre;
        this.price = calculateSalesPrice();
    }

    public Book(String title, Author author, String publisher, int publishYear, String isbn, int numberOfPages, Genre genre, BigDecimal price) {
        this(title, author, publisher, publishYear, isbn, numberOfPages, genre);
        this.price = Objects.requireNonNullElseGet(price, this::calculateSalesPrice);
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public Genre getGenre() {
        return genre;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private BigDecimal calculateSalesPrice() {
        return BigDecimal.valueOf((getNumberOfPages() / PAGES_PER_DOLLAR_RATIO) * genre.getPricingFactor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

}
