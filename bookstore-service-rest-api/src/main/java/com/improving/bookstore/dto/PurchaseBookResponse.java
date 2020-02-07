package com.improving.bookstore.dto;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.BookPurchaseInvoice;

import java.math.BigDecimal;

public class PurchaseBookResponse {

    private String title;
    private String authorFirstName;
    private String authorMiddleName;
    private String authorLastName;
    private String publisher;
    private int publishYear;
    private String isbn;
    private int numberOfPages;
    private String genre;
    private BigDecimal purchasePrice;

    public PurchaseBookResponse(BookPurchaseInvoice invoice) {
        Book book = invoice.getBook();
        title = book.getTitle();
        Author author = book.getAuthor();
        authorFirstName = author.getFirstName();
        authorMiddleName = author.getMiddleName();
        authorLastName = author.getLastName();
        publisher = book.getPublisher();
        publishYear = book.getPublishYear();
        isbn = book.getIsbn();
        numberOfPages = book.getNumberOfPages();
        genre = book.getGenre().getName();
        purchasePrice = invoice.getPurchasePrice();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorMiddleName() {
        return authorMiddleName;
    }

    public String getAuthorLastName() {
        return authorLastName;
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

    public String getGenre() {
        return genre;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

}
