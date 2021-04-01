package io.quarkuscoffeeshop.coffeeshop.kitchen;

import io.quarkus.vertx.ConsumeEvent;
import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.quarkuscoffeeshop.utils.JsonUtil;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;

import static io.quarkuscoffeeshop.coffeeshop.infrastructure.EventBusTopics.*;

@ApplicationScoped
public class KitchenImpl implements Kitchen {

    private static final Logger logger = LoggerFactory.getLogger(KitchenImpl.class);

    String madeBy = "Sulu";

    @Inject
    EventBus eventBus;

    @ConsumeEvent(KITCHEN_IN)
    public void onOrderIn(final Message message) {
        OrderIn orderIn = JsonUtil.fromJson(message.body().toString(), OrderIn.class);
        logger.debug("order in : {}", orderIn);
        make(orderIn).subscribe().with(result -> {
            eventBus.<OrderUp>publish(ORDERS_UP, JsonUtil.toJson(result));
            logger.debug("sent order up: {}", result);
        });

    }

    public Uni<OrderUp> make(OrderIn orderIn) {
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
                .by(Duration.ofSeconds(calculateDelay(orderIn.item)));
    }

    private int calculateDelay(final Item item) {
        switch (item) {
            case CROISSANT:
                return 7;
            case CROISSANT_CHOCOLATE:
                return 7;
            case CAKEPOP:
                return 5;
            case MUFFIN:
                return 7;
            default:
                return 3;
        }
    }
}
