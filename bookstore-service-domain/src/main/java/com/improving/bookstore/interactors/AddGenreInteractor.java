package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;

public class AddGenreInteractor {

    private GenreRepository genreRepository;

    public AddGenreInteractor(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void addGenre(Genre genre) {
        genreRepository.addGenre(genre);
    }

}
