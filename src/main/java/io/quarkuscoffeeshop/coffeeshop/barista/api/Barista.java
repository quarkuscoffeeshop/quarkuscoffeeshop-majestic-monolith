package io.quarkuscoffeeshop.coffeeshop.barista.api;

import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface Barista {

    public OrderUp make(final OrderIn orderIn);

    Uni<OrderUp> makeReactively(OrderIn orderIn);

    Multi<List<OrderUp>> batchReactively(List<OrderIn> orders);
}
