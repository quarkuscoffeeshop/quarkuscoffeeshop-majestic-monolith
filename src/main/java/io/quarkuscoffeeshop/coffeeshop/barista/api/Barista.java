package io.quarkuscoffeeshop.coffeeshop.barista.api;

import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;

public interface Barista {

    public OrderUp make(final OrderIn orderIn);
}
