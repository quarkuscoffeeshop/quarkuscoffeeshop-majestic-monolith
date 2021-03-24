package io.quarkuscoffeeshop.coffeeshop.domain.valueobjects;

import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderStatus;

public class OrderUpdate {

    public final String orderId;

    public final String itemId;

    public final String name;

    public final Item item;

    public final OrderStatus status;

    public final String madeBy;

    public OrderUpdate(final String orderId, final String itemId, final String name, final Item item, final OrderStatus status) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.name = name;
        this.item = item;
        this.status = status;
        this.madeBy = null;
    }

    public OrderUpdate(final String orderId, final String itemId, final String name, final Item item, final OrderStatus status, final String madeBy) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.name = name;
        this.item = item;
        this.status = status;
        this.madeBy = madeBy;
    }
}
