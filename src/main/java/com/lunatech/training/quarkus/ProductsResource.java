package com.lunatech.training.quarkus;

import javax.validation.Valid;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductsResource {

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

}
