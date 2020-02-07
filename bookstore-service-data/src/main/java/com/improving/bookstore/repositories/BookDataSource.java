package com.improving.bookstore.repositories;

import com.improving.bookstore.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDataSource extends CrudRepository<BookData, Integer> {

    Iterable<BookData> findAll();

    BookData findById(int id);

    List<BookData> findByTitle(String title);

    @Query("select b from BookData b join AuthorData a on a.id = b.author.id where a.firstName = :#{#author.firstName} and " +
            "(a.middleName is null or a.middleName = :#{#author.middleName}) and a.lastName = :#{#author.lastName}")
    List<BookData> findByAuthor(AuthorData author);

    List<BookData> findByGenreName(String genreName);

    <S extends BookData> S save(S book);

    void delete(BookData book);

}
