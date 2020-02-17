package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;

import java.util.Optional;

public class DeleteGenreInteractor {

    private GenreRepository genreRepository;

    public DeleteGenreInteractor(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void deleteGenre(String genreName) {
        Optional<Genre> genre = genreRepository.getGenreByName(genreName);
        genre.ifPresent(value -> genreRepository.deleteGenre(value));
    }

}
