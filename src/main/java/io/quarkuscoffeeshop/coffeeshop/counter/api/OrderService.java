package io.quarkuscoffeeshop.coffeeshop.counter.api;

import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.Message;

/**
 * Coordinates order functions
 */
public interface OrderService {

    public Uni<Order> onOrderIn(final PlaceOrderCommand placeOrderCommand);

    public void onOrderUp(final Message message);
}
