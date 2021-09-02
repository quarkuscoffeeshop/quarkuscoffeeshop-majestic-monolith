package io.quarkuscoffeeshop.coffeeshop.counter;

import io.quarkus.vertx.ConsumeEvent;
import io.quarkuscoffeeshop.coffeeshop.counter.api.OrderService;
import io.quarkuscoffeeshop.coffeeshop.counter.domain.OrderEventResult;
import io.quarkuscoffeeshop.coffeeshop.domain.Order;
import io.quarkuscoffeeshop.coffeeshop.domain.commands.PlaceOrderCommand;
import io.quarkuscoffeeshop.coffeeshop.domain.valueobjects.OrderUp;
import io.quarkuscoffeeshop.coffeeshop.infrastructure.OrderRepository;
import io.quarkuscoffeeshop.utils.JsonUtil;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.vertx.core.eventbus.EventBus;
import io.vertx.mutiny.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.concurrent.CompletableFuture;

import static io.quarkuscoffeeshop.coffeeshop.infrastructure.EventBusTopics.*;
import static io.quarkuscoffeeshop.utils.JsonUtil.fromJsonToOrderUp;
import static io.quarkuscoffeeshop.utils.JsonUtil.toJson;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Inject
    EventBus eventBus;

    @Inject
    OrderRepository orderRepository;

    public OrderServiceImpl() {
    }

    @Transactional
    public void onOrderIn(final PlaceOrderCommand placeOrderCommand) {
        LOGGER.debug("PlaceOrderCommand received: {}", placeOrderCommand);
        OrderEventResult orderEventResult = Order.from(placeOrderCommand);

        orderRepository.persist(orderEventResult.getOrder());

        orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
            eventBus.publish(WEB_UPDATES, JsonUtil.toJson(orderUpdate));
            LOGGER.debug("sent web update: {}", orderUpdate);
        });
        if (orderEventResult.getBaristaTickets().isPresent()) {
            orderEventResult.getBaristaTickets().get().forEach(baristaTicket -> {
                eventBus.send(BARISTA_IN, JsonUtil.toJson(baristaTicket));
                LOGGER.debug("sent to barista: {}", baristaTicket);
            });
        }
        if (orderEventResult.getKitchenTickets().isPresent()) {
            orderEventResult.getKitchenTickets().get().forEach(kitchenTicket -> {
                eventBus.send(KITCHEN_IN, JsonUtil.toJson(kitchenTicket));
                LOGGER.debug("sent to kitchen: {}", kitchenTicket);
            });
        }
    }

//    public Uni<Order> onOrderInOriginal(final PlaceOrderCommand placeOrderCommand) {
//        LOGGER.debug("PlaceOrderCommand received: {}", placeOrderCommand);
//        return Uni.createFrom().item(placeOrderCommand)
//                .map(command -> {
//                    return Order.from(placeOrderCommand);
//                })
//                .map(orderEventResult -> {
//                    LOGGER.debug("OrderEventResult: {}", orderEventResult);
//                    saveOrder(orderEventResult.getOrder());
//                    if (orderEventResult.getBaristaTickets().isPresent()) {
//                        orderEventResult.getBaristaTickets().get().forEach(baristaTicket -> {
//                            eventBus.send(BARISTA_IN, JsonUtil.toJson(baristaTicket));
//                            LOGGER.debug("sent to barista: {}", baristaTicket);
//                        });
//                    }
//                    if (orderEventResult.getKitchenTickets().isPresent()) {
//                        orderEventResult.getKitchenTickets().get().forEach(kitchenTicket -> {
//                            eventBus.send(KITCHEN_IN, JsonUtil.toJson(kitchenTicket));
//                            LOGGER.debug("sent to kitchen: {}", kitchenTicket);
//                        });
//                    }
//                    orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
//                        eventBus.send(WEB_UPDATES, JsonUtil.toJson(orderUpdate));
//                        LOGGER.debug("sent web update: {}", orderUpdate);
//                    });
//                    LOGGER.debug("order persiste", orderEventResult.getOrder());
//                    return orderEventResult.getOrder();
//                });
//    }

    private void saveOrder(final Order order) {
        orderRepository.persistAndFlush(order);
    }

    @Override
    @ConsumeEvent(ORDERS_UP)
    @Blocking
    @Transactional
    public void onOrderUp(final Message message) {

        LOGGER.debug("order up message: {}", message.body());
        OrderUp orderUp = fromJsonToOrderUp(message.body().toString());

        Order order = orderRepository.findById(orderUp.orderId);
        OrderEventResult orderEventResult = order.apply(orderUp);
        orderRepository.persistAndFlush(order);
        orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
            eventBus.publish(WEB_UPDATES, JsonUtil.toJson(orderUpdate));
        });

    }

