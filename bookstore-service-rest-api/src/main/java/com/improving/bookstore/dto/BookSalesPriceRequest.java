package com.improving.bookstore.dto;

import java.math.BigDecimal;

public class BookSalesPriceRequest {

    private BigDecimal newPrice;

    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }

}
