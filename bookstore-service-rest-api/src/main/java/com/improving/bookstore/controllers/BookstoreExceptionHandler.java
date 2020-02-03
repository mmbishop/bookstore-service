package com.improving.bookstore.controllers;

import com.improving.bookstore.interactors.BookNotFoundException;
import com.improving.bookstore.interactors.UnwantedGenreException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.core.Response;

@ControllerAdvice
public class BookstoreExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public Response handleBookNotFoundException(BookNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
    }

    @ExceptionHandler(UnwantedGenreException.class)
    public Response handleUnwantedGenreException(UnwantedGenreException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
    }

}
