package io.quarkuscoffeeshop.coffeeshop.domain;

import org.hibernate.annotations.Where;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue( value = "KITCHEN" )
@Where(clause="TYPE='KITCHEN'")
public class KitchenLineItem extends LineItem {

    public KitchenLineItem(Item item, String name, BigDecimal price, ItemStatus inProgress, Order order) {
        super(item, name, price, inProgress, order);
    }

    public KitchenLineItem() {

    }
}
