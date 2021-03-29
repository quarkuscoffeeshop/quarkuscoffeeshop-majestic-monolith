package io.quarkuscoffeeshop.coffeeshop.barista;

import io.quarkuscoffeeshop.coffeeshop.barista.api.Barista;
import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.Supplier;

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

    @Override
    public Uni<OrderUp> makeReactively(OrderIn orderIn) {
        return Uni.createFrom().item(orderIn)
                .onItem()
                .transform(item -> {
                    return new OrderUp(
                            item.orderId,
                            item.lineItemId,
                            item.item,
                            item.name,
                            Instant.now(),
                            madeBy);
                })
                .onItem()
                .delayIt()
                .by(Duration.ofSeconds(5));
    }

    @Override
    public Multi<OrderUp> batchReactively(List<OrderIn> orders) {
        return Multi.createFrom().iterable(orders).onItem().transform(
                orderIn -> {
                    return prepareBeverageWithDelay(orderIn);
        });
    }

    private OrderUp prepareBeverageWithDelay(OrderIn orderIn) {
        try {
            Thread.sleep(calculateDelay(orderIn.item) * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new OrderUp(
                orderIn.orderId,
                orderIn.lineItemId,
                orderIn.item,
                orderIn.name,
                Instant.now(),
                madeBy);
    }

    private Uni<OrderUp> prepareWithDelay(OrderIn orderIn) {

        // return the completed drink
        return Uni.createFrom().item(new OrderUpSupplier(orderIn));
    }

    class OrderUpSupplier implements Supplier<OrderUp> {

        OrderUp orderUp;

        protected OrderUpSupplier(OrderIn orderIn) {
            orderUp = new OrderUp(
                    orderIn.orderId,
                    orderIn.lineItemId,
                    orderIn.item,
                    orderIn.name,
                    Instant.now(),
                    madeBy);
        }

        @Override
        public OrderUp get() {
            try {
                Thread.sleep(calculateDelay(orderUp.item) * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return orderUp;
        }
    }


    private int calculateDelay(final Item item) {
        switch (item) {
            case COFFEE_BLACK:
                return 5;
            case COFFEE_WITH_ROOM:
                return 5;
            case ESPRESSO:
                return 7;
            case ESPRESSO_DOUBLE:
                return 7;
            case CAPPUCCINO:
                return 10;
            default:
                return 3;
        }
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
