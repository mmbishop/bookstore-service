package com.improving.bookstore;

import com.improving.bookstore.mappers.GenreMapper;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.GenreData;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GenreMapperTest {

    private Genre genreDomainEntity;
    private GenreData genreDataEntity;
    private GenreMapper genreMapper;

    @Test
    void mapper_maps_genre_data_entity_to_domain_entity() {
        given_a_genre_mapper();
        given_a_genre_data_entity();
        when_the_data_entity_is_mapped_to_a_domain_entity();
        then_the_domain_entity_is_produced();
    }

    @Test
    void mapper_maps_genre_domain_entity_to_data_entity() {
        given_a_genre_mapper();
        given_a_genre_domain_entity();
        when_the_domain_entity_is_mapped_to_a_data_entity();
        then_the_data_entity_is_produced();
    }

    @Test
    void mapper_returns_null_when_data_entity_is_null() {
        given_a_genre_mapper();
        when_a_null_data_entity_is_mapped_to_a_domain_entity();
        then_the_domain_entity_is_null();
    }

    @Test
    void mapper_returns_null_when_domain_entity_is_null() {
        given_a_genre_mapper();
        when_a_null_domain_entity_is_mapped_to_a_data_entity();
        then_the_data_entity_is_null();
    }

    private void given_a_genre_mapper() {
        genreMapper = new GenreMapper();
    }

    private void given_a_genre_data_entity() {
        genreDataEntity = new GenreData();
        genreDataEntity.setName("Science Fiction");
        genreDataEntity.setPricingFactor(1.1);
    }

    private void given_a_genre_domain_entity() {
        genreDomainEntity = new Genre("Science Fiction", 1.1);
    }

    private void when_the_data_entity_is_mapped_to_a_domain_entity() {
        genreDomainEntity = genreMapper.mapFrom(genreDataEntity);
    }

    private void when_the_domain_entity_is_mapped_to_a_data_entity() {
        genreDataEntity = genreMapper.mapFrom(genreDomainEntity);
    }

    private void when_a_null_data_entity_is_mapped_to_a_domain_entity() {
        genreDomainEntity = genreMapper.mapFrom((GenreData) null);
    }

    private void when_a_null_domain_entity_is_mapped_to_a_data_entity() {
        genreDataEntity = genreMapper.mapFrom((Genre) null);
    }

    private void then_the_domain_entity_is_produced() {
        assertThat(genreDomainEntity.getName(), is("Science Fiction"));
        assertThat(genreDomainEntity.getPricingFactor(), is(1.1));
    }

    private void then_the_data_entity_is_produced() {
        assertThat(genreDataEntity.getName(), is("Science Fiction"));
        assertThat(genreDataEntity.getPricingFactor(), is(1.1));
    }

    private void then_the_domain_entity_is_null() {
        assertThat(genreDomainEntity, is(nullValue()));
    }

    private void then_the_data_entity_is_null() {
        assertThat(genreDataEntity, is(nullValue()));
    }

}
