package io.quarkuscoffeeshop.coffeeshop.counter;

import io.quarkuscoffeeshop.coffeeshop.barista.api.Barista;
import io.quarkuscoffeeshop.coffeeshop.counter.api.OrderService;
import io.quarkuscoffeeshop.coffeeshop.counter.domain.OrderEventResult;
import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.quarkuscoffeeshop.utils.JsonUtil;
import io.smallrye.mutiny.Multi;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Inject
    Barista barista;

    @Inject
    EventBus eventBus;

    @Override
    public void onOrderIn(final PlaceOrderCommand placeOrderCommand) {
        logger.debug("PlaceOrderCommand received: {}", placeOrderCommand);

        OrderEventResult orderEventResult = Order.from(placeOrderCommand);

        logger.debug("sending {} web updates to notifiy the dashboard that the order is in progress", orderEventResult.getOrderUpdates().size());
        orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
            eventBus.send("web-updates", JsonUtil.toJson(orderUpdate));
            logger.debug("sent web update: {}", orderUpdate);
        });
        Multi<OrderUp> baristaOrdersUp;
        Multi<OrderUp> kitchenOrdersUp;
        if (orderEventResult.getBaristaTickets().isPresent()) {
            baristaOrdersUp = barista.batchReactively(orderEventResult.getBaristaTickets().get());
        }
        if (orderEventResult.getKitchenTickets().isPresent()) {
            if (orderEventResult.getKitchenTickets().get().size() >= 2) {
//                Multi<OrderUp> baristaOrdersUp = barista.batchReactively(orderEventResult.getBaristaTickets().get());
            }
        }

        // combine both multis
        // subscribe and send individual eventbus notifications
        // update the Order when complete
        // persist order
    }

    @Override
    public void onOrderUp(final OrderUp orderUp) {
        logger.debug("OrderUp received: {}", orderUp);
    }

    public OrderServiceImpl() {
    }
}
