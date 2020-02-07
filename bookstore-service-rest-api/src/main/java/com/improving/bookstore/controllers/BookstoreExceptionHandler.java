package com.improving.bookstore.controllers;

import com.improving.bookstore.interactors.BookNotFoundException;
import com.improving.bookstore.interactors.UnwantedGenreException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookstoreExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UnwantedGenreException.class)
    public ResponseEntity<String> handleUnwantedGenreException(UnwantedGenreException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
