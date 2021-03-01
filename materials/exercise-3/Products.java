package com.lunatech.training.quarkus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Products {

    private static final List<Product> products = Arrays.asList(
        new Product(1L, "Chair", "A metal frame chair, with oak seat", new BigDecimal("59.95")),
        new Product(2L, "Dinner Table", "Sturdy oak Table", new BigDecimal("200")),
        new Product(3L, "Coffee Table", "An oak coffee Table", new BigDecimal("120")),
        new Product(4L, "Side Table", "A Nice little oak side table", new BigDecimal("80")),
        new Product(5L, "Mirror", "A round mirror with oak fram", new BigDecimal("80")),
        new Product(6L, "Lamp", "A light that shines", new BigDecimal("45")),
        new Product(7L, "Carpet", "Soft carpet", new BigDecimal("39.95")));

    public static List<Product> all() {
        return products;
    }

    // It would be better if this would return an Optional<Product>, but at this point in the course we
    // don't want to force the students to deal with it.
    public static Product getById(Long identifier) {
        return products.stream().filter(p -> p.id.equals(identifier)).findAny().orElse(null);
    }
}
