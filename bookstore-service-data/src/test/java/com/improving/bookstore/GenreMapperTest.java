package com.improving.bookstore;

import com.improving.bookstore.mappers.GenreMapper;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.model.GenreData;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class GenreMapperTest {

    private final GwtTest<GenreMapperTestContext> gwt = new GwtTest<>(GenreMapperTestContext.class);

    @Test
    void mapper_maps_genre_data_entity_to_domain_entity() {
        gwt.test()
                .given(a_genre_mapper)
                .and(a_genre_data_entity)
                .when(mapping_the_data_entity_to_a_domain_entity)
                .then(the_domain_entity_is_produced);
    }

    @Test
    void mapper_maps_genre_domain_entity_to_data_entity() {
        gwt.test()
                .given(a_genre_mapper)
                .and(a_genre_domain_entity)
                .when(mapping_the_domain_entity_to_a_data_entity)
                .then(the_data_entity_is_produced);
    }

    @Test
    void mapper_returns_null_when_data_entity_is_null() {
        gwt.test()
                .given(a_genre_mapper)
                .when(mapping_a_null_data_entity_to_a_domain_entity)
                .then(the_domain_entity_is_null);
    }

    @Test
    void mapper_returns_null_when_domain_entity_is_null() {
        gwt.test()
                .given(a_genre_mapper)
                .when(mapping_a_null_domain_entity_to_a_data_entity)
                .then(the_data_entity_is_null);
    }

    private final GwtFunction<GenreMapperTestContext> a_genre_mapper = context -> context.genreMapper = new GenreMapper();

    private final GwtFunction<GenreMapperTestContext> a_genre_data_entity = context -> {
        context.genreDataEntity = new GenreData();
        context.genreDataEntity.setName("Science Fiction");
        context.genreDataEntity.setPricingFactor(1.1);
    };

    private final GwtFunction<GenreMapperTestContext> a_genre_domain_entity
            = context -> context.genreDomainEntity = new Genre("Science Fiction", 1.1);

    private final GwtFunction<GenreMapperTestContext> mapping_the_data_entity_to_a_domain_entity
            = context -> context.genreDomainEntity = context.genreMapper.mapFrom(context.genreDataEntity);

    private final GwtFunction<GenreMapperTestContext> mapping_the_domain_entity_to_a_data_entity
            = context -> context.genreDataEntity = context.genreMapper.mapFrom(context.genreDomainEntity);

    private final GwtFunction<GenreMapperTestContext> mapping_a_null_data_entity_to_a_domain_entity
            = context -> context.genreDomainEntity = context.genreMapper.mapFrom((GenreData) null);

    private final GwtFunction<GenreMapperTestContext> mapping_a_null_domain_entity_to_a_data_entity
            = context -> context.genreDataEntity = context.genreMapper.mapFrom((Genre) null);

    private final GwtFunction<GenreMapperTestContext> the_domain_entity_is_produced = context -> {
        assertThat(context.genreDomainEntity.getName(), is("Science Fiction"));
        assertThat(context.genreDomainEntity.getPricingFactor(), is(1.1));
    };

    private final GwtFunction<GenreMapperTestContext> the_data_entity_is_produced = context -> {
        assertThat(context.genreDataEntity.getName(), is("Science Fiction"));
        assertThat(context.genreDataEntity.getPricingFactor(), is(1.1));
    };

    private final GwtFunction<GenreMapperTestContext> the_domain_entity_is_null
            = context -> assertThat(context.genreDomainEntity, is(nullValue()));

    private final GwtFunction<GenreMapperTestContext> the_data_entity_is_null
            = context -> assertThat(context.genreDataEntity, is(nullValue()));

    public static final class GenreMapperTestContext extends Context {
        Genre genreDomainEntity;
        GenreData genreDataEntity;
        GenreMapper genreMapper;
    }

}
