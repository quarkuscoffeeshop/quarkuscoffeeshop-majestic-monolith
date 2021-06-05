package io.quarkuscoffeeshop.coffeeshop.reporter;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.quarkuscoffeeshop.coffeeshop.infrastructure.EventBusTopics;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.UUID;

@QuarkusTest
public class ReporterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReporterTest.class);

    @Inject
    EventBus eventBus;

    @InjectSpy
    ReporterService reporterService;

    @Test
    public void testReportOrder() {
        String orderId = UUID.randomUUID().toString();
        eventBus.sendAndForget(EventBusTopics.REPORTER, orderId);
        Mockito.verify(reporterService, Mockito.times(1)).orderCompleted(orderId);
    }

}
