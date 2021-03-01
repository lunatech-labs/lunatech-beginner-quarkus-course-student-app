package com.lunatech.training.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testGreetEndpoint() {
        given()
          .when().get("/greet")
          .then()
             .statusCode(200)
             .body(containsString("Hello"));
    }

}
