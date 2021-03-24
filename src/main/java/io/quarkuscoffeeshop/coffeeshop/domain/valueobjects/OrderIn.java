package io.quarkuscoffeeshop.coffeeshop.domain.valueobjects;

import io.quarkuscoffeeshop.coffeeshop.domain.Item;

import java.time.Instant;

/**
 * A value object encapsulating the information sent to Barista and Kitchen
 */
public class OrderIn {

    public final String orderId;

    public final String lineItemId;

    public final Item item;

    public final String name;

    public final Instant timestamp;

    public OrderIn(String orderId, String lineItemId, Item item, String name) {
        this.orderId = orderId;
        this.lineItemId = lineItemId;
        this.item = item;
        this.name = name;
        this.timestamp = Instant.now();
    }

    @Override
    public String toString() {
        return "OrderTicket{" +
                "orderId='" + orderId + '\'' +
                ", lineItemId='" + lineItemId + '\'' +
                ", item=" + item +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderIn that = (OrderIn) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (lineItemId != null ? !lineItemId.equals(that.lineItemId) : that.lineItemId != null) return false;
        if (item != that.item) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (lineItemId != null ? lineItemId.hashCode() : 0);
        result = 31 * result + (item != null ? item.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
