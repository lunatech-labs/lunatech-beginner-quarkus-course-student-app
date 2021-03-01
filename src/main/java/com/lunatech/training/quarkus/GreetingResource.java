package com.lunatech.training.quarkus;

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

    @GET
    @Path("hello/{subject}")
    public String hello(@PathParam("subject") String subject) {
        return "Hello, " + subject;
    }

}
