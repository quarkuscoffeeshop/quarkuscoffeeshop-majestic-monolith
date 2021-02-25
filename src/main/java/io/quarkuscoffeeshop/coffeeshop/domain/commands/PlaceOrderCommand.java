package io.quarkuscoffeeshop.coffeeshop.domain.commands;

import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.Location;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderSource;

import java.time.Instant;
import java.util.List;

public class PlaceOrderCommand {

    private final String id;

    private final OrderSource orderSource;

    private final Location location;

    private final String loyaltyMemberId;

    private final List<Item> baristaLineItems;

    private final List<Item> kitchenLineItems;

    private final Instant timestamp;

    public PlaceOrderCommand(final String id, final OrderSource orderSource, final Location location, final String loyaltyMemberId, final List<Item> baristaLineItems, final List<Item> kitchenLineItems) {
        this.id = id;
        this.orderSource = orderSource;
        this.location = location;
        this.loyaltyMemberId = loyaltyMemberId;
        this.baristaLineItems = baristaLineItems;
        this.kitchenLineItems = kitchenLineItems;
        this.timestamp = Instant.now();
    }

    @Override
    public String toString() {
        return "PlaceOrderCommand{" +
                "id='" + id + '\'' +
                ", orderSource=" + orderSource +
                ", location=" + location +
                ", loyaltyMemberId='" + loyaltyMemberId + '\'' +
                ", baristaLineItems=" + baristaLineItems +
                ", kitchenLineItems=" + kitchenLineItems +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceOrderCommand that = (PlaceOrderCommand) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (orderSource != that.orderSource) return false;
        if (location != that.location) return false;
        if (loyaltyMemberId != null ? !loyaltyMemberId.equals(that.loyaltyMemberId) : that.loyaltyMemberId != null)
            return false;
        if (baristaLineItems != null ? !baristaLineItems.equals(that.baristaLineItems) : that.baristaLineItems != null)
            return false;
        if (kitchenLineItems != null ? !kitchenLineItems.equals(that.kitchenLineItems) : that.kitchenLineItems != null)
            return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orderSource != null ? orderSource.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (loyaltyMemberId != null ? loyaltyMemberId.hashCode() : 0);
        result = 31 * result + (baristaLineItems != null ? baristaLineItems.hashCode() : 0);
        result = 31 * result + (kitchenLineItems != null ? kitchenLineItems.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

    public String getId() {
        return id;
    }

    public OrderSource getOrderSource() {
        return orderSource;
    }

    public Location getLocation() {
        return location;
    }

    public String getLoyaltyMemberId() {
        return loyaltyMemberId;
    }

    public List<Item> getBaristaLineItems() {
        return baristaLineItems;
    }

    public List<Item> getKitchenLineItems() {
        return kitchenLineItems;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
