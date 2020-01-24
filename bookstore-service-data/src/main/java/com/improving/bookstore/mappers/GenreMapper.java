package com.improving.bookstore.mappers;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.GenreData;

public class GenreMapper {

    public Genre mapFrom(GenreData genreData) {
        return new Genre(genreData.getName(), genreData.getPricingFactor());
    }

    public GenreData mapFrom(Genre genre) {
        GenreData genreData = new GenreData();
        genreData.setName(genre.getName());
        genreData.setPricingFactor(genre.getPricingFactor());
        return genreData;
    }

}
