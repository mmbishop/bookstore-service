package com.improving.bookstore.services;

import com.improving.bookstore.interactors.*;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.BookPurchaseInvoice;
import com.improving.bookstore.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookstoreService {

    private AddGenreInteractor addGenreInteractor;
    private BookSaleInteractor bookSaleInteractor;
    private DeleteGenreInteractor deleteGenreInteractor;
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

    public Genre addGenre(Genre genre) {
        return addGenreInteractor.addGenre(genre);
    }

    public void deleteGenre(String genreName) {
        deleteGenreInteractor.deleteGenre(genreName);
    }

    @Autowired
    public void setRetrieveAllAuthorsInteractor(RetrieveAllAuthorsInteractor retrieveAllAuthorsInteractor) {
        this.retrieveAllAuthorsInteractor = retrieveAllAuthorsInteractor;
    }

    @Autowired
    public void setRetrieveAllBooksInteractor(RetrieveAllBooksInteractor retrieveAllBooksInteractor) {
        this.retrieveAllBooksInteractor = retrieveAllBooksInteractor;
    }

    @Autowired
    public void setRetrieveBooksByAuthorInteractor(RetrieveBooksByAuthorInteractor retrieveBooksByAuthorInteractor) {
        this.retrieveBooksByAuthorInteractor = retrieveBooksByAuthorInteractor;
    }

    @Autowired
    public void setRetrieveBooksByGenreInteractor(RetrieveBooksByGenreInteractor retrieveBooksByGenreInteractor) {
        this.retrieveBooksByGenreInteractor = retrieveBooksByGenreInteractor;
    }

    @Autowired
    public void setRetrieveBooksByTitleInteractor(RetrieveBooksByTitleInteractor retrieveBooksByTitleInteractor) {
        this.retrieveBooksByTitleInteractor = retrieveBooksByTitleInteractor;
    }

    @Autowired
    public void setPurchaseBookInteractor(PurchaseBookInteractor purchaseBookInteractor) {
        this.purchaseBookInteractor = purchaseBookInteractor;
    }

    @Autowired
    public void setBookSaleInteractor(BookSaleInteractor bookSaleInteractor) {
        this.bookSaleInteractor = bookSaleInteractor;
    }

    @Autowired
    public void setSellBookInteractor(SellBookInteractor sellBookInteractor) {
        this.sellBookInteractor = sellBookInteractor;
    }

    @Autowired
    public void setRetrieveAllGenresInteractor(RetrieveAllGenresInteractor retrieveAllGenresInteractor) {
        this.retrieveAllGenresInteractor = retrieveAllGenresInteractor;
    }

    @Autowired
    public void setAddGenreInteractor(AddGenreInteractor addGenreInteractor) {
        this.addGenreInteractor = addGenreInteractor;
    }

    @Autowired
    public void setDeleteGenreInteractor(DeleteGenreInteractor deleteGenreInteractor) {
        this.deleteGenreInteractor = deleteGenreInteractor;
    }

}
