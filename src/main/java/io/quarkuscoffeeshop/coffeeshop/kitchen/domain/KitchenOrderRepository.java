package io.quarkuscoffeeshop.coffeeshop.kitchen.domain;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class KitchenOrderRepository implements PanacheRepository<KitchenOrder> {

    @Inject
    @DataSource("kitchen")
    AgroalDataSource kitchenDataSource;

}
