package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;

public class AddGenreUseCase {

    private GenreRepository genreRepository;

    public AddGenreUseCase(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void addGenre(Genre genre) {
        genreRepository.addGenre(genre);
    }

}
