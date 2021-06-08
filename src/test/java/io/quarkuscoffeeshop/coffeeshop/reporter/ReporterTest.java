package io.quarkuscoffeeshop.coffeeshop.reporter;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import io.quarkuscoffeeshop.coffeeshop.infrastructure.EventBusTopics;
import io.quarkuscoffeeshop.coffeeshop.infrastructure.OrderRepository;
import io.quarkuscoffeeshop.coffeeshop.utils.PostgresTestResource;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.UUID;

import static io.quarkuscoffeeshop.coffeeshop.utils.TestUtils.mockOrder;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

@QuarkusTest
@QuarkusTestResource(PostgresTestResource.class) @QuarkusTestResource(WiremockIngress.class)
public class ReporterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReporterTest.class);

    @Inject
    EventBus eventBus;

    @InjectSpy
    ReporterService reporterService;

    @Inject
    OrderRepository orderRepository;

    String orderId;

    @BeforeEach
    @Transactional
    public void setUp() {

        Order order = mockOrder();
        orderRepository.persist(order);
        orderId = order.getOrderId();
    }

    @Test
    public void testReportOrder() {

        eventBus.sendAndForget(EventBusTopics.REPORTER, orderId);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            assertNull(e);
        }
        Mockito.verify(reporterService, Mockito.times(1)).orderCompleted(orderId);
    }

}
