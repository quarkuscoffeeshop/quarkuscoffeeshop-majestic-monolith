package io.quarkuscoffeeshop.coffeeshop.web;

import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Startup
@Path("/dashboard")
public class DashboardEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(DashboardEndpoint.class);

/*
    @Inject
    @Channel("web-updates")
    @Broadcast
    Publisher<String> updater;
*/
    @Inject
    EventBus eventBus;

    @GET
    @Path("/stream")
    @Produces(MediaType.SERVER_SENT_EVENTS) // denotes that server side events (SSE) will be produced
//    @RestSseElementType("text/plain") // denotes that the contained data, within this SSE, is just regular text/plain data
    public Multi<String> dashboardStream() {

        return eventBus.<String>consumer("web-updates")
                .toMulti()
                .map(Message::body);
                //.toMulti().onItem().transform(item -> item.body());
    }

}
