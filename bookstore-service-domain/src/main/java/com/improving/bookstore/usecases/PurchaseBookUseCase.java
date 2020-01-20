package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.Offer;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.repositories.GenreRepository;

import java.util.Optional;

public class PurchaseBookUseCase {

    private Book book;
    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private String genreName;

    public PurchaseBookUseCase(Book book, String genreName, BookRepository bookRepository, GenreRepository genreRepository) {
        this.book = book;
        this.genreName = genreName;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    public Offer invoke() {
        Optional<Genre> genre = genreRepository.getGenreByName(genreName);
        if (genre.isPresent()) {
            book.setGenre(genre.get());
            bookRepository.addBook(book);
            return new Offer(book);
        }
        throw new UnwantedGenreException("Bookstore does not want books of genre " + genreName);
    }

}
