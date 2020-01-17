package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;

import java.util.List;

public class RetrieveAllGenresUseCase {

    private GenreRepository genreRepository;

    public RetrieveAllGenresUseCase(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> invoke() {
        return genreRepository.getAllGenres();
    }

}
