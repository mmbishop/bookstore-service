package com.improving.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Genre")
public class GenreData {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private double pricingFactor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricingFactor() {
        return pricingFactor;
    }

    public void setPricingFactor(double pricingFactor) {
        this.pricingFactor = pricingFactor;
    }

}
