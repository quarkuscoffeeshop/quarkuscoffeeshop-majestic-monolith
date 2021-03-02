package io.quarkuscoffeeshop.homeoffice.infrastructrue;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/homeoffice/api")
public class ApiResource {

    @GET
    public Response helloWorld() {
        return Response.ok().build();
    }
}
