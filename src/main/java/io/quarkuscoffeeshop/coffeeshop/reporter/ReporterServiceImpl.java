package io.quarkuscoffeeshop.coffeeshop.reporter;

import io.quarkus.vertx.ConsumeEvent;
import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import io.quarkuscoffeeshop.coffeeshop.infrastructure.OrderRepository;
import io.quarkuscoffeeshop.coffeeshop.reporter.domain.EventType;
import io.quarkuscoffeeshop.coffeeshop.reporter.domain.OrderEvent;
import io.quarkuscoffeeshop.utils.JsonUtil;
import io.smallrye.common.annotation.Blocking;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import static io.quarkuscoffeeshop.coffeeshop.infrastructure.EventBusTopics.REPORTER;

@ApplicationScoped
public class ReporterServiceImpl implements ReporterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReporterServiceImpl.class);

    @Inject
    OrderRepository orderRepository;

    @Inject @RestClient
    RESTReporterService restService;

    @Override
    @ConsumeEvent(REPORTER)
    @Blocking
    @Transactional
    public void orderCompleted(final String orderId) {

        LOGGER.debug("orderCompleted called for Order with id: {}", orderId);
        Order order = orderRepository.findById(orderId);
        OrderEvent orderEvent = OrderEvent.from(order, EventType.OrderCreated);
        LOGGER.debug("Sending Order to HomeOffice: {}", JsonUtil.toJson(orderEvent));
        try {
            restService.sendOrder(orderEvent);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        LOGGER.debug("Order sent");
    }

}
