package com.improving.bookstore.repositories;

import com.improving.bookstore.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Genre addGenre(Genre genre);

    void deleteGenre(Genre genre);

    Optional<Genre> getGenreByName(String genreName);

    List<Genre> getAllGenres();

}
