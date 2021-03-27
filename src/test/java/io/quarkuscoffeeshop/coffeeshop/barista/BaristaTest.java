package io.quarkuscoffeeshop.coffeeshop.barista;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkuscoffeeshop.coffeeshop.barista.api.Barista;
import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.subscription.Cancellable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class BaristaTest {

    private static final Logger logger = LoggerFactory.getLogger(BaristaTest.class);

    @Inject
    Barista barista;

    @Test
    public void testMakeBlackCoffee() {
        OrderIn orderIn = new OrderIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.COFFEE_BLACK, "Spock");
        OrderUp orderUp = barista.make(orderIn);
        assertNotNull(orderUp);
        assertNotNull(orderUp.madeBy);
        assertEquals(orderIn.orderId, orderUp.orderId);
    }

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

    @Test
    public void testMultiBlackCoffee() {
        List<OrderIn> orders = Arrays.asList(
                new OrderIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.COFFEE_BLACK, "Spock"),
                new OrderIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.COFFEE_BLACK, "Kirk")
        );
        barista.batchReactively(orders);
    }

}
