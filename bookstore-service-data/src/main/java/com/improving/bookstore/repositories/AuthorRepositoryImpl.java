package com.improving.bookstore.repositories;

import com.improving.bookstore.mappers.AuthorMapper;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.AuthorData;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AuthorRepositoryImpl implements AuthorRepository {

    private AuthorDataSource dataSource;
    private AuthorMapper authorMapper;

    public AuthorRepositoryImpl(AuthorDataSource dataSource, AuthorMapper authorMapper) {
        this.dataSource = dataSource;
        this.authorMapper = authorMapper;
    }

    @Override
    public Author addAuthor(Author author) {
        return authorMapper.mapFrom(dataSource.save(authorMapper.mapFrom(author)));
    }

    @Override
    public Optional<Author> getAuthorByExample(Author author) {
        List<AuthorData> authorDataList = dataSource.findByFirstNameAndMiddleNameAndLastName
                (author.getFirstName(), author.getMiddleName(), author.getLastName());
        if (authorDataList.isEmpty()) {
            return Optional.empty();
        }
        else {
            return Optional.of(authorMapper.mapFrom(authorDataList.get(0)));
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authorList = new LinkedList<>();
        dataSource.findAll().forEach(author -> authorList.add(authorMapper.mapFrom(author)));
        return authorList;
    }

}
