package io.quarkuscoffeeshop.coffeeshop.counter.api;

import io.quarkuscoffeeshop.coffeeshop.domain.OrderUp;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;

import javax.enterprise.context.ApplicationScoped;

public interface OrderService {

    public void onOrderIn(final PlaceOrderCommand placeOrderCommand);

    public void onOrderUp(final OrderUp orderUp);
}
