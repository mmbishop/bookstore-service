package com.improving.bookstore;

import com.improving.bookstore.mappers.AuthorMapper;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.AuthorData;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class AuthorMapperTest {

    private Author authorDomainEntity;
    private AuthorData authorDataEntity;
    private AuthorMapper authorMapper;

    @Test
    void mapper_maps_author_data_entity_to_domain_entity() {
        given_an_author_mapper();
        given_an_author_data_entity();
        when_the_data_entity_is_mapped_to_a_domain_entity();
        then_a_domain_entity_is_produced();
    }

    @Test
    void mapper_maps_author_domain_entity_to_data_entity() {
        given_an_author_mapper();
        given_an_author_domain_entity();
        when_the_domain_entity_is_mapped_to_a_data_entity();
        then_a_data_entity_is_produced();
    }

    private void given_an_author_mapper() {
        authorMapper = new AuthorMapper();
    }

    private void given_an_author_data_entity() {
        authorDataEntity = new AuthorData();
        authorDataEntity.setId(1);
        authorDataEntity.setFirstName("Edgar");
        authorDataEntity.setMiddleName("Allan");
        authorDataEntity.setLastName("Poe");
    }

    private void given_an_author_domain_entity() {
        authorDomainEntity = new Author("Edgar", "Allan", "Poe");
    }

    private void when_the_data_entity_is_mapped_to_a_domain_entity() {
        authorDomainEntity = authorMapper.mapFrom(authorDataEntity);
    }

    private void when_the_domain_entity_is_mapped_to_a_data_entity() {
        authorDataEntity = authorMapper.mapFrom(authorDomainEntity);
    }

    private void then_a_domain_entity_is_produced() {
        assertThat(authorDomainEntity.getFirstName(), is("Edgar"));
        assertThat(authorDomainEntity.getMiddleName(), is("Allan"));
        assertThat(authorDomainEntity.getLastName(), is("Poe"));
    }

    private void then_a_data_entity_is_produced() {
        assertThat(authorDataEntity.getFirstName(), is("Edgar"));
        assertThat(authorDataEntity.getMiddleName(), is("Allan"));
        assertThat(authorDataEntity.getLastName(), is("Poe"));
    }

}
