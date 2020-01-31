package com.improving.bookstore;

import com.improving.bookstore.interactors.RetrieveAllBooksInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByAuthorInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByGenreInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByTitleInteractor;
import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.services.BookstoreService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

public class BookRetrievalTest {

    private BookstoreService bookstoreService;
    private List<Book> bookList;
    private RetrieveAllBooksInteractor retrieveAllBooksInteractor;
    private RetrieveBooksByAuthorInteractor retrieveBooksByAuthorInteractor;
    private RetrieveBooksByGenreInteractor retrieveBooksByGenreInteractor;
    private RetrieveBooksByTitleInteractor retrieveBooksByTitleInteractor;

    @Test
    void all_books_are_retrieved() {
        given_a_book_service();
        given_an_interactor_for_retrieving_all_books();
        when_all_books_are_requested();
        then_all_books_are_returned();
    }

    @Test
    void all_books_are_retrieved_by_author() {
        given_a_book_service();
        given_an_interactor_for_retrieving_books_by_author();
        when_books_are_retrieved_by_author();
        then_all_books_by_that_author_are_returned();
    }

    @Test
    void all_books_are_retrieved_by_genre() {
        given_a_book_service();
        given_an_interactor_for_retrieving_books_by_genre();
        when_books_are_retrieved_by_genre();
        then_all_books_of_that_genre_are_returned();
    }

    @Test
    void all_books_are_retrieved_by_title() {
        given_a_book_service();
        given_an_interactor_for_retrieving_books_by_title();
        when_books_are_retrieved_by_title();
        then_all_books_with_that_title_are_returned();
    }

    private void given_an_interactor_for_retrieving_all_books() {
        retrieveAllBooksInteractor = Mockito.mock(RetrieveAllBooksInteractor.class);
        bookstoreService.setRetrieveAllBooksInteractor(retrieveAllBooksInteractor);
    }

    private void given_an_interactor_for_retrieving_books_by_author() {
        retrieveBooksByAuthorInteractor = Mockito.mock(RetrieveBooksByAuthorInteractor.class);
        bookstoreService.setRetrieveBooksByAuthorInteractor(retrieveBooksByAuthorInteractor);
    }

    private void given_an_interactor_for_retrieving_books_by_genre() {
        retrieveBooksByGenreInteractor = Mockito.mock(RetrieveBooksByGenreInteractor.class);
        bookstoreService.setRetrieveBooksByGenreInteractor(retrieveBooksByGenreInteractor);
    }

    private void given_an_interactor_for_retrieving_books_by_title() {
        retrieveBooksByTitleInteractor = Mockito.mock(RetrieveBooksByTitleInteractor.class);
        bookstoreService.setRetrieveBooksByTitleInteractor(retrieveBooksByTitleInteractor);
    }

    private void given_a_book_service() {
        bookstoreService = new BookstoreService();
    }

    private void when_all_books_are_requested() {
        when(retrieveAllBooksInteractor.retrieveAllBooks()).thenReturn(getBookList());
        bookList = bookstoreService.getAllBooks();
    }

    private void when_books_are_retrieved_by_author() {
        when(retrieveBooksByAuthorInteractor.retrieveBooksByAuthor(getAuthorKimStanleyRobinson())).thenReturn(Collections.singletonList(getRedMarsBook()));
        bookList = bookstoreService.getBooksByAuthor(getAuthorKimStanleyRobinson());
    }

    private void when_books_are_retrieved_by_genre() {
        when(retrieveBooksByGenreInteractor.retrieveBooksByGenre("Science Fiction")).thenReturn(getBookList());
        bookList = bookstoreService.getBooksByGenre("Science Fiction");
    }

    private void when_books_are_retrieved_by_title() {
        when(retrieveBooksByTitleInteractor.retrieveBooksByTitle("Red Mars")).thenReturn(Collections.singletonList(getRedMarsBook()));
        bookList = bookstoreService.getBooksByTitle("Red Mars");
    }

    private void then_all_books_are_returned() {
        assertThat(bookList.size(), is(2));
        assertThat(bookList.get(0).getTitle(), is("Red Mars"));
        assertThat(bookList.get(1).getTitle(), is("Foundation"));
    }

    private void then_all_books_by_that_author_are_returned() {
        assertThat(bookList.size(), is(1));
        assertThat(bookList.get(0).getTitle(), is("Red Mars"));
    }

    private void then_all_books_of_that_genre_are_returned() {
        assertThat(bookList.size(), is(2));
        assertThat(bookList.get(0).getTitle(), is("Red Mars"));
        assertThat(bookList.get(1).getTitle(), is("Foundation"));
    }

    private void then_all_books_with_that_title_are_returned() {
        assertThat(bookList.size(), is(1));
        assertThat(bookList.get(0).getTitle(), is("Red Mars"));
    }

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

}
