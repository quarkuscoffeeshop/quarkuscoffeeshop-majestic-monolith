package io.quarkuscoffeeshop.coffeeshop.web;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public class QuteResource {

    @Inject
    Template coffeeshop;

//    @GET
//    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return coffeeshop.data("quarks", null);
    }

}
