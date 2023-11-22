package io.quarkuscoffeeshop.coffeeshop.domain.valueobjects;

import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderStatus;

public record OrderUpdate(String orderId, String itemId, String name, Item item, OrderStatus status, String madeBy){}
