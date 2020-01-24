package com.improving.bookstore.repositories;

import com.improving.bookstore.model.GenreData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenreDataSource extends CrudRepository<GenreData, Integer> {

    Iterable<GenreData> findAll();

    List<GenreData> findByName(String name);

    <S extends GenreData> S save(S entity);

}
