package io.quarkuscoffeeshop.coffeeshop.kitchen;


import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KitchenTest {

    Kitchen kitchen;

    @BeforeEach
    public void setUp() {
        kitchen = new KitchenImpl();
    }

    @Test
    public void testMakeCroissant() {

        OrderIn orderIn = new OrderIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.CROISSANT, "Uhuru");
        OrderUp orderUp = kitchen.make(orderIn);
        assertNotNull(orderUp);
        assertNotNull(orderUp.orderId, orderIn.orderId);
    }
}
