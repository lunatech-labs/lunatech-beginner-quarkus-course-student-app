package com.lunatech.training.quarkus;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class GreetingResource {

    @GET
    @Path("greet")
    public String greet() {
        return "Hello, Quarkians!";
    }

    @Inject
    Template greet;

    @GET
    @Path("hello/{subject}")
    public TemplateInstance hello(@PathParam("subject") String subject) {
        return greet.data("subject", subject);
    }

}
