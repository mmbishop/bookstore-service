package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.interactors.RetrieveAllBooksInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByAuthorInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByGenreInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByTitleInteractor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

public class BookRetrievalInteractorsTest {

    private BookRepository bookRepository;
    private List<Book> bookInventory = getBookInventory();
    private List<Book> bookList;

    @Test
    void all_books_are_retrieved() {
        given_a_book_repository();
        when_all_books_are_requested();
        then_all_books_are_retrieved();
    }

    @Test
    void books_are_retrieved_by_title() {
        given_a_book_repository();
        when_a_book_is_requested_by_title();
        then_the_book_with_that_title_is_retrieved();
    }

    @Test
    void books_are_retrieved_by_author() {
        given_a_book_repository();
        when_all_books_are_requested_by_author();
        then_all_books_by_that_author_are_retrieved();
    }

    @Test
    void books_are_retrieved_by_genre() {
        given_a_book_repository();
        when_all_books_are_requested_by_genre();
        then_all_books_of_that_genre_are_retrieved();
    }

    private void given_a_book_repository() {
        bookRepository = Mockito.mock(BookRepository.class);
    }

    private void when_all_books_are_requested() {
        when(bookRepository.getAllBooks()).thenReturn(bookInventory);
        bookList = new RetrieveAllBooksInteractor(bookRepository).retrieveAllBooks();
    }

    private void when_a_book_is_requested_by_title() {
        when(bookRepository.getBooksByTitle("Foundation")).thenReturn(List.of(getBook("Foundation")));
        bookList = new RetrieveBooksByTitleInteractor(bookRepository).retrieveBooksByTitle("Foundation");
    }

    private void when_all_books_are_requested_by_author() {
        Author isaacAsimov = createAuthor("Isaac", "Asimov");
        when(bookRepository.getBooksByAuthor(isaacAsimov)).thenReturn(List.of(
                getBook("Foundation"),
                getBook("Foundation and Empire")
        ));
        bookList = new RetrieveBooksByAuthorInteractor(bookRepository).retrieveBooksByAuthor(isaacAsimov);
    }

    private void when_all_books_are_requested_by_genre() {
        when(bookRepository.getBooksByGenre("Science Fiction")).thenReturn(List.of(
                getBook("Foundation"),
                getBook("Foundation and Empire"),
                getBook("Fahrenheit 451")
        ));
        bookList = new RetrieveBooksByGenreInteractor(bookRepository).retrieveBooksByGenre("Science Fiction");
    }

    private void then_all_books_are_retrieved() {
        assertThat(bookList.size(), is(bookInventory.size()));
    }

    private void then_the_book_with_that_title_is_retrieved() {
        assertThat(bookList.size(), is(1));
        assertThat(bookList.get(0).getTitle(), is("Foundation"));
    }

    private void then_all_books_by_that_author_are_retrieved() {
        assertThat(bookList.size(), is(2));
        bookList.forEach(book -> {
            if (! "Isaac".equals(book.getAuthor().getFirstName()) || ! "Asimov".equals(book.getAuthor().getLastName())) {
                fail("Author must be Isaac Asimov");
            }
        });
    }

    private void then_all_books_of_that_genre_are_retrieved() {
        assertThat(bookList.size(), is(3));
        bookList.forEach(book -> {
            if (! "Science Fiction".equals(book.getGenre().getName())) {
                fail("Genre must be Science Fiction");
            }
        });
    }

    private List<Book> getBookInventory() {
        return List.of(
                createBook("Foundation", createAuthor("Isaac", "Asimov"), 410, "Science Fiction"),
                createBook("Foundation and Empire", createAuthor("Isaac", "Asimov"), 385, "Science Fiction"),
                createBook("Fahrenheit 451", createAuthor("Ray", "Bradbury"), 80, "Science Fiction"),
                createBook("Lord Foul's Bane", createAuthor("Steven", "Donaldson"), 475, "Fantasy")
        );
    }

    private Book getBook(String title) {
        Optional<Book> book = bookInventory.stream().filter(b -> title.equals(b.getTitle())).findFirst();
        return book.orElse(null);
    }

    private Book createBook(String title, Author author, int numberOfPages, String genre) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setNumberOfPages(numberOfPages);
        book.setGenre(new Genre(genre, 1.0));
        return book;
    }

    private Author createAuthor(String firstName, String lastName) {
        return new Author(firstName, null, lastName);
    }

}