//    private Uni<OrderEventResult> applyOrderUp3(final OrderUp orderUp) {
//        return Uni.createFrom().item(orderUp)
//                .map(o -> {
//                    Order order = orderRepository.findById(o.orderId);
//                    OrderEventResult orderEventResult = order.apply(orderUp);
//                    LOGGER.debug("After applying OrderUp event Order: {}", orderEventResult.getOrder());
//                    orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
//                        eventBus.send(WEB_UPDATES, toJson(orderUpdate));
//                        LOGGER.debug("sent web update: {}", orderUpdate);
//                    });
////                    orderRepository.persistAndFlush(order);
//                    LOGGER.debug("persisted order: {}", orderEventResult.getOrder());
//                    return orderEventResult;
//                });
//    }

//    @Override @Transactional
//    public void onOrderIn(final PlaceOrderCommand placeOrderCommand) {
//        Order.from(placeOrderCommand).subscribe().with(orderEventResult -> {
//            logger.debug("OrderEventResult returned: {}", orderEventResult);
//
//            logger.debug("sending {} web updates to notifiy the dashboard that the order is in progress", orderEventResult.getOrderUpdates().size());
//
//            orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
//                eventBus.send(WEB_UPDATES, JsonUtil.toJson(orderUpdate));
//                logger.debug("sent web update: {}", orderUpdate);
//            });
//
//            if (orderEventResult.getBaristaTickets().isPresent()) {
//                orderEventResult.getBaristaTickets().get().forEach(baristaTicket -> {
//                    eventBus.send(BARISTA_IN, JsonUtil.toJson(baristaTicket));
//                    logger.debug("sent to barista: {}", baristaTicket);
//                });
//            }
//
//            if (orderEventResult.getKitchenTickets().isPresent()) {
//                orderEventResult.getKitchenTickets().get().forEach(kitchenTicket -> {
//                    eventBus.send(KITCHEN_IN, JsonUtil.toJson(kitchenTicket));
//                    logger.debug("sent to kitchen: {}", kitchenTicket);
//                });
//            }
//
//            orderRepository.persist(orderEventResult.getOrder());
//        });
//    }

/*
    */
/**
     * Create an Order and releated value objects and events
     * Persist the order
     * Dispatch appropriate value objects to the event bus
     * Dispatch appropriate events
     *//*

    @Transactional
    public void onOrderInOriginal(final PlaceOrderCommand placeOrderCommand) {
        logger.debug("PlaceOrderCommand received: {}", placeOrderCommand);

        OrderEventResult orderEventResult = Order.from(placeOrderCommand);
        logger.debug("OrderEventResult returned: {}", orderEventResult);

        logger.debug("sending {} web updates to notifiy the dashboard that the order is in progress", orderEventResult.getOrderUpdates().size());

        orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
            eventBus.send(WEB_UPDATES, toJson(orderUpdate));
            logger.debug("sent web update: {}", orderUpdate);
        });

<<<<<<< Updated upstream
=======
        if (orderEventResult.getBaristaTickets().isPresent()) {
            orderEventResult.getBaristaTickets().get().forEach(baristaTicket -> {
                eventBus.send(BARISTA_IN, toJson(baristaTicket));
                logger.debug("sent to barista: {}", baristaTicket);
            });
        }

        if (orderEventResult.getKitchenTickets().isPresent()) {
            orderEventResult.getKitchenTickets().get().forEach(kitchenTicket -> {
                eventBus.send(KITCHEN_IN, toJson(kitchenTicket));
                logger.debug("sent to kitchen: {}", kitchenTicket);
            });
        }

        //orderRepository.persist(orderEventResult.getOrder());
    }

    @Override @Transactional
    public void onOrderIn(final PlaceOrderCommand placeOrderCommand) {
        Uni.createFrom().completionStage(applyOrderIn(placeOrderCommand)).subscribe();
    }

    private CompletableFuture<OrderEventResult> applyOrderIn(final PlaceOrderCommand placeOrderCommand) {
        return CompletableFuture.supplyAsync(() -> {
            logger.debug("PlaceOrderCommand received: {}", placeOrderCommand);

            OrderEventResult orderEventResult = Order.from(placeOrderCommand);
            logger.debug("OrderEventResult returned: {}", orderEventResult);

            logger.debug("sending {} web updates to notifiy the dashboard that the order is in progress", orderEventResult.getOrderUpdates().size());

            orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
                eventBus.send(WEB_UPDATES, toJson(orderUpdate));
                logger.debug("sent web update: {}", orderUpdate);
            });

            if (orderEventResult.getBaristaTickets().isPresent()) {
                orderEventResult.getBaristaTickets().get().forEach(baristaTicket -> {
                    eventBus.send(BARISTA_IN, toJson(baristaTicket));
                    logger.debug("sent to barista: {}", baristaTicket);
                });
            }

            if (orderEventResult.getKitchenTickets().isPresent()) {
                orderEventResult.getKitchenTickets().get().forEach(kitchenTicket -> {
                    eventBus.send(KITCHEN_IN, toJson(kitchenTicket));
                    logger.debug("sent to kitchen: {}", kitchenTicket);
                });
            }

            orderRepository.persist(orderEventResult.getOrder());
            logger.debug("persisted order: {}", orderEventResult.getOrder());
            return orderEventResult;
        });
    }
*/

