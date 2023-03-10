package com.improving.bookstore;

import com.improving.bookstore.mappers.AuthorMapper;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.AuthorData;
import com.improving.bookstore.model.BookData;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AuthorMapperTest {

    private final GwtTest<AuthorMapperTestContext> gwt = new GwtTest<>(AuthorMapperTestContext.class);

    @Test
    void mapper_maps_author_data_entity_to_domain_entity() {
        gwt.test()
                .given(an_author_mapper)
                .and(an_author_data_entity)
                .when(mapping_the_data_entity_to_a_domain_entity)
                .then(a_domain_entity_is_produced);
    }

    @Test
    void mapper_maps_author_domain_entity_to_data_entity() {
        gwt.test()
                .given(an_author_mapper)
                .and(an_author_domain_entity)
                .when(mapping_the_domain_entity_to_a_data_entity)
                .then(a_data_entity_is_produced);
    }

    @Test
    void mapper_returns_null_when_data_entity_is_null() {
        gwt.test()
                .given(an_author_mapper)
                .when(mapping_a_null_data_entity_to_a_domain_entity)
                .then(the_domain_entity_is_null);
    }

    @Test
    void mapper_returns_null_when_domain_entity_is_null() {
        gwt.test()
                .given(an_author_mapper)
                .when(mapping_a_null_domain_entity_to_a_data_entity)
                .then(the_data_entity_is_null);
    }

    private final GwtFunction<AuthorMapperTestContext> an_author_mapper = context -> context.authorMapper = new AuthorMapper();

    private final GwtFunction<AuthorMapperTestContext> an_author_data_entity = context -> {
        context.authorDataEntity = new AuthorData();
        context.authorDataEntity.setId(1);
        context.authorDataEntity.setFirstName("Edgar");
        context.authorDataEntity.setMiddleName("Allan");
        context.authorDataEntity.setLastName("Poe");
    };

    private final GwtFunction<AuthorMapperTestContext> an_author_domain_entity
            = context -> context.authorDomainEntity = new Author("Edgar", "Allan", "Poe");

    private final GwtFunction<AuthorMapperTestContext> mapping_the_data_entity_to_a_domain_entity
            = context -> context.authorDomainEntity = context.authorMapper.mapFrom(context.authorDataEntity);

    private final GwtFunction<AuthorMapperTestContext> mapping_the_domain_entity_to_a_data_entity
            = context -> context.authorDataEntity = context.authorMapper.mapFrom(context.authorDomainEntity);

    private final GwtFunction<AuthorMapperTestContext> mapping_a_null_data_entity_to_a_domain_entity
            = context -> context.authorDomainEntity = context.authorMapper.mapFrom((AuthorData) null);

    private final GwtFunction<AuthorMapperTestContext> mapping_a_null_domain_entity_to_a_data_entity
            = context -> context.authorDataEntity = context.authorMapper.mapFrom((Author) null);

    private final GwtFunction<AuthorMapperTestContext> a_domain_entity_is_produced = context -> {
        assertThat(context.authorDomainEntity.getFirstName(), is("Edgar"));
        assertThat(context.authorDomainEntity.getMiddleName(), is("Allan"));
        assertThat(context.authorDomainEntity.getLastName(), is("Poe"));
    };

    private final GwtFunction<AuthorMapperTestContext> a_data_entity_is_produced = context -> {
        assertThat(context.authorDataEntity.getFirstName(), is("Edgar"));
        assertThat(context.authorDataEntity.getMiddleName(), is("Allan"));
        assertThat(context.authorDataEntity.getLastName(), is("Poe"));
    };

    private final GwtFunction<AuthorMapperTestContext> the_domain_entity_is_null
            = context -> assertThat(context.authorDomainEntity, is(nullValue()));

    private final GwtFunction<AuthorMapperTestContext> the_data_entity_is_null
            = context -> assertThat(context.authorDataEntity, is(nullValue()));

    public static final class AuthorMapperTestContext extends Context {
        Author authorDomainEntity;
        AuthorData authorDataEntity;
        AuthorMapper authorMapper;
    }

}
