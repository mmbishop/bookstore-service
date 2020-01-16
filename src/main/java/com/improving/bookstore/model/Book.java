package com.improving.bookstore.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Book {

    private String title;
    private Author author;
    private String publisher;
    private int publishYear;
    private String isbn;
    private int numberOfPages;
    private BigDecimal price;

    public Book(String title, Author author, String publisher, int publishYear, String isbn, int numberOfPages, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.isbn = isbn;
        this.numberOfPages = numberOfPages;
        this.price = price;
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

    public BigDecimal getPrice() {
        return price;
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
