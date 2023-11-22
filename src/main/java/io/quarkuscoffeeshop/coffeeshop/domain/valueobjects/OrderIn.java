package io.quarkuscoffeeshop.coffeeshop.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkuscoffeeshop.coffeeshop.domain.Item;

import java.time.Instant;

/**
 * A value object encapsulating the information sent to Barista and Kitchen
 */
public record OrderIn(String orderId, String itemId, Item item, String name, Instant timeIn) {}
