package io.quarkuscoffeeshop.coffeeshop.counter;

import io.quarkuscoffeeshop.coffeeshop.counter.api.OrderService;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderUp;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public void onOrderIn(final PlaceOrderCommand placeOrderCommand) {
        logger.debug("PlaceOrderCommand received: {}", placeOrderCommand);
    }

    @Override
    public void onOrderUp(final OrderUp orderUp) {
        logger.debug("OrderUp received: {}", orderUp);
    }

    public OrderServiceImpl() {
    }
}
