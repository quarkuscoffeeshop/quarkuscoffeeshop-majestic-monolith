package io.quarkuscoffeeshop.coffeeshop.domain;

import io.quarkuscoffeeshop.coffeeshop.counter.domain.OrderEventResult;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "Orders")
public class Order {

    @Transient
    static Logger logger = LoggerFactory.getLogger(Order.class);

    @Id
    @Column(nullable = false, unique = true, name = "order_id")
    private String orderId;

    @Enumerated(EnumType.STRING)
    private OrderSource orderSource;

    private String loyaltyMemberId;

    private Instant timestamp;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private Location location;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private List<LineItem> baristaLineItems;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private List<LineItem> kitchenLineItems;

    protected Order() {

    }

    private Order(String orderId) {
        this.orderId = orderId;
        this.timestamp = Instant.now();
    }

    /**
     * Creates a new Order and corresponding updates from a PlaceOrderCommand
     *
     * @param placeOrderCommand
     * @return OrderEventResult
     * @see OrderEventResult
     */
    public static OrderEventResult from(final PlaceOrderCommand placeOrderCommand) {


        // build the order from the PlaceOrderCommand
        Order order = new Order(placeOrderCommand.getId());
        order.setOrderSource(placeOrderCommand.getOrderSource());
        order.setLocation(placeOrderCommand.getLocation());
        order.setTimestamp(placeOrderCommand.getTimestamp());
        order.setOrderStatus(OrderStatus.IN_PROGRESS);

        // create the return value
        OrderEventResult orderEventResult = new OrderEventResult();

        if (placeOrderCommand.getBaristaItems().isPresent()) {
            logger.debug("createOrderFromCommand adding beverages {}", placeOrderCommand.getBaristaItems().get().size());

            logger.debug("adding Barista LineItems");
            placeOrderCommand.getBaristaItems().get().forEach(commandItem -> {
                logger.debug("createOrderFromCommand adding baristaItem from {}", commandItem.toString());
                LineItem lineItem = new LineItem(commandItem.item, commandItem.name, commandItem.price, ItemStatus.IN_PROGRESS, order);
                order.addBaristaLineItem(lineItem);
                logger.debug("added LineItem: {}", order.getBaristaLineItems().get().size());
                orderEventResult.addBaristaTicket(new OrderIn(order.getOrderId(), lineItem.getItemId(), lineItem.getItem(), lineItem.getName()));
                logger.debug("Added Barista Ticket to OrderEventResult: {}", orderEventResult.getBaristaTickets().get().size());
                orderEventResult.addUpdate(new OrderUpdate(order.getOrderId(), lineItem.getItemId(), lineItem.getName(), lineItem.getItem(), OrderStatus.IN_PROGRESS));
                logger.debug("Added Order Update to OrderEventResult: ", orderEventResult.getOrderUpdates().size());
            });
        }
        logger.debug("adding Kitchen LineItems");
        if (placeOrderCommand.getKitchenItems().isPresent()) {
            logger.debug("createOrderFromCommand adding kitchenOrders {}", placeOrderCommand.getKitchenItems().get().size());
            placeOrderCommand.getKitchenItems().get().forEach(commandItem -> {
                logger.debug("createOrderFromCommand adding kitchenItem from {}", commandItem.toString());
                LineItem lineItem = new LineItem(commandItem.item, commandItem.name, commandItem.price, ItemStatus.IN_PROGRESS, order);
                order.addKitchenLineItem(lineItem);
                orderEventResult.addKitchenTicket(new OrderIn(order.getOrderId(), lineItem.getItemId(), lineItem.getItem(), lineItem.getName()));
                orderEventResult.addUpdate(new OrderUpdate(order.getOrderId(), lineItem.getItemId(), lineItem.getName(), lineItem.getItem(), OrderStatus.IN_PROGRESS));
            });
        }

        orderEventResult.setOrder(order);
        logger.debug("Added Order and OrderCreatedEvent to OrderEventResult: {}", orderEventResult);

        logger.debug("returning {}", orderEventResult);
        return orderEventResult;
    }

    /**
     * Convenience method to prevent Null Pointer Exceptions
     *
     * @param lineItem
     */
    public void addBaristaLineItem(LineItem lineItem) {
        if (this.baristaLineItems == null) {
            this.baristaLineItems = new ArrayList<>();
        }
        lineItem.setOrder(this);
        this.baristaLineItems.add(lineItem);
    }

    /**
     * Convenience method to prevent Null Pointer Exceptions
     *
     * @param lineItem
     */
    public void addKitchenLineItem(LineItem lineItem) {
        if (this.kitchenLineItems == null) {
            this.kitchenLineItems = new ArrayList<>();
        }
        lineItem.setOrder(this);
        this.kitchenLineItems.add(lineItem);
    }


    /**
     * Not all Orders come from Loyalty Members so this returns an Optional<String> of the Loyalty Member's Id
     *
     * @return Optional<String>
     */
    public Optional<String> getLoyaltyMemberId() {
        return Optional.ofNullable(loyaltyMemberId);
    }

    /**
     * Most but not all Orders have barista items so this returns an Optional<List<LineItem>
     *
     * @return Optional<String>
     */
    public Optional<List<LineItem>> getBaristaLineItems() {
        return Optional.ofNullable(baristaLineItems);
    }

    /**
     * Many orders do not contain kitchen items so this returns an Optional<List<LineItem>
     *
     * @return Optional<String>
     */
    public Optional<List<LineItem>> getKitchenLineItems() {
        return Optional.ofNullable(kitchenLineItems);
    }


    // Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != null ? !orderId.equals(order.orderId) : order.orderId != null) return false;
        if (orderSource != order.orderSource) return false;
        if (loyaltyMemberId != null ? !loyaltyMemberId.equals(order.loyaltyMemberId) : order.loyaltyMemberId != null)
            return false;
        if (timestamp != null ? !timestamp.equals(order.timestamp) : order.timestamp != null) return false;
        if (orderStatus != order.orderStatus) return false;
        if (location != order.location) return false;
        if (baristaLineItems != null ? !baristaLineItems.equals(order.baristaLineItems) : order.baristaLineItems != null)
            return false;
        return kitchenLineItems != null ? kitchenLineItems.equals(order.kitchenLineItems) : order.kitchenLineItems == null;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (orderSource != null ? orderSource.hashCode() : 0);
        result = 31 * result + (loyaltyMemberId != null ? loyaltyMemberId.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (baristaLineItems != null ? baristaLineItems.hashCode() : 0);
        result = 31 * result + (kitchenLineItems != null ? kitchenLineItems.hashCode() : 0);
        return result;
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

    public void setLoyaltyMemberId(String loyaltyMemberId) {
        this.loyaltyMemberId = loyaltyMemberId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setBaristaLineItems(List<LineItem> baristaLineLineItems) {
        this.baristaLineItems = baristaLineLineItems;
    }

    public void setKitchenLineItems(List<LineItem> kitchenLineLineItems) {
        this.kitchenLineItems = kitchenLineLineItems;
    }
}
