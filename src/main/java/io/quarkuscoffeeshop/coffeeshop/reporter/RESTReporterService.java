package io.quarkuscoffeeshop.coffeeshop.reporter;

import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import io.quarkuscoffeeshop.coffeeshop.reporter.domain.OrderEvent;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

@Path("/order")
@RegisterRestClient
@Produces(MediaType.APPLICATION_JSON)
public interface RESTReporterService {

    @POST
    public Response sendOrder(final OrderEvent orderEvent);
}
