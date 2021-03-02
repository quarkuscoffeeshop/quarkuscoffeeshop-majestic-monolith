package io.quarkuscoffeeshop.customerloyalty.infrastructure;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/customerloyalty/api")
public class CustomerLoyaltyApiResource {

        @GET
        public Response helloWorld() {
            return Response.ok().build();
        }
}
