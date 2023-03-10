package com.improving.bookstore;

import com.improving.bookstore.mappers.AuthorMapper;
import com.improving.bookstore.mappers.BookMapper;
import com.improving.bookstore.model.*;
import com.improving.bookstore.repositories.BookDataSource;
import com.improving.bookstore.repositories.BookRepositoryImpl;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookRepositoryImplTest {

    private GwtTest<BookRepositoryTestContext> gwt = new GwtTest<>(BookRepositoryTestContext.class);

    @Test
    void repository_returns_all_books() {
        gwt.test()
                .given(a_book_data_source)
                .and(mappers)
                .and(a_book_repository)
                .when(requesting_all_books_from_the_repository)
                .then(all_books_are_returned);
    }

    @Test
    void repository_finds_a_book_by_id() {
        gwt.test()
                .given(a_book_data_source)
                .and(mappers)
                .and(a_book_repository)
                .when(requesting_a_book_by_id)
                .then(that_book_is_returned);
    }

    @Test
    void repository_returns_empty_object_when_book_not_found_by_id() {
        gwt.test()
                .given(a_book_data_source)
                .and(mappers)
                .and(a_book_repository)
                .when(requesting_a_nonexistent_book_by_id)
                .then(nothing_is_returned);
    }

    @Test
    void repository_finds_books_by_title() {
        gwt.test()
                .given(a_book_data_source)
                .and(mappers)
                .and(a_book_repository)
                .when(requesting_a_book_by_title)
                .then(the_book_with_that_title_is_returned);
    }

    @Test
    void repository_finds_books_by_author() {
        gwt.test()
                .given(a_book_data_source)
                .and(mappers)
                .and(a_book_repository)
                .when(requesting_a_book_by_author)
                .then(the_book_by_that_author_is_returned);
    }

    @Test
    void repository_finds_books_by_genre() {
        gwt.test()
                .given(a_book_data_source)
                .and(mappers)
                .and(a_book_repository)
                .when(requesting_a_book_by_genre)
                .then(the_books_of_that_genre_are_returned);
    }

    @Test
    void repository_adds_book() {
        gwt.test()
                .given(a_book_data_source)
                .and(mappers)
                .and(a_book_repository)
                .when(adding_a_book_to_the_repository)
                .then(the_repository_adds_the_book_via_the_data_source);
    }

    @Test
    void repository_deletes_book() {
        gwt.test()
                .given(a_book_data_source)
                .and(mappers)
                .and(a_book_repository)
                .when(deleting_a_book_from_the_repository)
                .then(the_repository_deletes_the_book_via_the_data_source);
    }

    @Test
    void repository_updates_book() {
        gwt.test()
                .given(a_book_data_source)
                .and(mappers)
                .and(a_book_repository)
                .when(updating_a_book_in_the_repository)
                .then(the_repository_updates_the_book_via_the_data_source);
    }

    private final GwtFunction<BookRepositoryTestContext> a_book_data_source = context -> context.bookDataSource = Mockito.mock(BookDataSource.class);

    private final GwtFunction<BookRepositoryTestContext> mappers = context -> {
        context.authorMapper = Mockito.mock(AuthorMapper.class);
        context.bookMapper = Mockito.mock(BookMapper.class);
    };

    private final GwtFunction<BookRepositoryTestContext> a_book_repository
            = context -> context.bookRepository = new BookRepositoryImpl(context.bookDataSource, context.bookMapper, context.authorMapper);

    private final GwtFunction<BookRepositoryTestContext> requesting_all_books_from_the_repository = context -> {
        Iterable<BookData> bookIterable = getBookDataList();
        when(context.bookDataSource.findAll()).thenReturn(bookIterable);
        bookIterable.forEach(bookData -> {
            when(context.bookMapper.mapFrom(bookData)).thenReturn(getBook(bookData));
        });
        context.bookList = context.bookRepository.getAllBooks();
    };

    private final GwtFunction<BookRepositoryTestContext> requesting_a_book_by_id = context -> {
        Book book = getRedMarsBook();
        BookData bookData = getBookData(book);
        when(context.bookDataSource.findById(1)).thenReturn(bookData);
        when(context.bookMapper.mapFrom(bookData)).thenReturn(book);
        context.searchResult = context.bookRepository.getBookById(1);
    };

    private final GwtFunction<BookRepositoryTestContext> requesting_a_nonexistent_book_by_id = context -> {
        when(context.bookDataSource.findById(2)).thenReturn(null);
        context.searchResult = context.bookRepository.getBookById(2);
    };

    private final GwtFunction<BookRepositoryTestContext> requesting_a_book_by_title = context -> {
        Book book = getRedMarsBook();
        BookData bookData = getBookData(book);
        when(context.bookDataSource.findByTitle("Red Mars")).thenReturn(Collections.singletonList(bookData));
        when(context.bookMapper.mapFrom(bookData)).thenReturn(book);
        context.searchResultList = context.bookRepository.getBooksByTitle("Red Mars");
    };

    private final GwtFunction<BookRepositoryTestContext> requesting_a_book_by_author = context -> {
        Book book = getRedMarsBook();
        BookData bookData = getBookData(book);
        Author author = new Author("Kim", "Stanley", "Robinson");
        AuthorData authorData = getAuthorData(author);
        when(context.bookDataSource.findByAuthor(authorData)).thenReturn(Collections.singletonList(bookData));
        when(context.bookMapper.mapFrom(bookData)).thenReturn(book);
        when(context.authorMapper.mapFrom(author)).thenReturn(authorData);
        context.searchResultList = context.bookRepository.getBooksByAuthor(author);
    };

    private final GwtFunction<BookRepositoryTestContext> requesting_a_book_by_genre = context -> {
        List<BookData> bookDataList = getBookDataList();
        when(context.bookDataSource.findByGenreName("Science Fiction")).thenReturn(bookDataList);
        bookDataList.forEach(bookData -> {
            when(context.bookMapper.mapFrom(bookData)).thenReturn(getBook(bookData));
        });
        context.searchResultList = context.bookRepository.getBooksByGenre("Science Fiction");
    };

    private final GwtFunction<BookRepositoryTestContext> adding_a_book_to_the_repository = context -> {
        Book book = getRedMarsBook();
        context.bookData = getBookData(book);
        when(context.authorMapper.mapFrom(book.getAuthor())).thenReturn(context.bookData.getAuthor());
        when(context.bookMapper.mapFrom(book)).thenReturn(context.bookData);
        context.bookRepository.addBook(book);
    };

    private final GwtFunction<BookRepositoryTestContext> deleting_a_book_from_the_repository = context -> {
        Book book = getRedMarsBook();
        context.bookData = getBookData(book);
        when(context.authorMapper.mapFrom(book.getAuthor())).thenReturn(context.bookData.getAuthor());
        when(context.bookMapper.mapFrom(book)).thenReturn(context.bookData);
        context.bookRepository.deleteBook(book);
    };

    private final GwtFunction<BookRepositoryTestContext> updating_a_book_in_the_repository = context -> {
        Book book = getRedMarsBook();
        context.bookData = getBookData(book);
        when(context.authorMapper.mapFrom(book.getAuthor())).thenReturn(context.bookData.getAuthor());
        when(context.bookMapper.mapFrom(book)).thenReturn(context.bookData);
        context.bookRepository.saveBook(book);
    };

    private final GwtFunction<BookRepositoryTestContext> all_books_are_returned = context -> {
        assertThat(context.bookList.size(), is(2));
        Book book = context.bookList.get(0);
        assertThat(book.getTitle(), is("Red Mars"));
        book = context.bookList.get(1);
        assertThat(book.getTitle(), is("Foundation"));
    };

    private final GwtFunction<BookRepositoryTestContext> that_book_is_returned = context -> {
        assert context.searchResult.isPresent();
        assertThat(context.searchResult.get().getTitle(), is("Red Mars"));
    };

    private final GwtFunction<BookRepositoryTestContext> the_book_with_that_title_is_returned = context -> {
        assertThat(context.searchResultList.size(), is(1));
        assertThat(context.searchResultList.get(0).getTitle(), is("Red Mars"));
    };

    private final GwtFunction<BookRepositoryTestContext> the_book_by_that_author_is_returned = context -> {
        assertThat(context.searchResultList.size(), is(1));
        Book book = context.searchResultList.get(0);
        assertThat(book.getTitle(), is("Red Mars"));
        assertThat(book.getAuthor().getLastName(), is("Robinson"));
    };

    private final GwtFunction<BookRepositoryTestContext> the_books_of_that_genre_are_returned = context -> {
        assertThat(context.searchResultList.size(), is(2));
        Book book = context.searchResultList.get(0);
        assertThat(book.getTitle(), is("Red Mars"));
        book = context.searchResultList.get(1);
        assertThat(book.getTitle(), is("Foundation"));
    };

    private final GwtFunction<BookRepositoryTestContext> nothing_is_returned = context -> {
        assert context.searchResult.isEmpty();
    };

    private final GwtFunction<BookRepositoryTestContext> the_repository_adds_the_book_via_the_data_source
            = context -> verify(context.bookDataSource).save(context.bookData);

    private final GwtFunction<BookRepositoryTestContext> the_repository_deletes_the_book_via_the_data_source
            = context -> verify(context.bookDataSource).delete(context.bookData);

    private final GwtFunction<BookRepositoryTestContext> the_repository_updates_the_book_via_the_data_source
            = context -> verify(context.bookDataSource).save(context.bookData);

    private Book createBook(String title, Author author, String publisher, int publishYear, String isbn, int numberOfPages, Genre genre) {
        return new Book(title, author, publisher, publishYear, isbn, numberOfPages, genre);
    }

    private BookData getBookData(Book book) {
        BookData bookData = new BookData();
        bookData.setTitle(book.getTitle());
        bookData.setAuthor(getAuthorData(book.getAuthor()));
        bookData.setPublisher(book.getPublisher());
        bookData.setPublishYear(book.getPublishYear());
        bookData.setIsbn(book.getIsbn());
        bookData.setNumberOfPages(book.getNumberOfPages());
        bookData.setGenre(getGenreData(book.getGenre()));
        bookData.setPrice(book.getPrice());
        return bookData;
    }

    private Book getBook(BookData bookData) {
        return new Book(bookData.getTitle(), getAuthor(bookData.getAuthor()), bookData.getPublisher(), bookData.getPublishYear(), bookData.getIsbn(),
                bookData.getNumberOfPages(), getGenre(bookData.getGenre()));
    }

    private List<BookData> getBookDataList() {
        return List.of(
                getBookData(getRedMarsBook()),
                getBookData(createBook("Foundation", new Author("Isaac", null, "Asimov"), "A Publisher", 1972,
                        "Another ISBN", 400, new Genre("Science Fiction", 1.1)))
        );
    }

    private Book getRedMarsBook() {
        return createBook("Red Mars", new Author("Kim", "Stanley", "Robinson"), "A Publisher", 1995,
                "An ISBN", 450, new Genre("Science Fiction", 1.1));
    }

    private AuthorData getAuthorData(Author author) {
        AuthorData authorData = new AuthorData();
        authorData.setFirstName(author.getFirstName());
        authorData.setMiddleName(author.getMiddleName());
        authorData.setLastName(author.getLastName());
        return authorData;
    }

    private Author getAuthor(AuthorData authorData) {
        return new Author(authorData.getFirstName(), authorData.getMiddleName(), authorData.getLastName());
    }

    private GenreData getGenreData(Genre genre) {
        GenreData genreData = new GenreData();
        genreData.setName(genre.getName());
        genreData.setPricingFactor(genre.getPricingFactor());
        return genreData;
    }

    private Genre getGenre(GenreData genreData) {
        return new Genre(genreData.getName(), genreData.getPricingFactor());
    }

    public static class BookRepositoryTestContext extends Context {
        AuthorMapper authorMapper;
        BookData bookData;
        BookDataSource bookDataSource;
        BookMapper bookMapper;
        BookRepositoryImpl bookRepository;
        List<Book> bookList;
        List<Book> searchResultList;
        Optional<Book> searchResult;
    }

}
