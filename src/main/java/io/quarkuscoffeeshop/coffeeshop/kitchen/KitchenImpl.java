package io.quarkuscoffeeshop.coffeeshop.kitchen;

import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.Instant;

@ApplicationScoped
public class KitchenImpl implements Kitchen {

    private static final Logger logger = LoggerFactory.getLogger(KitchenImpl.class);

    String madeBy = "Sulu";

    @Override
    public OrderUp make(OrderIn orderIn) {

        logger.debug("making: {}" + orderIn.item);

        int delay;
        switch (orderIn.item) {
            case CROISSANT:
                delay = 5;
                break;
            case CAKEPOP:
                delay = 3;
                break;
            case CROISSANT_CHOCOLATE:
                delay = 5;
                break;
            case MUFFIN:
                delay = 7;
                break;
            default:
                delay = 10;
                break;
        };

        return prepare(orderIn, delay);
    }

    private OrderUp prepare(OrderIn orderIn, int delay) {
        // model the time to make the order
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
