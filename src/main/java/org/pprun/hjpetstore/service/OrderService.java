package org.pprun.hjpetstore.service;

import java.util.Calendar;
import java.util.List;
import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.domain.OrderLineItem;

/**
 * Service interface for order process.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface OrderService {

    Long insertOrder(Order order, boolean isShippingAddressRequired);

    Order getOrderById(long id);

    List<Order> getOrdersByUsername(String username, int page, int max);

    List<Order> getOrders(Calendar from, Calendar to);

    List<OrderLineItem> getOrderLineItemsByOrderId(long orderId);

    void updateOrderStatus(long orderId, Order.OrderStatus status);

    void chargeOrder(Order order, String plainCardNumber);
}
