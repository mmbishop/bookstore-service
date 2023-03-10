package com.improving.bookstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.improving.bookstore.model.Book;
import com.improving.bookstore.services.BookstoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = BookstoreTestConfiguration.class)
class BookstoreServiceApplicationTests {

    private static final String CONTEXT_PATH = "/bookstore";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookstoreService bookstoreService;

    private BookTestSupport bookTestSupport = new BookTestSupport();
    private List<Book> bookList;
    private ResultActions resultActions;

    @Test
    void controller_retrieves_all_books() throws Exception {
        given_a_book_service_with_a_repository_containing_books();
        when_the_controller_is_requested_to_retrieve_all_books();
        then_all_books_are_retrieved();
    }

    private void given_a_book_service_with_a_repository_containing_books() {
        bookList = bookTestSupport.getBookList();
        when(bookstoreService.getAllBooks()).thenReturn(bookList);
    }

    private void when_the_controller_is_requested_to_retrieve_all_books() throws Exception {
        resultActions = mockMvc.perform(get(CONTEXT_PATH + "/books").contextPath(CONTEXT_PATH));
    }

    private void then_all_books_are_retrieved() throws Exception {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        resultActions.andExpect(status().isOk()).andExpect(content().json(objectWriter.writeValueAsString(bookList)));
    }

}
