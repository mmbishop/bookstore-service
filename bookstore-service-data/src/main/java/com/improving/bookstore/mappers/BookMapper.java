package com.improving.bookstore.mappers;

import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.BookData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    private AuthorMapper authorMapper;
    private GenreMapper genreMapper;

    @Autowired
    public BookMapper(AuthorMapper authorMapper, GenreMapper genreMapper) {
        this.authorMapper = authorMapper;
        this.genreMapper = genreMapper;
    }

    public Book mapFrom(BookData bookData) {
        if (bookData == null) {
            return null;
        }
        return new Book(bookData.getId(), bookData.getTitle(), authorMapper.mapFrom(bookData.getAuthor()), bookData.getPublisher(),
                bookData.getPublishYear(), bookData.getIsbn(), bookData.getNumberOfPages(), genreMapper.mapFrom(bookData.getGenre()),
                bookData.getPrice());
    }

    public BookData mapFrom(Book book) {
        if (book == null) {
            return null;
        }
        BookData bookData = new BookData();
        bookData.setId(book.getId());
        bookData.setTitle(book.getTitle());
        bookData.setAuthor(authorMapper.mapFrom(book.getAuthor()));
        bookData.setPublisher(book.getPublisher());
        bookData.setPublishYear(book.getPublishYear());
        bookData.setIsbn(book.getIsbn());
        bookData.setNumberOfPages(book.getNumberOfPages());
        bookData.setGenre(genreMapper.mapFrom(book.getGenre()));
        bookData.setPrice(book.getPrice());
        return bookData;
    }

}
