package io.quarkuscoffeeshop.coffeeshop.domain.valueobjects;

import io.quarkuscoffeeshop.coffeeshop.domain.Item;

import java.time.Instant;

public class OrderUp extends OrderIn{

    public final String madeBy;

    public OrderUp(String orderId, String lineItemId, Item item, String name, Instant now, String madeBy) {
        super(orderId, lineItemId, item, name);
        this.madeBy = madeBy;
    }

    @Override
    public String toString() {
        return "OrderUp{" +
                "orderId='" + orderId + '\'' +
                ", lineItemId='" + lineItemId + '\'' +
                ", item=" + item +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                ", madeBy='" + madeBy + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderUp orderUp = (OrderUp) o;

        return madeBy != null ? madeBy.equals(orderUp.madeBy) : orderUp.madeBy == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (madeBy != null ? madeBy.hashCode() : 0);
        return result;
    }
}
