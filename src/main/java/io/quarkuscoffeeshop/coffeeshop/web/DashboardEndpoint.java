package io.quarkuscoffeeshop.coffeeshop.web;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static io.quarkuscoffeeshop.coffeeshop.infrastructure.EventBusTopics.WEB_UPDATES;

@Path("/dashboard")
public class DashboardEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardEndpoint.class);

    @Inject
    EventBus eventBus;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<String> dashboardStream() {

        return eventBus.<String>consumer(WEB_UPDATES)
                .toMulti()
                .map(Message::body);
    }

}
