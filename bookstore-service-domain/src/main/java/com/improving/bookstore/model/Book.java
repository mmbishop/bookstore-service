package com.improving.bookstore.model;

import com.improving.bookstore.interactors.UnspecifiedSalesPriceException;
import com.improving.bookstore.util.PriceNormalizer;

import java.math.BigDecimal;
import java.util.Objects;

public final class Book {

    private static final double PAGES_PER_DOLLAR_RATIO = 50.0;

    private int id;
    private String title;
    private Author author;
    private String publisher;
    private int publishYear;
    private String isbn;
    private int numberOfPages;
    private BigDecimal price;
    private Genre genre;

    public Book() {

    }

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

    public Book(int id, String title, Author author, String publisher, int publishYear, String isbn, int numberOfPages, Genre genre) {
        this(title, author, publisher, publishYear, isbn, numberOfPages, genre);
        this.id = id;
    }

    public Book(int id, String title, Author author, String publisher, int publishYear, String isbn, int numberOfPages, Genre genre, BigDecimal price) {
        this(id, title, author, publisher, publishYear, isbn, numberOfPages, genre);
        setPrice(price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public BigDecimal getPrice() {
        if (price == null) {
            price = calculateSalesPrice();
        }
        return price;
    }

    public void setPrice(BigDecimal newPrice) {
        if (newPrice != null) {
            price = PriceNormalizer.normalizePrice(newPrice);
        }
        else {
            throw new UnspecifiedSalesPriceException();
        }
    }

    public void resetPrice() {
        price = calculateSalesPrice();
    }

    private BigDecimal calculateSalesPrice() {
        return PriceNormalizer.normalizePrice(BigDecimal.valueOf((getNumberOfPages() / PAGES_PER_DOLLAR_RATIO) * genre.getPricingFactor()));
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
