package com.improving.bookstore.repositories;

import com.improving.bookstore.model.GenreData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreDataSource extends CrudRepository<GenreData, Integer> {

    Iterable<GenreData> findAll();

    List<GenreData> findByName(String name);

    <S extends GenreData> S save(S entity);

}
