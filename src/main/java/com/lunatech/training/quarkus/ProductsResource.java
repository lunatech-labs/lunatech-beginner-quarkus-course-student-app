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
    @Path("{productId}")
    public Uni<Product> details(@PathParam("productId") Long productId) {
        return Product.<Product>findById(productId).map(p -> {
            if (p == null) {
                throw new NotFoundException();
            } else {
                return p;
            }
        });
    }

    @PUT
    @Path("{productId}")
    public Uni<Product> update(@PathParam("productId") Long productId, @Valid Product product) {
        return Product.<Product>findById(productId).flatMap(p -> {
            if(p == null) {
                return Uni.createFrom().failure(new NotFoundException());
            } else {
                p.name = product.name;
                p.description = product.description;
                p.price = product.price;
                return p.persistAndFlush().map(__ -> p);
            }
        });
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
