package com.improving.bookstore.mappers;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.AuthorData;

public class AuthorMapper {

    public Author mapFrom(AuthorData authorData) {
        if (authorData == null) {
            return null;
        }
        return new Author(authorData.getId(), authorData.getFirstName(), authorData.getMiddleName(), authorData.getLastName());
    }

    public AuthorData mapFrom(Author author) {
        if (author == null) {
            return null;
        }
        AuthorData authorData = new AuthorData();
        authorData.setId(author.getId());
        authorData.setFirstName(author.getFirstName());
        authorData.setMiddleName(author.getMiddleName());
        authorData.setLastName(author.getLastName());
        return authorData;
    }

}
