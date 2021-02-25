package io.quarkuscoffeeshop.coffeeshop.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkuscoffeeshop.coffeeshop.TestUtils;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class ApiResourceTest {

    Logger logger = LoggerFactory.getLogger(ApiResourceTest.class);

    static ObjectMapper objectMapper;

    @BeforeAll
    public static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testPlaceOrder() {

        try {

            logger.info("Testing place order");
            logger.info("PlaceOrderCommand: {}", objectMapper.writeValueAsString(TestUtils.mockPlaceOrderCommand()));

            given()
                    .contentType(MediaType.APPLICATION_JSON)
                    .when()
                    .body(objectMapper.writeValueAsString(TestUtils.mockPlaceOrderCommand()))
                    .post("/api/order")
                    .then()
                    .statusCode(202);
        } catch (JsonProcessingException e) {
            assertNull(e, "Should not throw an Exception");
        }

    }
}
