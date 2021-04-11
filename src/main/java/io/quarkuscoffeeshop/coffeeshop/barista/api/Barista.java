package io.quarkuscoffeeshop.coffeeshop.barista.api;

import io.vertx.mutiny.core.eventbus.Message;

/**
 * Message driven interface for the Barista implementation
 */
public interface Barista {

    /**
     * Makes a beverage corresponding to the OrderIn and publishes an OrderUp event to the Event Bus.
     * Expects a Message with a JSON payload corresponding to OrderIn value object
     *
     * @param orderInMessage Message
     * @see io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn
     * @see Message
     */
    public void onOrderIn(final Message orderInMessage);

    /**
     * Makes a beverage corresponding to the OrderIn and publishes an OrderUp event to the Event Bus.
     * Expects a Message with a JSON payload corresponding to OrderRemakeIn value object
     *
     * @param remakeMessage Message
     * @see io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn
     * @see Message
     */
    public void remakeOrder(final Message remakeMessage);

    /**
     * Cancels an in-process Order if possible.
     * Expects a Message with a JSON payload corresponding to OrderIn value object
     *
     * @param cancellationMessage Message
     * @see Message
     * @see io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn
     */
    public void cancelOrder(final Message cancellationMessage);
}
