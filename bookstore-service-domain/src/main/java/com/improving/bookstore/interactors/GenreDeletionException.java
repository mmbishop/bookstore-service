package com.improving.bookstore.interactors;

public class GenreDeletionException extends RuntimeException {

    public GenreDeletionException(String genreName) {
        super("Cannot delete genre " + genreName + " because books of that genre are still in the bookstore.");
    }

}
