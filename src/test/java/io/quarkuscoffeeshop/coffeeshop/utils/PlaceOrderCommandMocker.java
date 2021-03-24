package io.quarkuscoffeeshop.coffeeshop.utils;

import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.LineItem;
import io.quarkuscoffeeshop.coffeeshop.domain.Location;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderSource;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.CommandItem;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlaceOrderCommandMocker {

    private String id;

    private OrderSource orderSource;

    private Location location;

    private String loyaltyMemberId;

    private List<LineItem> baristaLineItems;

    private List<LineItem> kitchenLineItems;

    private Instant timestamp;

    public PlaceOrderCommand mockPlaceOrderCommand() {
        return new PlaceOrderCommand(
                UUID.randomUUID().toString(),
                OrderSource.COUNTER,
                Location.ATLANTA,
                Optional.of("PeskyParrot"),
                Optional.of(Arrays.asList(new CommandItem(Item.COFFEE_BLACK, "Spock", BigDecimal.valueOf(3.00)))),
                Optional.empty());
    }

}
