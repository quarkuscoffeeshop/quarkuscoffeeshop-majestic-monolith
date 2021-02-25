package io.quarkuscoffeeshop.coffeeshop.web;


import io.quarkuscoffeeshop.coffeeshop.counter.api.OrderService;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ApiResource {

    private static final Logger logger = LoggerFactory.getLogger(ApiResource.class);

    @Inject
    OrderService orderService;

    @POST
    @Path("/order")
    @Transactional
    public Response placeOrder(PlaceOrderCommand placeOrderCommand) {

        logger.info("PlaceOrderCommand received: {}", placeOrderCommand);
        orderService.onOrderIn(placeOrderCommand);
        return Response.accepted().build();
    }
}
