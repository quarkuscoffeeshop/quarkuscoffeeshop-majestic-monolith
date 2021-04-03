package io.quarkuscoffeeshop.coffeeshop.barista;

import io.quarkus.runtime.Startup;
import io.quarkus.vertx.ConsumeEvent;
import io.quarkuscoffeeshop.coffeeshop.barista.api.Barista;
import io.quarkuscoffeeshop.coffeeshop.barista.domain.BaristaItem;
import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.quarkuscoffeeshop.utils.JsonUtil;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.vertx.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;

import static io.quarkuscoffeeshop.coffeeshop.infrastructure.EventBusTopics.BARISTA_IN;
import static io.quarkuscoffeeshop.coffeeshop.infrastructure.EventBusTopics.ORDERS_UP;

@Startup @ApplicationScoped
public class BaristaImpl implements Barista {

    private static final Logger logger = LoggerFactory.getLogger(BaristaImpl.class);

    private final String madeBy = "Bones";

    @Inject
    EventBus eventBus;

    @ConsumeEvent(BARISTA_IN) @Blocking @Transactional
    public void onOrderIn(final Message message) {
        OrderIn orderIn = JsonUtil.fromJson(message.body().toString(), OrderIn.class);
        BaristaItem baristaItem = new BaristaItem();
        baristaItem.setItem(orderIn.item.toString());
        baristaItem.setTimeIn(Instant.now());
        logger.debug("order in : {}", orderIn);
        try {
            Thread.sleep(calculateDelay(orderIn.item));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OrderUp orderUp = new OrderUp(
                orderIn.orderId,
                orderIn.itemId,
                orderIn.item,
                orderIn.name,
                Instant.now(),
                madeBy);
        baristaItem.setTimeUp(Instant.now());
        baristaItem.persist();
        eventBus.<OrderUp>publish(ORDERS_UP, JsonUtil.toJson(orderUp));
    }

    public Uni<OrderUp> makeReactively(OrderIn orderIn) {
        return Uni.createFrom().item(orderIn)
                .onItem()
                .transform(item -> {
                    return new OrderUp(
                            item.orderId,
                            item.itemId,
                            item.item,
                            item.name,
                            Instant.now(),
                            madeBy);
                })
                .onItem()
                .delayIt()
                .by(Duration.ofSeconds(calculateDelay(orderIn.item)));
    }

    private int calculateDelay(final Item item) {
        switch (item) {
            case COFFEE_BLACK:
                return 5000;
            case COFFEE_WITH_ROOM:
                return 5000;
            case ESPRESSO:
                return 7000;
            case ESPRESSO_DOUBLE:
                return 7000;
            case CAPPUCCINO:
                return 10000;
            default:
                return 3000;
        }
    }

/*
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
*/
}
