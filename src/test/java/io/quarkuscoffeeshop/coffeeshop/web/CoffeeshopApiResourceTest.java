package io.quarkuscoffeeshop.coffeeshop.web;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkuscoffeeshop.coffeeshop.utils.TestUtils;
import io.quarkuscoffeeshop.utils.JsonUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class CoffeeshopApiResourceTest {

    Logger logger = LoggerFactory.getLogger(CoffeeshopApiResourceTest.class);

    @Test
    public void testPlaceOrder() {

        logger.info("Testing place order");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .when()
                .body(JsonUtil.toJson(TestUtils.mockPlaceOrderCommand()))
                .then()
                .statusCode(202);

    }

    @Test
    public void testPlaceOrderForSingleCroissant() {

    }
}
