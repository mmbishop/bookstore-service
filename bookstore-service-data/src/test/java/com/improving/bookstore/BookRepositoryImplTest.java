package com.improving.bookstore;

import com.improving.bookstore.mappers.AuthorMapper;
import com.improving.bookstore.mappers.BookMapper;
import com.improving.bookstore.model.*;
import com.improving.bookstore.repositories.BookDataSource;
import com.improving.bookstore.repositories.BookRepositoryImpl;
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

    private AuthorMapper authorMapper;
    private BookData bookData;
    private BookDataSource bookDataSource;
    private BookMapper bookMapper;
    private BookRepositoryImpl bookRepository;
    private List<Book> bookList;
    private List<Book> searchResultList;
    private Optional<Book> searchResult;

    @Test
    void repository_returns_all_books() {
        given_a_book_data_source();
        given_mappers();
        given_a_book_repository();
        when_all_books_are_requested_from_the_repository();
        then_all_books_are_returned();
    }

    @Test
    void repository_finds_a_book_by_id() {
        given_a_book_data_source();
        given_mappers();
        given_a_book_repository();
        when_a_book_is_requested_by_id();
        then_that_book_is_returned();
    }

    @Test
    void repository_returns_empty_object_when_book_not_found_by_id() {
        given_a_book_data_source();
        given_mappers();
        given_a_book_repository();
        when_a_nonexistent_book_is_requested_by_id();
        then_nothing_is_returned();
    }

    @Test
    void repository_finds_books_by_title() {
        given_a_book_data_source();
        given_mappers();
        given_a_book_repository();
        when_a_book_is_requested_by_title();
        then_the_book_with_that_title_is_returned();
    }

    @Test
    void repository_finds_books_by_author() {
        given_a_book_data_source();
        given_mappers();
        given_a_book_repository();
        when_a_book_is_requested_by_author();
        then_the_book_by_that_author_is_returned();
    }

    @Test
    void repository_finds_books_by_genre() {
        given_a_book_data_source();
        given_mappers();
        given_a_book_repository();
        when_a_book_is_requested_by_genre();
        then_the_books_of_that_genre_are_returned();
    }

    @Test
    void repository_adds_book() {
        given_a_book_data_source();
        given_mappers();
        given_a_book_repository();
        when_a_book_is_added_to_the_repository();
        then_the_repository_adds_the_book_via_the_data_source();
    }

    @Test
    void repository_deletes_book() {
        given_a_book_data_source();
        given_mappers();
        given_a_book_repository();
        when_a_book_is_deleted_from_the_repository();
        then_the_repository_deletes_the_book_via_the_data_source();
    }

    @Test
    void repository_updates_book() {
        given_a_book_data_source();
        given_mappers();
        given_a_book_repository();
        when_a_book_is_updated_in_the_repository();
        then_the_repository_updates_the_book_via_the_data_source();
    }

    private void given_a_book_data_source() {
        bookDataSource = Mockito.mock(BookDataSource.class);
    }

    private void given_mappers() {
        authorMapper = Mockito.mock(AuthorMapper.class);
        bookMapper = Mockito.mock(BookMapper.class);
    }

    private void given_a_book_repository() {
        bookRepository = new BookRepositoryImpl(bookDataSource, bookMapper, authorMapper);
    }

    private void when_all_books_are_requested_from_the_repository() {
        Iterable<BookData> bookIterable = getBookDataList();
        when(bookDataSource.findAll()).thenReturn(bookIterable);
        bookIterable.forEach(bookData -> {
            when(bookMapper.mapFrom(bookData)).thenReturn(getBook(bookData));
        });
        bookList = bookRepository.getAllBooks();
    }

    private void when_a_book_is_requested_by_id() {
        Book book = getRedMarsBook();
        BookData bookData = getBookData(book);
        when(bookDataSource.findById(1)).thenReturn(bookData);
        when(bookMapper.mapFrom(bookData)).thenReturn(book);
        searchResult = bookRepository.getBookById(1);
    }

    private void when_a_nonexistent_book_is_requested_by_id() {
        when(bookDataSource.findById(2)).thenReturn(null);
        searchResult = bookRepository.getBookById(2);
    }

    private void when_a_book_is_requested_by_title() {
        Book book = getRedMarsBook();
        BookData bookData = getBookData(book);
        when(bookDataSource.findByTitle("Red Mars")).thenReturn(Collections.singletonList(bookData));
        when(bookMapper.mapFrom(bookData)).thenReturn(book);
        searchResultList = bookRepository.getBooksByTitle("Red Mars");
    }

    private void when_a_book_is_requested_by_author() {
        Book book = getRedMarsBook();
        BookData bookData = getBookData(book);
        Author author = new Author("Kim", "Stanley", "Robinson");
        AuthorData authorData = getAuthorData(author);
        when(bookDataSource.findByAuthor(authorData)).thenReturn(Collections.singletonList(bookData));
        when(bookMapper.mapFrom(bookData)).thenReturn(book);
        when(authorMapper.mapFrom(author)).thenReturn(authorData);
        searchResultList = bookRepository.getBooksByAuthor(author);
    }

    private void when_a_book_is_requested_by_genre() {
        List<BookData> bookDataList = getBookDataList();
        when(bookDataSource.findByGenreName("Science Fiction")).thenReturn(bookDataList);
        bookDataList.forEach(bookData -> {
            when(bookMapper.mapFrom(bookData)).thenReturn(getBook(bookData));
        });
        searchResultList = bookRepository.getBooksByGenre("Science Fiction");
    }

    private void when_a_book_is_added_to_the_repository() {
        Book book = getRedMarsBook();
        bookData = getBookData(book);
        when(authorMapper.mapFrom(book.getAuthor())).thenReturn(bookData.getAuthor());
        when(bookMapper.mapFrom(book)).thenReturn(bookData);
        bookRepository.addBook(book);
    }

    private void when_a_book_is_deleted_from_the_repository() {
        Book book = getRedMarsBook();
        bookData = getBookData(book);
        when(authorMapper.mapFrom(book.getAuthor())).thenReturn(bookData.getAuthor());
        when(bookMapper.mapFrom(book)).thenReturn(bookData);
        bookRepository.deleteBook(book);
    }

    private void when_a_book_is_updated_in_the_repository() {
        Book book = getRedMarsBook();
        bookData = getBookData(book);
        when(authorMapper.mapFrom(book.getAuthor())).thenReturn(bookData.getAuthor());
        when(bookMapper.mapFrom(book)).thenReturn(bookData);
        bookRepository.saveBook(book);
    }

    private void then_all_books_are_returned() {
        assertThat(bookList.size(), is(2));
        Book book = bookList.get(0);
        assertThat(book.getTitle(), is("Red Mars"));
        book = bookList.get(1);
        assertThat(book.getTitle(), is("Foundation"));
    }

    private void then_that_book_is_returned() {
        assert searchResult.isPresent();
        assertThat(searchResult.get().getTitle(), is("Red Mars"));
    }

    private void then_the_book_with_that_title_is_returned() {
        assertThat(searchResultList.size(), is(1));
        assertThat(searchResultList.get(0).getTitle(), is("Red Mars"));
    }

    private void then_the_book_by_that_author_is_returned() {
        assertThat(searchResultList.size(), is(1));
        Book book = searchResultList.get(0);
        assertThat(book.getTitle(), is("Red Mars"));
        assertThat(book.getAuthor().getLastName(), is("Robinson"));
    }

    private void then_the_books_of_that_genre_are_returned() {
        assertThat(searchResultList.size(), is(2));
        Book book = searchResultList.get(0);
        assertThat(book.getTitle(), is("Red Mars"));
        book = searchResultList.get(1);
        assertThat(book.getTitle(), is("Foundation"));
    }

    private void then_nothing_is_returned() {
        assert searchResult.isEmpty();
    }

    private void then_the_repository_adds_the_book_via_the_data_source() {
        verify(bookDataSource).save(bookData);
    }

    private void then_the_repository_deletes_the_book_via_the_data_source() {
        verify(bookDataSource).delete(bookData);
    }

    private void then_the_repository_updates_the_book_via_the_data_source() {
        verify(bookDataSource).save(bookData);
    }

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

}
