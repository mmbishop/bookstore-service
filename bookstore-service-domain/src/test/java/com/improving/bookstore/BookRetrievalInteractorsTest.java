package com.improving.bookstore;

import com.improving.bookstore.model.Author;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.model.Genre;
import com.improving.bookstore.repositories.BookRepository;
import com.improving.bookstore.interactors.RetrieveAllBooksInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByAuthorInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByGenreInteractor;
import com.improving.bookstore.interactors.RetrieveBooksByTitleInteractor;
import io.github.mmbishop.gwttest.core.GwtTest;
import io.github.mmbishop.gwttest.functions.GwtFunction;
import io.github.mmbishop.gwttest.model.Context;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

public class BookRetrievalInteractorsTest {

    private final GwtTest<BookTestContext> gwt = new GwtTest<>(BookTestContext.class);

    @Test
    void all_books_are_retrieved() {
        gwt.test()
                .given(a_book_repository)
                .and(a_book_inventory)
                .when(requesting_all_books)
                .then(all_books_are_retrieved);
    }

    @Test
    void books_are_retrieved_by_title() {
        gwt.test()
                .given(a_book_repository)
                .and(a_book_inventory)
                .when(requesting_a_book_by_title)
                .then(the_book_with_that_title_is_retrieved);
    }

    @Test
    void books_are_retrieved_by_author() {
        gwt.test()
                .given(a_book_repository)
                .and(a_book_inventory)
                .when(requesting_all_books_by_author)
                .then(all_books_by_that_author_are_retrieved);
    }

    @Test
    void books_are_retrieved_by_genre() {
        gwt.test()
                .given(a_book_repository)
                .and(a_book_inventory)
                .when(requesting_all_books_by_genre)
                .then(all_books_of_that_genre_are_retrieved);
    }

    private final GwtFunction<BookTestContext> a_book_repository = context -> context.bookRepository = Mockito.mock(BookRepository.class);

    private final GwtFunction<BookTestContext> a_book_inventory = context -> context.bookInventory = getBookInventory();

    private final GwtFunction<BookTestContext> requesting_all_books = context -> {
        when(context.bookRepository.getAllBooks()).thenReturn(context.bookInventory);
        context.bookList = new RetrieveAllBooksInteractor(context.bookRepository).retrieveAllBooks();
    };

    private final GwtFunction<BookTestContext> requesting_a_book_by_title = context -> {
        when(context.bookRepository.getBooksByTitle("Foundation")).thenReturn(List.of(getBook("Foundation", context.bookInventory)));
        context.bookList = new RetrieveBooksByTitleInteractor(context.bookRepository).retrieveBooksByTitle("Foundation");
    };

    private final GwtFunction<BookTestContext> requesting_all_books_by_author = context -> {
        Author isaacAsimov = createAuthor("Isaac", "Asimov");
        when(context.bookRepository.getBooksByAuthor(isaacAsimov)).thenReturn(List.of(
                getBook("Foundation", context.bookInventory),
                getBook("Foundation and Empire", context.bookInventory)
        ));
        context.bookList = new RetrieveBooksByAuthorInteractor(context.bookRepository).retrieveBooksByAuthor(isaacAsimov);
    };

    private final GwtFunction<BookTestContext> requesting_all_books_by_genre = context -> {
        when(context.bookRepository.getBooksByGenre("Science Fiction")).thenReturn(List.of(
                getBook("Foundation", context.bookInventory),
                getBook("Foundation and Empire", context.bookInventory),
                getBook("Fahrenheit 451", context.bookInventory)
        ));
        context.bookList = new RetrieveBooksByGenreInteractor(context.bookRepository).retrieveBooksByGenre("Science Fiction");
    };

    private final GwtFunction<BookTestContext> all_books_are_retrieved
            = context ->  assertThat(context.bookList.size(), is(context.bookInventory.size()));

    private final GwtFunction<BookTestContext> the_book_with_that_title_is_retrieved = context -> {
        assertThat(context.bookList.size(), is(1));
        assertThat(context.bookList.get(0).getTitle(), is("Foundation"));
    };

    private final GwtFunction<BookTestContext> all_books_by_that_author_are_retrieved = context -> {
        assertThat(context.bookList.size(), is(2));
        context.bookList.forEach(book -> {
            if (! "Isaac".equals(book.getAuthor().getFirstName()) || ! "Asimov".equals(book.getAuthor().getLastName())) {
                fail("Author must be Isaac Asimov");
            }
        });
    };

    private final GwtFunction<BookTestContext> all_books_of_that_genre_are_retrieved = context -> {
        assertThat(context.bookList.size(), is(3));
        context.bookList.forEach(book -> {
            if (! "Science Fiction".equals(book.getGenre().getName())) {
                fail("Genre must be Science Fiction");
            }
        });
    };

    private List<Book> getBookInventory() {
        return List.of(
                createBook("Foundation", createAuthor("Isaac", "Asimov"), 410, "Science Fiction"),
                createBook("Foundation and Empire", createAuthor("Isaac", "Asimov"), 385, "Science Fiction"),
                createBook("Fahrenheit 451", createAuthor("Ray", "Bradbury"), 80, "Science Fiction"),
                createBook("Lord Foul's Bane", createAuthor("Steven", "Donaldson"), 475, "Fantasy")
        );
    }

    private Book getBook(String title, List<Book> bookInventory) {
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

    public static final class BookTestContext extends Context {
        BookRepository bookRepository;
        List<Book> bookInventory;
        List<Book> bookList;
    }

}
