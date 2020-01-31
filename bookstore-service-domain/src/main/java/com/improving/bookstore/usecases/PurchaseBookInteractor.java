package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.BookPurchaseInvoice;
import com.improving.bookstore.repositories.AuthorRepository;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.repositories.GenreRepository;

import java.util.Optional;

public class PurchaseBookInteractor {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private GenreRepository genreRepository;

    public PurchaseBookInteractor(BookRepository bookRepository, AuthorRepository authorRepository,
                                  GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    public BookPurchaseInvoice purchaseBook(Book book, String genreName) {
        Optional<Genre> genre = genreRepository.getGenreByName(genreName);
        if (genre.isPresent()) {
            book.setGenre(genre.get());
            addAuthorIfNotAlreadyInTheRepository(book.getAuthor());
            bookRepository.addBook(book);
            return new BookPurchaseInvoice(book);
        }
        throw new UnwantedGenreException("Bookstore does not want books of genre " + genreName);
    }

    private void addAuthorIfNotAlreadyInTheRepository(Author bookAuthor) {
        Optional<Author> author = authorRepository.getAuthorByExample(bookAuthor);
        if (author.isEmpty()) {
            authorRepository.addAuthor(bookAuthor);
        }
    }

}
