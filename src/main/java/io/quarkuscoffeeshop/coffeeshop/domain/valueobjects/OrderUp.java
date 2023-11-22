package io.quarkuscoffeeshop.coffeeshop.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkuscoffeeshop.coffeeshop.domain.Item;

import java.time.Instant;

public record OrderUp(String orderId, String itemId, Item item, String name, String madeBy, Instant timeUp){}
