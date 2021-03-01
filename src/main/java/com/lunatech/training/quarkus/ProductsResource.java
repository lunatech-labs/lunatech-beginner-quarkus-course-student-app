package com.lunatech.training.quarkus;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/products")
public class ProductsResource {

    @Inject
    Template catalogue;

    @Inject
    Template details;

    @GET
    public TemplateInstance products() {
        return catalogue.data("products", Products.all());
    }

    @GET
    @Path("{productId}")
    public TemplateInstance details(@PathParam("productId") Long productId) {
        Product product = Products.getById(productId);
        if(product != null) {
            return details.data("product", product);
        } else {
            // Let RESTEasy handle it for us. Of course, alternatively we could also render a custom 404 page.
            throw new NotFoundException("Product not found!");
        }
    }

}
