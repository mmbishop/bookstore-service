package com.improving.bookstore.repositories;

import com.improving.bookstore.mappers.GenreMapper;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.GenreData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryImpl implements GenreRepository {

    private GenreDataSource dataSource;
    private GenreMapper genreMapper;

    @Autowired
    public GenreRepositoryImpl(GenreDataSource dataSource, GenreMapper genreMapper) {
        this.dataSource = dataSource;
        this.genreMapper = genreMapper;
    }

    @Override
    public void addGenre(Genre genre) {
        dataSource.save(genreMapper.mapFrom(genre));
    }

    @Override
    public Optional<Genre> getGenreByName(String genreName) {
        List<GenreData> genreDataList = dataSource.findByName(genreName);
        if (genreDataList.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(genreMapper.mapFrom(genreDataList.get(0)));
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genreList = new LinkedList<>();
        dataSource.findAll().forEach(genre -> {
            genreList.add(genreMapper.mapFrom(genre));
        });
        return genreList;
    }

}
