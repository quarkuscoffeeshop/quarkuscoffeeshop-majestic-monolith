package io.quarkuscoffeeshop.coffeeshop.reporter.domain;

import io.quarkuscoffeeshop.coffeeshop.domain.Item;
import io.quarkuscoffeeshop.coffeeshop.domain.Order;

public class OrderLineItem {

    private String name;

    private Item item;

    public OrderLineItem(String name, Item item) {
        this.name = name;
        this.item = item;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderLineItem{");
        sb.append("name='").append(name).append('\'');
        sb.append(", item=").append(item);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderLineItem that = (OrderLineItem) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return item == that.item;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (item != null ? item.hashCode() : 0);
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
