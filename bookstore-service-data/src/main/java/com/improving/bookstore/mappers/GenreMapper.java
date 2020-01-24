package com.improving.bookstore.mappers;

import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.GenreData;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public Genre mapFrom(GenreData genreData) {
        if (genreData == null) {
            return null;
        }
        return new Genre(genreData.getName(), genreData.getPricingFactor());
    }

    public GenreData mapFrom(Genre genre) {
        if (genre == null) {
            return null;
        }
        GenreData genreData = new GenreData();
        genreData.setName(genre.getName());
        genreData.setPricingFactor(genre.getPricingFactor());
        return genreData;
    }

}
