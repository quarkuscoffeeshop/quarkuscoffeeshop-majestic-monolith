package io.quarkuscoffeeshop.coffeeshop.reporter.domain;

import io.quarkuscoffeeshop.coffeeshop.domain.LineItem;
import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import io.quarkuscoffeeshop.coffeeshop.domain.OrderSource;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OrderEvent {

    private EventType eventType;

    private String orderId;

    private OrderSource orderSource;

    private String loyaltyMemberId;

    private Instant timestamp;

    private List<OrderLineItem> baristaLineItems;

    private List<OrderLineItem> kitchenLineItems;

    public static OrderEvent from(final Order order, final EventType eventType) {
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.eventType = eventType;
        orderEvent.orderId = order.getOrderId();
        orderEvent.orderSource = order.getOrderSource();
        orderEvent.timestamp = order.getTimestamp();
        if (order.getLoyaltyMemberId().isPresent()) {
            orderEvent.loyaltyMemberId = order.getLoyaltyMemberId().get();
        }
        if (order.getBaristaLineItems().isPresent()) {
            orderEvent.baristaLineItems = new ArrayList<>(order.getBaristaLineItems().get().size());
            order.getBaristaLineItems().get().forEach(baristaLineItem -> {
                orderEvent.getBaristaLineItems().add(new OrderLineItem(baristaLineItem.getName(), baristaLineItem.getItem()));
            });
        }
        if (order.getKitchenLineItems().isPresent()) {
            orderEvent.kitchenLineItems = new ArrayList(order.getKitchenLineItems().get().size());
            order.getKitchenLineItems().get().forEach(k -> {
                orderEvent.getKitchenLineItems().add(new OrderLineItem(k.getName(), k.getItem()));
            });
        }
        return orderEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderEvent that = (OrderEvent) o;

        if (eventType != that.eventType) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (orderSource != that.orderSource) return false;
        if (loyaltyMemberId != null ? !loyaltyMemberId.equals(that.loyaltyMemberId) : that.loyaltyMemberId != null)
            return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (baristaLineItems != null ? !baristaLineItems.equals(that.baristaLineItems) : that.baristaLineItems != null)
            return false;
        return kitchenLineItems != null ? kitchenLineItems.equals(that.kitchenLineItems) : that.kitchenLineItems == null;
    }

    @Override
    public int hashCode() {
        int result = eventType != null ? eventType.hashCode() : 0;
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        result = 31 * result + (orderSource != null ? orderSource.hashCode() : 0);
        result = 31 * result + (loyaltyMemberId != null ? loyaltyMemberId.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (baristaLineItems != null ? baristaLineItems.hashCode() : 0);
        result = 31 * result + (kitchenLineItems != null ? kitchenLineItems.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderEvent{");
        sb.append("eventType=").append(eventType);
        sb.append(", orderId='").append(orderId).append('\'');
        sb.append(", orderSource=").append(orderSource);
        sb.append(", loyaltyMemberId='").append(loyaltyMemberId).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append(", baristaLineItems=").append(baristaLineItems);
        sb.append(", kitchenLineItems=").append(kitchenLineItems);
        sb.append('}');
        return sb.toString();
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderSource getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(OrderSource orderSource) {
        this.orderSource = orderSource;
    }

    public String getLoyaltyMemberId() {
        return loyaltyMemberId;
    }

    public void setLoyaltyMemberId(String loyaltyMemberId) {
        this.loyaltyMemberId = loyaltyMemberId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public List<OrderLineItem> getBaristaLineItems() {
        return baristaLineItems;
    }

    public void setBaristaLineItems(List<OrderLineItem> baristaLineItems) {
        this.baristaLineItems = baristaLineItems;
    }

    public List<OrderLineItem> getKitchenLineItems() {
        return kitchenLineItems;
    }

    public void setKitchenLineItems(List<OrderLineItem> kitchenLineItems) {
        this.kitchenLineItems = kitchenLineItems;
    }
}
