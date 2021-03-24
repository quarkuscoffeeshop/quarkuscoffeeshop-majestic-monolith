package io.quarkuscoffeeshop.coffeeshop.barista;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkuscoffeeshop.coffeeshop.barista.api.Barista;
import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class BaristaTest {

    @Inject
    Barista barista;

    @Test
    public void testMakeBlackCoffee() {
        OrderIn orderIn = new OrderIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.COFFEE_BLACK, "Spock");
        OrderUp orderUp = barista.make(orderIn);
        assertNotNull(orderUp);
        assertNotNull(orderUp.madeBy);
    }
}
