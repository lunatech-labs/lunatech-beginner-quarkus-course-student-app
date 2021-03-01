package com.lunatech.training.quarkus;

import java.math.BigDecimal;

public class Product {

    public final Long id;
    public final String name;
    public final String description;
    public final BigDecimal price;

    public Product(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
