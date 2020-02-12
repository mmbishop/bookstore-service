package com.improving.bookstore.services;

import com.improving.bookstore.interactors.*;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.BookPurchaseInvoice;
import com.improving.bookstore.model.Genre;

import java.math.BigDecimal;
import java.util.List;

public class BookstoreService {

    private AddGenreInteractor addGenreInteractor;
    private BookSaleInteractor bookSaleInteractor;
    private PurchaseBookInteractor purchaseBookInteractor;
    private RetrieveAllAuthorsInteractor retrieveAllAuthorsInteractor;
    private RetrieveAllBooksInteractor retrieveAllBooksInteractor;
    private RetrieveAllGenresInteractor retrieveAllGenresInteractor;
    private RetrieveBooksByAuthorInteractor retrieveBooksByAuthorInteractor;
    private RetrieveBooksByGenreInteractor retrieveBooksByGenreInteractor;
    private RetrieveBooksByTitleInteractor retrieveBooksByTitleInteractor;
    private SellBookInteractor sellBookInteractor;

    public List<Book> getAllBooks() {
        return retrieveAllBooksInteractor.retrieveAllBooks();
    }

    public List<Book> getBooksByAuthor(String firstName, String middleName, String lastName) {
        return retrieveBooksByAuthorInteractor.retrieveBooksByAuthor(new Author(firstName, middleName, lastName));
    }

    public List<Book> getBooksByGenre(String genreName) {
        return retrieveBooksByGenreInteractor.retrieveBooksByGenre(genreName);
    }

    public List<Book> getBooksByTitle(String title) {
        return retrieveBooksByTitleInteractor.retrieveBooksByTitle(title);
    }

    public List<Author> getAllAuthors() {
        return retrieveAllAuthorsInteractor.retrieveAllAuthors();
    }

    public BookPurchaseInvoice purchaseBook(Book book, Author author, String genreName) {
        return purchaseBookInteractor.purchaseBook(book, author, genreName);
    }

    public void putBookOnSale(int bookId, BigDecimal newSalesPrice) {
        bookSaleInteractor.putBookOnSale(bookId, newSalesPrice);
    }

    public void takeBookOffSale(int bookId) {
        bookSaleInteractor.takeBookOffSale(bookId);
    }

    public Book sellBook(int bookId) {
        return sellBookInteractor.sellBook(bookId);
    }

    public List<Genre> getAllGenres() {
        return retrieveAllGenresInteractor.retrieveAllGenres();
    }

    public void addGenre(Genre genre) {
        addGenreInteractor.addGenre(genre);
    }

    public void setRetrieveAllAuthorsInteractor(RetrieveAllAuthorsInteractor retrieveAllAuthorsInteractor) {
        this.retrieveAllAuthorsInteractor = retrieveAllAuthorsInteractor;
    }

    public void setRetrieveAllBooksInteractor(RetrieveAllBooksInteractor retrieveAllBooksInteractor) {
        this.retrieveAllBooksInteractor = retrieveAllBooksInteractor;
    }

    public void setRetrieveBooksByAuthorInteractor(RetrieveBooksByAuthorInteractor retrieveBooksByAuthorInteractor) {
        this.retrieveBooksByAuthorInteractor = retrieveBooksByAuthorInteractor;
    }

    public void setRetrieveBooksByGenreInteractor(RetrieveBooksByGenreInteractor retrieveBooksByGenreInteractor) {
        this.retrieveBooksByGenreInteractor = retrieveBooksByGenreInteractor;
    }

    public void setRetrieveBooksByTitleInteractor(RetrieveBooksByTitleInteractor retrieveBooksByTitleInteractor) {
        this.retrieveBooksByTitleInteractor = retrieveBooksByTitleInteractor;
    }

    public void setPurchaseBookInteractor(PurchaseBookInteractor purchaseBookInteractor) {
        this.purchaseBookInteractor = purchaseBookInteractor;
    }

    public void setBookSaleInteractor(BookSaleInteractor bookSaleInteractor) {
        this.bookSaleInteractor = bookSaleInteractor;
    }

    public void setSellBookInteractor(SellBookInteractor sellBookInteractor) {
        this.sellBookInteractor = sellBookInteractor;
    }

    public void setRetrieveAllGenresInteractor(RetrieveAllGenresInteractor retrieveAllGenresInteractor) {
        this.retrieveAllGenresInteractor = retrieveAllGenresInteractor;
    }

    public void setAddGenreInteractor(AddGenreInteractor addGenreInteractor) {
        this.addGenreInteractor = addGenreInteractor;
    }

}
