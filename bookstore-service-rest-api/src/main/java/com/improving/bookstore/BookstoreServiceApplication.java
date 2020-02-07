package com.improving.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("applicationContext.xml")
public class BookstoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreServiceApplication.class, args);
    }

}
