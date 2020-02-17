package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

public class DeleteGenreInteractor {

    private BookRepository bookRepository;
    private GenreRepository genreRepository;

    public DeleteGenreInteractor(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public void deleteGenre(String genreName) {
        Optional<Genre> genre = genreRepository.getGenreByName(genreName);
        if (genre.isPresent()) {
            List<Book> booksByGenre = bookRepository.getBooksByGenre(genreName);
            if (booksByGenre.isEmpty()) {
                genreRepository.deleteGenre(genre.get());
            }
            else {
                throw new GenreDeletionException(genreName);
            }
        }
    }

}
