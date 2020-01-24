package com.improving.bookstore.mappers;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.AuthorData;

public class AuthorMapper {

    public Author mapFromData(AuthorData authorData) {
        return new Author(authorData.getFirstName(), authorData.getMiddleName(), authorData.getLastName());
    }

    public AuthorData mapFromEntity(Author author) {
        return new AuthorData(author.getFirstName(), author.getMiddleName(), author.getLastName());
    }

}
