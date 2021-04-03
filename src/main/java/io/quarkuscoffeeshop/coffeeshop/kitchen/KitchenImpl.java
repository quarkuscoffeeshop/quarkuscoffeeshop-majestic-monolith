package io.quarkuscoffeeshop.coffeeshop.kitchen;

import io.quarkus.vertx.ConsumeEvent;
import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.quarkuscoffeeshop.coffeeshop.kitchen.domain.KitchenOrder;
import io.quarkuscoffeeshop.coffeeshop.kitchen.domain.KitchenOrderRepository;
import io.quarkuscoffeeshop.utils.JsonUtil;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;

import static io.quarkuscoffeeshop.coffeeshop.infrastructure.EventBusTopics.*;

@ApplicationScoped
public class KitchenImpl implements Kitchen {

    private static final Logger logger = LoggerFactory.getLogger(KitchenImpl.class);

    String madeBy = "Sulu";

    @Inject
    EventBus eventBus;

    @Inject
    KitchenOrderRepository kitchenOrderRepository;

    @ConsumeEvent(KITCHEN_IN)
    @Blocking
    @Transactional
    public void onOrderIn(final Message message) {
        OrderIn orderIn = JsonUtil.fromJson(message.body().toString(), OrderIn.class);
        KitchenOrder kitchenOrder = new KitchenOrder(orderIn.orderId, orderIn.item, Instant.now());
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
        kitchenOrder.setTimeUp(Instant.now());
        kitchenOrderRepository.persist(kitchenOrder);
        eventBus.<OrderUp>publish(ORDERS_UP, JsonUtil.toJson(orderUp));

    }

    public Uni<OrderUp> make(OrderIn orderIn) {
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
