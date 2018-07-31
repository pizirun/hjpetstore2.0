package org.pprun.hjpetstore.dao;

import java.util.Calendar;
import java.util.List;
import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.domain.OrderLineItem;

import org.springframework.dao.DataAccessException;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface OrderDao {

    List<Order> getOrdersByUsername(String username, int page, int max) throws DataAccessException;

    List<Order> getOrders(Calendar from, Calendar to);

    List<OrderLineItem> getOrderLineItemsByOrderId(long orderId);

    Order getOrderById(long id) throws DataAccessException;

    Long insertOrder(Order order) throws DataAccessException;

    void updateOrder(Order order) throws DataAccessException;
}
