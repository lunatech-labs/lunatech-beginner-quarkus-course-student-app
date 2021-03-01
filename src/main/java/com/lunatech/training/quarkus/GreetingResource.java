package com.lunatech.training.quarkus;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class GreetingResource {

    private final SubjectBean subjectBean;
    private final String greeting;
    private final Template greet;

    @Inject
    public GreetingResource(SubjectBean subjectBean, @ConfigProperty(name = "greeting") String greeting, Template greet) {
        this.subjectBean = subjectBean;
        this.greeting = greeting;
        this.greet = greet;
    }

    void startup(@Observes StartupEvent event) {
        System.out.println("GreetingResource Ready!");
    }

    @GET
    @Path("greet")
    public String greet() {
        return greeting + ", " + subjectBean.subject();
    }

    @GET
    @Path("hello/{subject}")
    public TemplateInstance hello(@PathParam("subject") String subject) {
        return greet.data("subject", subject);
    }

}
