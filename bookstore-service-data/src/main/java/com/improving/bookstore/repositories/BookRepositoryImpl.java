package com.improving.bookstore.repositories;

import com.improving.bookstore.mappers.AuthorMapper;
import com.improving.bookstore.mappers.BookMapper;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository {

    private AuthorMapper authorMapper;
    private BookDataSource dataSource;
    private BookMapper bookMapper;

    public BookRepositoryImpl(BookDataSource dataSource, BookMapper bookMapper, AuthorMapper authorMapper) {
        this.dataSource = dataSource;
        this.bookMapper = bookMapper;
        this.authorMapper = authorMapper;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> bookList = new LinkedList<>();
        dataSource.findAll().forEach(book -> bookList.add(bookMapper.mapFrom(book)));
        return bookList;
    }

    @Override
    public Optional<Book> getBookById(int bookId) {
        return Optional.ofNullable(bookMapper.mapFrom(dataSource.findById(bookId)));
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        List<Book> bookList = new LinkedList<>();
        dataSource.findByTitle(title).forEach(book -> bookList.add(bookMapper.mapFrom(book)));
        return bookList;
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        List<Book> bookList = new LinkedList<>();
        dataSource.findByAuthor(authorMapper.mapFrom(author)).forEach(book -> bookList.add(bookMapper.mapFrom(book)));
        return bookList;
    }

    @Override
    public List<Book> getBooksByGenre(String genreName) {
        List<Book> bookList = new LinkedList<>();
        dataSource.findByGenreName(genreName).forEach(book -> bookList.add(bookMapper.mapFrom(book)));
        return bookList;
    }

    @Override
    public void addBook(Book book) {
        dataSource.save(bookMapper.mapFrom(book));
    }

    @Override
    public void deleteBook(Book book) {
        dataSource.delete(bookMapper.mapFrom(book));
    }

    @Override
    public void saveBook(Book book) {
        dataSource.save(bookMapper.mapFrom(book));
    }

}
