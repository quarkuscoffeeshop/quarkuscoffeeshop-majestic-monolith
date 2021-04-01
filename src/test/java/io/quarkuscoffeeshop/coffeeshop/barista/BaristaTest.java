package io.quarkuscoffeeshop.coffeeshop.barista;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkuscoffeeshop.coffeeshop.barista.api.Barista;
import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUpdate;
import io.smallrye.mutiny.Multi;
import io.quarkuscoffeeshop.utils.JsonUtil;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static io.quarkuscoffeeshop.utils.JsonUtil.fromJson;
import static io.quarkuscoffeeshop.utils.JsonUtil.fromJsonToOrderUp;
import static org.awaitility.Awaitility.await;
import static io.quarkuscoffeeshop.coffeeshop.infrastructure.EventBusTopics.*;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class BaristaTest {

    private static final Logger logger = LoggerFactory.getLogger(BaristaTest.class);

    @Inject
    Barista barista;

    @Inject
    EventBus eventBus;

    List<OrderUp> orderUpMessages = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        eventBus.consumer(WEB_UPDATES)
                .handler(message -> {
                    logger.info("message received: {}", message.body().toString());
                    assertNotNull(message);
                    orderUpMessages.add(fromJsonToOrderUp(message.body().toString()));
                });
    }

    @Test
    public void testMakeBlackCoffee() {
        OrderIn orderIn = new OrderIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.COFFEE_BLACK, "Spock");

        eventBus.publish(ORDERS_UP, JsonUtil.toJson(orderIn));

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            assertNull(e);
        }

        assertEquals(1, orderUpMessages.size());
    }

/*
    @Test
    public void testReactiveMakeBlackCoffee() {
        OrderIn orderIn = new OrderIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.COFFEE_BLACK, "Spock");
        boolean success = true;
        barista.makeReactively(orderIn)
                .subscribe()
                .with(result -> {
                    logger.info("orderUp: {}", result);
                    assertNotNull(result);
                    assertEquals(orderIn.orderId, result.orderId);
        });
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            assertNull(e);
        }
    }
*/

/*
    @Test
    public void testMultiBlackCoffee() {
        List<OrderIn> orders = Arrays.asList(
                new OrderIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.COFFEE_BLACK, "Spock"),
                new OrderIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.COFFEE_BLACK, "Kirk")
        );
        Multi<OrderUp> results = barista.batchReactively(orders);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            assertNull(e);
        }
        assertEquals(2, results.subscribe().asStream().count());
    }
*/

/*
    @Test
    public void testMultiBlackCoffeeTakesAtLeast5Seconds() {
        List<OrderIn> orders = Arrays.asList(
                new OrderIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.COFFEE_BLACK, "Spock"),
                new OrderIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.COFFEE_BLACK, "Kirk")
        );
        Multi<OrderUp> results = barista.batchReactively(orders);
        logger.debug("now we sleep");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            assertNull(e);
        }
        logger.debug("now we wake");
        Stream<OrderUp> ordersUp = results.subscribe().asStream();
//        assertEquals(2, ordersUp.count());
        assertTrue(ordersUp.allMatch(orderUp -> {
            return orderUp.item == Item.COFFEE_BLACK;
        }));
    }
*/
}
