package io.quarkuscoffeeshop.coffeeshop.domain;

import org.hibernate.annotations.Where;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue( value = "BARISTA" )
@Where(clause="TYPE='BARISTA'")
public class BaristaLineItem extends LineItem {

    public BaristaLineItem() {
    }

    public BaristaLineItem(Item item, String name, BigDecimal price, ItemStatus inProgress, Order order) {
        super(item, name, price, inProgress, order);
    }
}
