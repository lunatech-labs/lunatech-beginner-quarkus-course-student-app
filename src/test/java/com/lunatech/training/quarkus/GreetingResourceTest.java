package com.lunatech.training.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class GreetingResourceTest {

    @Inject
    @ConfigProperty(name = "greeting")
    String greeting;

    @Test
    public void testGreetEndpoint() {
        given()
          .when().get("/greet")
          .then()
             .statusCode(200)
             .body(containsString(greeting));
    }

}
