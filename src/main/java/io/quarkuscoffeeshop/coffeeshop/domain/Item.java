package io.quarkuscoffeeshop.coffeeshop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;

@JsonIgnoreProperties(value = { "orderId" })
@Entity
@Table(name = "items")
public class Item  extends PanacheEntityBase {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    Order order;

    @Id
    @Column(nullable = false, unique = true)
    private String itemId;

    @Enumerated(EnumType.STRING)
    private Item item;

    private String name;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ItemStatus lineItemStatus;
}
