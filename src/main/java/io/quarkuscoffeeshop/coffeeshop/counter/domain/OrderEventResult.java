package io.quarkuscoffeeshop.coffeeshop.counter.domain;

import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderTicket;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderEventResult {

    private Order order;

    private List<OrderTicket> baristaTickets;

    private List<OrderTicket> kitchenTickets;

    private List<OrderUpdate> orderUpdates;

    public OrderEventResult() {
    }

    public OrderEventResult(Order order, List<OrderTicket> baristaTickets, List<OrderTicket> kitchenTickets, List<OrderUpdate> orderUpdates) {
        this.order = order;
        this.baristaTickets = baristaTickets;
        this.kitchenTickets = kitchenTickets;
        this.orderUpdates = orderUpdates;
    }

    public void addUpdate(final OrderUpdate orderUpdate) {
        if (this.orderUpdates == null) {
            this.orderUpdates = new ArrayList<>();
        }
        this.orderUpdates.add(orderUpdate);
    }

    public void addBaristaTicket(final OrderTicket orderTicket) {
        if (this.baristaTickets == null) {
            this.baristaTickets = new ArrayList<>();
        }
        this.baristaTickets.add(orderTicket);
    }

    public void addKitchenTicket(final OrderTicket orderTicket) {
        if (this.kitchenTickets == null) {
            this.kitchenTickets = new ArrayList<>();
        }
        this.kitchenTickets.add(orderTicket);
    }

    public Optional<List<OrderTicket>> getBaristaTickets() {
        return Optional.ofNullable(baristaTickets);
    }

    public Optional<List<OrderTicket>> getKitchenTickets() {
        return Optional.ofNullable(kitchenTickets);
    }

    // Generated

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setBaristaTickets(List<OrderTicket> baristaTickets) {
        this.baristaTickets = baristaTickets;
    }

    public void setKitchenTickets(List<OrderTicket> kitchenTickets) {
        this.kitchenTickets = kitchenTickets;
    }

    public List<OrderUpdate> getOrderUpdates() {
        return orderUpdates;
    }

    public void setOrderUpdates(List<OrderUpdate> orderUpdates) {
        this.orderUpdates = orderUpdates;
    }
}
