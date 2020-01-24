package com.improving.bookstore.mappers;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.AuthorData;

public class AuthorMapper {

    public Author mapFrom(AuthorData authorData) {
        return new Author(authorData.getFirstName(), authorData.getMiddleName(), authorData.getLastName());
    }

    public AuthorData mapFrom(Author author) {
        AuthorData authorData = new AuthorData();
        authorData.setFirstName(author.getFirstName());
        authorData.setMiddleName(author.getMiddleName());
        authorData.setLastName(author.getLastName());
        return authorData;
    }

}
