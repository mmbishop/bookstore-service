package com.improving.bookstore.repositories;

import com.improving.bookstore.model.AuthorData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorDataSource extends CrudRepository<AuthorData, Integer> {

    Iterable<AuthorData> findAll();

    List<AuthorData> findByFirstNameAndMiddleNameAndLastName(String firstName, String middleName, String lastName);

    <S extends AuthorData> S save(S entity);

}
