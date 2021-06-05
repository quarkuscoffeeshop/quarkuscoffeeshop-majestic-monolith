package io.quarkuscoffeeshop.coffeeshop.reporter;

import io.quarkus.vertx.ConsumeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

import static io.quarkuscoffeeshop.coffeeshop.infrastructure.EventBusTopics.REPORTER;

@ApplicationScoped
public class ReporterServiceImpl implements ReporterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReporterServiceImpl.class);

    @Override
    @ConsumeEvent(REPORTER)
    public void orderCompleted(final String orderId) {

        LOGGER.debug("orderCompleted called for Order with id: {}", orderId);
    }
}
