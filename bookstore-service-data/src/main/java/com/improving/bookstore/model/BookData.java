package com.improving.bookstore.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Book")
public class BookData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @ManyToOne
    private AuthorData author;

    @Column
    private String publisher;

    @Column
    private int publishYear;

    @Column
    private String isbn;

    @Column
    private int numberOfPages;

    @Column
    private BigDecimal price;

    @ManyToOne
    private GenreData genre;

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

    public AuthorData getAuthor() {
        return author;
    }

    public void setAuthor(AuthorData author) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public GenreData getGenre() {
        return genre;
    }

    public void setGenre(GenreData genre) {
        this.genre = genre;
    }

}
