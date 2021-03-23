package io.quarkuscoffeeshop.coffeeshop.domain.commands;

import io.quarkuscoffeeshop.coffeeshop.domain.LineItem;
import io.quarkuscoffeeshop.coffeeshop.domain.Location;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderSource;

import java.time.Instant;
import java.util.List;

public class PlaceOrderCommand {

    private final String id;

    private final OrderSource orderSource;

    private final Location location;

    private final String loyaltyMemberId;

    private final List<LineItem> baristaLineLineItems;

    private final List<LineItem> kitchenLineLineItems;

    private final Instant timestamp;

    public PlaceOrderCommand(final String id, final OrderSource orderSource, final Location location, final String loyaltyMemberId, final List<LineItem> baristaLineLineItems, final List<LineItem> kitchenLineLineItems) {
        this.id = id;
        this.orderSource = orderSource;
        this.location = location;
        this.loyaltyMemberId = loyaltyMemberId;
        this.baristaLineLineItems = baristaLineLineItems;
        this.kitchenLineLineItems = kitchenLineLineItems;
        this.timestamp = Instant.now();
    }

    @Override
    public String toString() {
        return "PlaceOrderCommand{" +
                "id='" + id + '\'' +
                ", orderSource=" + orderSource +
                ", location=" + location +
                ", loyaltyMemberId='" + loyaltyMemberId + '\'' +
                ", baristaLineItems=" + baristaLineLineItems +
                ", kitchenLineItems=" + kitchenLineLineItems +
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
        if (baristaLineLineItems != null ? !baristaLineLineItems.equals(that.baristaLineLineItems) : that.baristaLineLineItems != null)
            return false;
        if (kitchenLineLineItems != null ? !kitchenLineLineItems.equals(that.kitchenLineLineItems) : that.kitchenLineLineItems != null)
            return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orderSource != null ? orderSource.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (loyaltyMemberId != null ? loyaltyMemberId.hashCode() : 0);
        result = 31 * result + (baristaLineLineItems != null ? baristaLineLineItems.hashCode() : 0);
        result = 31 * result + (kitchenLineLineItems != null ? kitchenLineLineItems.hashCode() : 0);
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

    public List<LineItem> getBaristaLineItems() {
        return baristaLineLineItems;
    }

    public List<LineItem> getKitchenLineItems() {
        return kitchenLineLineItems;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
