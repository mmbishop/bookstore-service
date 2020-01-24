package com.improving.bookstore.mappers;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.AuthorData;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author mapFrom(AuthorData authorData) {
        if (authorData == null) {
            return null;
        }
        return new Author(authorData.getFirstName(), authorData.getMiddleName(), authorData.getLastName());
    }

    public AuthorData mapFrom(Author author) {
        if (author == null) {
            return null;
        }
        AuthorData authorData = new AuthorData();
        authorData.setFirstName(author.getFirstName());
        authorData.setMiddleName(author.getMiddleName());
        authorData.setLastName(author.getLastName());
        return authorData;
    }

}
