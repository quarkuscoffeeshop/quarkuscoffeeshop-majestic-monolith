package io.quarkuscoffeeshop.coffeeshop;

import io.quarkuscoffeeshop.coffeeshop.domain.Location;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderSource;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class TestUtils {

    public static PlaceOrderCommand mockPlaceOrderCommand() {

        return new PlaceOrderCommand(
                UUID.randomUUID().toString(),
                OrderSource.COUNTER,
                Location.ATLANTA,
                Optional.of("PeskyParrot"),
                Optional.empty(),
                Optional.empty());
    }
}
