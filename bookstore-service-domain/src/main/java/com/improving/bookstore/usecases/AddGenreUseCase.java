package com.improving.bookstore.usecases;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AddGenreUseCase {

    private GenreRepository genreRepository;

    @Autowired
    public AddGenreUseCase(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void addGenre(Genre genre) {
        genreRepository.addGenre(genre);
    }

}
