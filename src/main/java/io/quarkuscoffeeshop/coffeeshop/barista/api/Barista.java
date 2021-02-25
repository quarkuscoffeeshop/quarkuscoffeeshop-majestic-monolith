package io.quarkuscoffeeshop.coffeeshop.barista.api;

import io.quarkuscoffeeshop.coffeeshop.domain.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderUp;

public interface Barista {

    public OrderUp make(final OrderIn orderIn);
}
