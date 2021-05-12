package io.quarkuscoffeeshop.coffeeshop.domain.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkuscoffeeshop.coffeeshop.domain.Location;
import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderSource;
import io.quarkuscoffeeshop.utils.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlaceOrderCommandJsonTest {


    @Test
    public void testDeserialization() {
        String json = "{\"id\":\"1e08c459-7e9e-463d-9c19-608688d1a63e\",\"orderSource\":\"COUNTER\",\"location\":\"ATLANTA\",\"loyaltyMemberId\":\"StarshipCaptain\",\"baristaLineItems\":[{\"name\":\"Jeremy\",\"item\":\"COFFEE_BLACK\",\"price\":3.50}],\"kitchenLineItems\":[]}";
        String json1 = "{\"commandType\":\"PLACE_ORDER\",\"id\":\"1e08c459-7e9e-463d-9c19-608688d1a63e\",\"orderSource\":\"COUNTER\",\"location\":\"ATLANTA\",\"loyaltyMemberId\":\"StarshipCaptain\",\"baristaLineItems\":[{\"name\":\"Jeremy\",\"item\":\"COFFEE_BLACK\",\"price\":3.50}],\"kitchenLineItems\":[]}";

        PlaceOrderCommand orderCommand = JsonUtil.fromJson(json1, PlaceOrderCommand.class);
        assertNotNull(orderCommand);
        assertEquals("1e08c459-7e9e-463d-9c19-608688d1a63e", orderCommand.getId());
        assertEquals(OrderSource.COUNTER, orderCommand.getOrderSource());
        assertEquals(Location.ATLANTA, orderCommand.getLocation());
        assertEquals("StarshipCaptain", orderCommand.getLoyaltyMemberId().get());
        assertNotNull(orderCommand.getBaristaLineItems());
    }

}
