package com.improving.bookstore;

import com.improving.bookstore.interactors.RetrieveAllBooksInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByAuthorInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByGenreInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByTitleInteractor;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

public class BookRetrievalTest {

    private final GwtTest<BookTestContext> gwt = new GwtTest<>(BookTestContext.class);

    @Test
    void all_books_are_retrieved() {
        gwt.test()
                .given(a_book_service)
                .and(an_interactor_for_retrieving_all_books)
                .when(requesting_all_books)
                .then(all_books_are_returned);
    }

    @Test
    void all_books_are_retrieved_by_author() {
        gwt.test()
                .given(a_book_service)
                .and(an_interactor_for_retrieving_books_by_author)
                .when(requesting_books_by_author)
                .then(all_books_by_that_author_are_returned);
    }

    @Test
    void all_books_are_retrieved_by_genre() {
        gwt.test()
                .given(a_book_service)
                .and(an_interactor_for_retrieving_books_by_genre)
                .when(requesting_books_by_genre)
                .then(all_books_of_that_genre_are_returned);
    }

    @Test
    void all_books_are_retrieved_by_title() {
        gwt.test()
                .given(a_book_service)
                .and(an_interactor_for_retrieving_books_by_title)
                .when(requesting_books_by_title)
                .then(all_book_with_that_title_are_returned);
    }

    private final GwtFunction<BookTestContext> an_interactor_for_retrieving_all_books = context -> {
        context.retrieveAllBooksInteractor = Mockito.mock(RetrieveAllBooksInteractor.class);
        context.bookstoreService.setRetrieveAllBooksInteractor(context.retrieveAllBooksInteractor);
    };

    private final GwtFunction<BookTestContext> an_interactor_for_retrieving_books_by_author = context -> {
        context.retrieveBooksByAuthorInteractor = Mockito.mock(RetrieveBooksByAuthorInteractor.class);
        context.bookstoreService.setRetrieveBooksByAuthorInteractor(context.retrieveBooksByAuthorInteractor);
    };

    private final GwtFunction<BookTestContext> an_interactor_for_retrieving_books_by_genre = context -> {
        context.retrieveBooksByGenreInteractor = Mockito.mock(RetrieveBooksByGenreInteractor.class);
        context.bookstoreService.setRetrieveBooksByGenreInteractor(context.retrieveBooksByGenreInteractor);
    };

    private final GwtFunction<BookTestContext> an_interactor_for_retrieving_books_by_title = context -> {
        context.retrieveBooksByTitleInteractor = Mockito.mock(RetrieveBooksByTitleInteractor.class);
        context.bookstoreService.setRetrieveBooksByTitleInteractor(context.retrieveBooksByTitleInteractor);
    };

    private final GwtFunction<BookTestContext> a_book_service = context -> context.bookstoreService = new BookstoreService();

    private final GwtFunction<BookTestContext> requesting_all_books = context -> {
        when(context.retrieveAllBooksInteractor.retrieveAllBooks()).thenReturn(getBookList());
        context.bookList = context.bookstoreService.getAllBooks();
    };

    private final GwtFunction<BookTestContext> requesting_books_by_author = context -> {
        when(context.retrieveBooksByAuthorInteractor.retrieveBooksByAuthor(getAuthorKimStanleyRobinson()))
                .thenReturn(Collections.singletonList(getRedMarsBook()));
        context.bookList = context.bookstoreService.getBooksByAuthor("Kim", "Stanley", "Robinson");
    };

    private final GwtFunction<BookTestContext> requesting_books_by_genre = context -> {
        when(context.retrieveBooksByGenreInteractor.retrieveBooksByGenre("Science Fiction")).thenReturn(getBookList());
        context.bookList = context.bookstoreService.getBooksByGenre("Science Fiction");
    };

    private final GwtFunction<BookTestContext> requesting_books_by_title = context -> {
        when(context.retrieveBooksByTitleInteractor.retrieveBooksByTitle("Red Mars")).thenReturn(Collections.singletonList(getRedMarsBook()));
        context.bookList = context.bookstoreService.getBooksByTitle("Red Mars");
    };

    private final GwtFunction<BookTestContext> all_books_are_returned = context -> {
        assertThat(context.bookList.size(), is(2));
        assertThat(context.bookList.get(0).getTitle(), is("Red Mars"));
        assertThat(context.bookList.get(1).getTitle(), is("Foundation"));
    };

    private final GwtFunction<BookTestContext> all_books_by_that_author_are_returned = context -> {
        assertThat(context.bookList.size(), is(1));
        assertThat(context.bookList.get(0).getTitle(), is("Red Mars"));
    };

    private final GwtFunction<BookTestContext> all_books_of_that_genre_are_returned = context -> {
        assertThat(context.bookList.size(), is(2));
        assertThat(context.bookList.get(0).getTitle(), is("Red Mars"));
        assertThat(context.bookList.get(1).getTitle(), is("Foundation"));
    };

    private final GwtFunction<BookTestContext> all_book_with_that_title_are_returned = context -> {
        assertThat(context.bookList.size(), is(1));
        assertThat(context.bookList.get(0).getTitle(), is("Red Mars"));
    };

    private List<Book> getBookList() {
        return List.of(
                getRedMarsBook(),
                createBook("Foundation", new Author("Isaac", null, "Asimov"), "A Publisher", 1972,
                        "Another ISBN", 400, new Genre("Science Fiction", 1.1))
        );
    }

    private Book getRedMarsBook() {
        return createBook("Red Mars", new Author("Kim", "Stanley", "Robinson"), "A Publisher", 1995,
                "An ISBN", 450, new Genre("Science Fiction", 1.1));
    }

    private Author getAuthorKimStanleyRobinson() {
        return new Author("Kim", "Stanley", "Robinson");
    }

    private Book createBook(String title, Author author, String publisher, int publishYear, String isbn, int numberOfPages, Genre genre) {
        return new Book(title, author, publisher, publishYear, isbn, numberOfPages, genre);
    }

    public static final class BookTestContext extends Context {
        BookstoreService bookstoreService;
        List<Book> bookList;
        RetrieveAllBooksInteractor retrieveAllBooksInteractor;
        RetrieveBooksByAuthorInteractor retrieveBooksByAuthorInteractor;
        RetrieveBooksByGenreInteractor retrieveBooksByGenreInteractor;
        RetrieveBooksByTitleInteractor retrieveBooksByTitleInteractor;
    }

}
