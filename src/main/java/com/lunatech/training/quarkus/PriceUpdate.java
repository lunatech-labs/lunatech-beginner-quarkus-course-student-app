package com.lunatech.training.quarkus;

import java.math.BigDecimal;

public class PriceUpdate {
    public Long productId;
    public BigDecimal price;

    public PriceUpdate(){}

    public PriceUpdate(Long productId, BigDecimal price) {
        this.productId = productId;
        this.price = price;
    }

    public String toString() {
        return "Price(" + productId + ", " + price.toString() + ")";
    }
}
