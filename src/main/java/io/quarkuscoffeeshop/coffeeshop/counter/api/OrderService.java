package io.quarkuscoffeeshop.coffeeshop.counter.api;

import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;

/**
 * Coordinates order functions
 */
public interface OrderService {

    public void onOrderIn(final PlaceOrderCommand placeOrderCommand);

    public void onOrderUp(final OrderUp orderUp);
}
