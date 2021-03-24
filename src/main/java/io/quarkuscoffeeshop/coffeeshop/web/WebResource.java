package io.quarkuscoffeeshop.coffeeshop.web;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkuscoffeeshop.coffeeshop.counter.api.OrderService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class WebResource {

    @ConfigProperty(name="streamUrl")
    String streamUrl;

    @ConfigProperty(name="storeId")
    String storeId;

    @Inject
    OrderService orderService;

    @Inject
    Template coffeeshopTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getIndex(){

        return coffeeshopTemplate
                .data("streamUrl", streamUrl)
                .data("storeId", storeId);
    }
}
