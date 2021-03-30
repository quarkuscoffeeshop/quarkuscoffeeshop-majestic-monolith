package io.quarkuscoffeeshop.coffeeshop.barista.api;

import io.vertx.mutiny.core.eventbus.Message;

public interface Barista {

    public void onOrderIn(final Message orderIn);}
