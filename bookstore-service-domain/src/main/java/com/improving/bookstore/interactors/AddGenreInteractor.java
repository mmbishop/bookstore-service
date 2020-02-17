package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;

public class AddGenreInteractor {

    private GenreRepository genreRepository;

    public AddGenreInteractor(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre addGenre(Genre genre) {
        return genreRepository.addGenre(genre);
    }

}
