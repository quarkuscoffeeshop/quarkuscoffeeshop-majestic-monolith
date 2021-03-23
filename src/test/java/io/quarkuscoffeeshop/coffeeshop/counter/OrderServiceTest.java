package io.quarkuscoffeeshop.coffeeshop.counter;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkuscoffeeshop.coffeeshop.TestUtils;
import io.quarkuscoffeeshop.coffeeshop.counter.api.OrderService;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;
import io.quarkuscoffeeshop.utils.JsonUtil;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class OrderServiceTest {

    Logger logger = LoggerFactory.getLogger(OrderServiceTest.class);

    @Inject
    OrderService orderService;

    @Inject
    EventBus eventBus;

    @Test
    /**
     * Verify that the appropriate message is sent on the Event Bus
     */
    public void testOnOrderIn() {

        eventBus.consumer("web-updates", message -> {
            logger.info("message received: {}", message.body().toString());
        }).toString().equalsIgnoreCase("{id='1d4748de-1839-41dd-8569-4a2173b45c57', orderSource=COUNTER, location=ATLANTA, loyaltyMemberId='PeskyParrot', baristaLineItems=null, kitchenLineItems=null, timestamp=2021-03-23T19:32:24.318864Z}");
        assertNotNull(orderService);
        orderService.onOrderIn(TestUtils.mockPlaceOrderCommand());
    }
}
