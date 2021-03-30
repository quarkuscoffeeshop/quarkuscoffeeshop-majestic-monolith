package io.quarkuscoffeeshop.coffeeshop.counter;

import io.quarkus.vertx.ConsumeEvent;
import io.quarkuscoffeeshop.coffeeshop.barista.api.Barista;
import io.quarkuscoffeeshop.coffeeshop.counter.api.OrderService;
import io.quarkuscoffeeshop.coffeeshop.counter.domain.OrderEventResult;
import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.quarkuscoffeeshop.coffeeshop.eventbus.EventBusTopics;
import io.quarkuscoffeeshop.utils.JsonUtil;
import io.smallrye.mutiny.Multi;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static io.quarkuscoffeeshop.coffeeshop.eventbus.EventBusTopics.*;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Inject
    EventBus eventBus;

    @Override
    /**
     * Create an Order and releated value objects and events
     * Persist the order
     * Dispatch appropriate value objects to the event bus
     * Dispatch appropriate events
     */
    public void onOrderIn(final PlaceOrderCommand placeOrderCommand) {
        logger.debug("PlaceOrderCommand received: {}", placeOrderCommand);

        OrderEventResult orderEventResult = Order.from(placeOrderCommand);
        logger.debug("OrderEventResult returned: {}", orderEventResult);

        logger.debug("sending {} web updates to notifiy the dashboard that the order is in progress", orderEventResult.getOrderUpdates().size());

        orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
            eventBus.send(WEB_UPDATES, JsonUtil.toJson(orderUpdate));
            logger.debug("sent web update: {}", orderUpdate);
        });

        if (orderEventResult.getBaristaTickets().isPresent()) {
            orderEventResult.getBaristaTickets().get().forEach(baristaTicket -> {
                eventBus.send(BARISTA_IN, JsonUtil.toJson(baristaTicket));
                logger.debug("sent to barista: {}", baristaTicket);
            });
        }

        if (orderEventResult.getKitchenTickets().isPresent()) {
            orderEventResult.getKitchenTickets().get().forEach(kitchenTicket -> {
                eventBus.send(KITCHEN_IN, JsonUtil.toJson(kitchenTicket));
                logger.debug("sent to kitchen: {}", kitchenTicket);
            });
        }

    }

    @Override
    @ConsumeEvent(ORDERS_UP)
    public void onOrderUp(final OrderUp orderUp) {
        logger.debug("OrderUp received: {}", orderUp);
    }

    public OrderServiceImpl() {
    }
}
