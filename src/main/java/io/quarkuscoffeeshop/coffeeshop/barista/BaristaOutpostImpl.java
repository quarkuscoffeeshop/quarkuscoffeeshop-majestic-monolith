package io.quarkuscoffeeshop.coffeeshop.barista;

import io.quarkuscoffeeshop.coffeeshop.barista.api.Barista;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderIn;
import io.quarkuscoffeeshop.utils.JsonUtil;
import io.vertx.mutiny.core.eventbus.Message;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

public class BaristaOutpostImpl implements Barista {

    @Channel("barista-outpost")
    Emitter<OrderIn> baristaEmitter;

    @Override
    public void onOrderIn(final Message orderInMessage) {
        baristaEmitter.send(JsonUtil.fromJsonToOrderIn(orderInMessage.body().toString()));
    }

    @Override
    public void remakeOrder(Message remakeMessage) {
        baristaEmitter.send(JsonUtil.fromJsonToOrderIn(remakeMessage.body().toString()));
    }

    @Override
    public void cancelOrder(Message cancellationMessage) {
        baristaEmitter.send(JsonUtil.fromJsonToOrderIn(cancellationMessage.body().toString()));
    }

}
