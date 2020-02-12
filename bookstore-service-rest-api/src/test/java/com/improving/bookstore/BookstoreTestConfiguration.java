package com.improving.bookstore;

import com.improving.bookstore.controllers.BookstoreController;
import com.improving.bookstore.services.BookstoreService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookstoreTestConfiguration {

    private BookstoreService service = Mockito.mock(BookstoreService.class);

    @Bean
    public BookstoreService bookstoreService() {
        return service;
    }

    @Bean
    public BookstoreController bookstoreController() {
        return new BookstoreController(service);
    }

}
