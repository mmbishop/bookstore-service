package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;

import java.util.List;

public class BookTestSupport {

    public List<Book> getBookList() {
        return List.of(
                getRedMarsBook(),
                createBook("Foundation", new Author("Isaac", null, "Asimov"), "A Publisher", 1972,
                        "Another ISBN", 400, new Genre("Science Fiction", 1.1))
        );
    }

    public Book getRedMarsBook() {
        return createBook("Red Mars", new Author("Kim", "Stanley", "Robinson"), "A Publisher", 1995,
                "An ISBN", 450, new Genre("Science Fiction", 1.1));
    }

    public Author getAuthorKimStanleyRobinson() {
        return new Author("Kim", "Stanley", "Robinson");
    }

    public Book createBook(String title, Author author, String publisher, int publishYear, String isbn, int numberOfPages, Genre genre) {
        return new Book(title, author, publisher, publishYear, isbn, numberOfPages, genre);
    }

}
