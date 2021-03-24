package io.quarkuscoffeeshop.coffeeshop.counter;

import io.quarkuscoffeeshop.coffeeshop.counter.api.OrderService;
import io.quarkuscoffeeshop.coffeeshop.counter.domain.OrderEventResult;
import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderUp;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;
import io.quarkuscoffeeshop.utils.JsonUtil;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.inject.Inject;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Inject
    EventBus eventBus;

    @Override
    public void onOrderIn(final PlaceOrderCommand placeOrderCommand) {
        logger.debug("PlaceOrderCommand received: {}", placeOrderCommand);

        OrderEventResult orderEventResult = Order.from(placeOrderCommand);

        eventBus.send("web-updates", JsonUtil.toJson(placeOrderCommand));
    }

    @Override
    public void onOrderUp(final OrderUp orderUp) {
        logger.debug("OrderUp received: {}", orderUp);
    }

    public OrderServiceImpl() {
    }
}
