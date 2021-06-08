package io.quarkuscoffeeshop.coffeeshop.reporter;

import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;

@Path("/v1")
@RegisterRestClient
@Produces(MediaType.APPLICATION_JSON)
public interface RESTReporterService {

    @POST
    @Path("/order")
    public Response sendOrder(final Order order);
}
