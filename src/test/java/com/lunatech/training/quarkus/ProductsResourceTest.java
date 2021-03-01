package com.lunatech.training.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
public class ProductsResourceTest {

    @Test
    public void testProductsEndpoint() {
        given()
                .when().get("/products")
                .then()
                .statusCode(200)
                .body(containsString("Chair"));
    }

    @Test
    public void testProductDetailsEndpoint() {
        given()
                .when().get("/products/1")
                .then()
                .statusCode(200)
                .body(containsString("A metal frame chair, with oak seat"));
    }

    @Test
    public void testProductDetailsEndpointNotFound() {
        given()
                .when().get("/products/11")
                .then()
                .statusCode(404);
    }

}
