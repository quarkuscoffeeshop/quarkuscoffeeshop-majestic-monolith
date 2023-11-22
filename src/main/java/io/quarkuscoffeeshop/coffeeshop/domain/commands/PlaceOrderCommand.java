package io.quarkuscoffeeshop.coffeeshop.domain.commands;

import io.quarkuscoffeeshop.coffeeshop.domain.Location;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderSource;

import java.time.Instant;
import java.util.List;

public record PlaceOrderCommand(CommandType commandType, String id, OrderSource orderSource, Location location, String loyaltyMemberId, List<CommandItem> baristaItems, List<CommandItem> kitchenItems, Instant timestamp){

}
