package com.improving.bookstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = BookstoreTestConfiguration.class)
class BookstoreServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
