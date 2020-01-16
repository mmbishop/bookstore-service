package com.improving.bookstore.model;

import java.util.Objects;

public final class Genre {

    private String name;
    private double pricingFactor;

    public Genre(String name, double pricingFactor) {
        this.name = name;
        this.pricingFactor = pricingFactor;
    }

    public String getName() {
        return name;
    }

    public double getPricingFactor() {
        return pricingFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return name.equals(genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
