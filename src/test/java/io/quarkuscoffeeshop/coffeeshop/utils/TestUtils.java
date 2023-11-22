package io.quarkuscoffeeshop.coffeeshop.utils;

import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.Location;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderSource;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.CommandItem;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.CommandType;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

public class TestUtils {

    public static PlaceOrderCommand mockPlaceOrderCommand() {

        return new PlaceOrderCommand(
                CommandType.PLACE_ORDER,
                UUID.randomUUID().toString(),
                OrderSource.COUNTER,
                Location.ATLANTA,
                "StarshipCaptain",
                Arrays.asList(new CommandItem(Item.COFFEE_BLACK, "Kirk", BigDecimal.valueOf(3.00))),
                Collections.emptyList(),
                Instant.now());
    }

}
