package com.lunatech.training.quarkus;

import org.hibernate.validator.constraints.Length;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Product extends PanacheEntity {

    @NotNull
    @Length(min = 3)
    public String name;

    @NotNull
    @Length(min = 10)
    public String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Digits(integer = 10, fraction = 2)
    public BigDecimal price;

    public Product(){}

    public Product(Long id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
