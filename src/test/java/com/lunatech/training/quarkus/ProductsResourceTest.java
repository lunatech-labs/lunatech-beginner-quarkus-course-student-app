package com.lunatech.training.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ProductsResourceTest {

    @Test
    public void testProductsEndpoint() {
        given()
                .when().get("/products")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON).extract().response();
    }

    @Test
    public void testProductDetailsEndpoint() {
        Response response = given()
                .when().get("/products/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON).extract().response();

        assertEquals("Chair", response.jsonPath().getString("name"));
    }

    @Test
    public void testProductDetailsEndpointNotFound() {
        given()
                .when().get("/products/11")
                .then()
                .statusCode(404);
    }

}
