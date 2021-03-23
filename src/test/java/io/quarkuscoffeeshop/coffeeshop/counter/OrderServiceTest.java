package io.quarkuscoffeeshop.coffeeshop.counter;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkuscoffeeshop.coffeeshop.TestUtils;
import io.quarkuscoffeeshop.coffeeshop.counter.api.OrderService;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderUp;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class OrderServiceTest {

    Logger logger = LoggerFactory.getLogger(OrderServiceTest.class);

    @Inject
    OrderService orderService;

    @Inject
    EventBus eventBus;

    @Test
    public void testOnOrderIn() {

        assertNotNull(orderService);
        orderService.onOrderIn(TestUtils.mockPlaceOrderCommand());
        eventBus.consumer("web-updates", message -> {
            logger.info("message received: {}", message);
        });
    }

}
