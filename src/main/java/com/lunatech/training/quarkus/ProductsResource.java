package com.lunatech.training.quarkus;

import javax.inject.Inject;
import javax.validation.Valid;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource {

    @Inject
    PgPool client;

    @GET
    public Multi<Product> products() {
        return Product.streamAll();
    }

   @GET
    @Path("/{id}")
    public Uni<Product> product(@PathParam("id") Long identifier) {
        return Product.<Product>findById(identifier)
                .onItem().ifNull().failWith(NotFoundException::new);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Product> update(@PathParam("id") Long identifier, @Valid Product product) {
        return Panache
                .withTransaction(() -> Product.<Product>findById(identifier)
                        .onItem().ifNotNull().invoke(it -> {
                            it.name = product.name;
                            it.description = product.description;
                            it.price = product.price;
                        })
                        .onItem().ifNull().failWith(NotFoundException::new));
    }

    @GET
    @Path("search/{term}")
    public Multi<Product> search(@PathParam("term") String term) {
        return client
                .preparedQuery("SELECT id, name, description, price FROM product WHERE name ILIKE $1 OR description ILIKE $1")
                .execute(Tuple.of("%" + term + "%"))
                .toMulti().flatMap(Multi.createFrom()::iterable)
                .map(Product::from);
    }

}
