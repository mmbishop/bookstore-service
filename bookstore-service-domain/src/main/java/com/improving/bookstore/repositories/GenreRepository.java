package com.improving.bookstore.repositories;

import com.improving.bookstore.model.Genre;

import java.util.List;

public interface GenreRepository {

    void addGenre(Genre genre);

    void deleteGenre(Genre genre);

    List<Genre> getAllGenres();

}