//    @Override
//    @ConsumeEvent(ORDERS_UP)
//    @Transactional
//    public void onOrderUp(final Message message) {
//
//        logger.debug("message received: {}", message.body());
//
///*
//        Uni.createFrom().item(message.body().toString())
//                .map(body -> fromJsonToOrderUp(body))
//                .map(orderUp -> applyOrderUp2(orderUp))
//                .map(orderEventResult -> {
//                    logger.debug("After applying OrderUp event Order: {}", orderEventResult.getOrder());
//                    orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
//                        eventBus.send(WEB_UPDATES, toJson(orderUpdate));
//                        logger.debug("sent web update: {}", orderUpdate);
//                    });
//                    return orderEventResult.getOrder();
//                })
//                .invoke(order -> {
//                    System.out.println(order.toString());
//                })
//                .onFailure().invoke(err -> logger.error(err.getMessage()))
//                .runSubscriptionOn(Infrastructure.getDefaultWorkerPool())
//                .subscribe();
//*/
//;
//
//
////        Uni.createFrom().completionStage(applyOrderUp(fromJson(message.body().toString(), OrderUp.class)))
////                .emitOn(Infrastructure.getDefaultExecutor())
////                .subscribe();/*
////        Order.apply(orderUp).onItem().invoke(orderEventResult -> {
////            logger.debug("After applying OrderUp event Order: {}", orderEventResult.getOrder());
////            orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
////                eventBus.send(WEB_UPDATES, JsonUtil.toJson(orderUpdate));
////                logger.debug("sent web update: {}", orderUpdate);
////            });
////            orderRepository.persist(orderEventResult.getOrder());
////            logger.debug("persisted order: {}", orderEventResult.getOrder());
////        }).subscribe();
////*/
//    }

    private void persistOrder(final Order order) {

    }

    private OrderEventResult applyOrderUp2(final OrderUp orderUp) {
        Order order = orderRepository.findById(orderUp.orderId);
        OrderEventResult orderEventResult = order.apply(orderUp);
        return orderEventResult;
    }

    @Transactional
    private CompletableFuture<OrderEventResult> applyOrderUp(final OrderUp orderUp) {
        return CompletableFuture.supplyAsync(() -> {
            Order order = orderRepository.findById(orderUp.orderId);
            OrderEventResult orderEventResult = order.apply(orderUp);
            LOGGER.debug("After applying OrderUp event Order: {}", orderEventResult.getOrder());
            orderEventResult.getOrderUpdates().forEach(orderUpdate -> {
                eventBus.send(WEB_UPDATES, toJson(orderUpdate));
                LOGGER.debug("sent web update: {}", orderUpdate);
            });
            orderRepository.persist(orderEventResult.getOrder());
            LOGGER.debug("persisted order: {}", orderEventResult.getOrder());
            return orderEventResult;
        });
    }

}
