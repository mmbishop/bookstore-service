package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;

public class AddGenreUseCase {

    private Genre genre;
    private GenreRepository genreRepository;

    public AddGenreUseCase(Genre genre, GenreRepository genreRepository) {
        this.genre = genre;
        this.genreRepository = genreRepository;
    }

    public void invoke() {
        genreRepository.addGenre(genre);
    }

}
