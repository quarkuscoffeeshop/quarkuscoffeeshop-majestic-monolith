package io.quarkuscoffeeshop.coffeeshop.barista;

import io.quarkuscoffeeshop.coffeeshop.barista.api.Barista;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;

@ApplicationScoped
public class BaristaImpl implements Barista {

    private static final Logger logger = LoggerFactory.getLogger(BaristaImpl.class);

    private final String madeBy = "Bones";

    @Override
    public OrderUp make(OrderIn orderIn) {
        int delay;
        switch (orderIn.item) {
            case COFFEE_BLACK:
                delay = 5;
                break;
            case COFFEE_WITH_ROOM:
                delay = 5;
                break;
            case ESPRESSO:
                delay = 7;
                break;
            case ESPRESSO_DOUBLE:
                delay = 7;
                break;
            case CAPPUCCINO:
                delay = 10;
                break;
            default:
                delay = 3;
                break;
        };
        return prepare(orderIn, delay);
    }

    private OrderUp prepare(OrderIn orderIn, int delay) {
        // model the barista's time making the drink
        try {
            Thread.sleep(delay * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // return the completed drink
        return new OrderUp(
                orderIn.orderId,
                orderIn.lineItemId,
                orderIn.item,
                orderIn.name,
                Instant.now(),
                madeBy);
    }
}
