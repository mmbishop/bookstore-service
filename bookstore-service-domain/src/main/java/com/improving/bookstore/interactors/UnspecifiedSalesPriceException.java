package com.improving.bookstore.interactors;

public class UnspecifiedSalesPriceException extends RuntimeException {

    public UnspecifiedSalesPriceException() {
        super("No sales price was provided.");
    }
}
