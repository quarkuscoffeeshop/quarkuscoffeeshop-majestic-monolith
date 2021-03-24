package io.quarkuscoffeeshop.coffeeshop.domain.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkuscoffeeshop.coffeeshop.domain.LineItem;
import io.quarkuscoffeeshop.coffeeshop.domain.Location;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderSource;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class PlaceOrderCommand {

    private final String id;

    private final OrderSource orderSource;

    private final Location location;

    private final String loyaltyMemberId;

    private final List<CommandItem> baristaLineItems;

    private final List<CommandItem> kitchenLineItems;

    private final Instant timestamp;

    public PlaceOrderCommand(
            @JsonProperty("id") final String id,
            @JsonProperty("orderSource") final OrderSource orderSource,
            @JsonProperty("location") final Location location,
            @JsonProperty("loyaltyMemberId") final Optional<String> loyaltyMemberId,
            @JsonProperty("baristaLineItems") final Optional<List<CommandItem>> baristaLineItems,
            @JsonProperty("kitchenLineItems") final Optional<List<CommandItem>> kitchenLineItems) {
        this.id = id;
        this.orderSource = orderSource;
        this.location = location;
        if (loyaltyMemberId.isPresent()) {
            this.loyaltyMemberId = loyaltyMemberId.get();
        }else{
            this.loyaltyMemberId = null;
        }
        if (baristaLineItems.isPresent()) {
            this.baristaLineItems = baristaLineItems.get();
        }else{
            this.baristaLineItems = null;
        }
        if (kitchenLineItems.isPresent()) {
            this.kitchenLineItems = kitchenLineItems.get();
        }else{
            this.kitchenLineItems = null;
        }
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

    public Optional<List<CommandItem>> getBaristaLineItems() {
        return Optional.ofNullable(baristaLineItems);
    }

    public Optional<List<CommandItem>> getKitchenLineItems() {
        return Optional.ofNullable(kitchenLineItems);
    }

    public Optional<String> getLoyaltyMemberId() {
        return Optional.ofNullable(loyaltyMemberId);
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

    public Instant getTimestamp() {
        return timestamp;
    }
}
