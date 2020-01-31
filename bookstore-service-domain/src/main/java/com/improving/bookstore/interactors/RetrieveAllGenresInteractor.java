package com.improving.bookstore.interactors;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;

import java.util.List;

public class RetrieveAllGenresInteractor implements EntityRetrievalInteractor<Genre> {

    private GenreRepository genreRepository;

    public RetrieveAllGenresInteractor(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> invoke() {
        return genreRepository.getAllGenres();
    }

}
