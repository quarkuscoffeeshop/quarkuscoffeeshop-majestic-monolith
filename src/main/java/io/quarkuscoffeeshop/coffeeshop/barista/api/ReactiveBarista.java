package io.quarkuscoffeeshop.coffeeshop.barista.api;

import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.Message;

public interface ReactiveBarista {

    /**
     * Asynchronously makes a beverage corresponding to the OrderIn and returns a Uni with the OrderUp.
     *
     * @param orderIn OrderIn
     * @see OrderIn
     * @see OrderUp
     */
    public Uni<OrderUp> orderIn(final OrderIn orderIn);

    /**
     * Asynchronously makes a beverage corresponding to the OrderIn and returns a Uni with the OrderUp.
     *
     * @param orderIn OrderIn
     * @see OrderIn
     * @see OrderUp
     */
    public Uni<OrderUp> remakeOrder(final OrderIn orderIn);

    /**
     * Cancels an order corresponding to the OrderIn and returns void
     *
     * @param orderIn OrderIn
     * @see OrderIn
     */
    public Uni<Void> cancelOrder(final OrderIn orderIn);
}
