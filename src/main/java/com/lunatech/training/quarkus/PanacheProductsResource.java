package com.lunatech.training.quarkus;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;

public interface PanacheProductsResource extends PanacheEntityResource<Product, Long> {

}
