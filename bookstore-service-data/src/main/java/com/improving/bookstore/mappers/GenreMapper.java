package com.improving.bookstore.mappers;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.GenreData;

public class GenreMapper {

    public Genre mapFrom(GenreData genreData) {
        if (genreData == null) {
            return null;
        }
        return new Genre(genreData.getId(), genreData.getName(), genreData.getPricingFactor());
    }

    public GenreData mapFrom(Genre genre) {
        if (genre == null) {
            return null;
        }
        GenreData genreData = new GenreData();
        genreData.setId(genre.getId());
        genreData.setName(genre.getName());
        genreData.setPricingFactor(genre.getPricingFactor());
        return genreData;
    }

}
