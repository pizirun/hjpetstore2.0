package org.pprun.hjpetstore.dao.hibernate;

import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.pprun.hjpetstore.dao.OrderDao;
import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.domain.OrderLineItem;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * {@literal DAO} annotated {@code @Repository}is eligible for Spring DataAccessException translation.
 * @see Repository
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Repository
public class HibernateOrderDao extends HibernateDaoSupport implements OrderDao {

    private static Log log = LogFactory.getLog(HibernateOrderDao.class);
    private final static int MAX_PER_PAGE = 100;

    @Override
    @SuppressWarnings("unchecked")
    public List<Order> getOrdersByUsername(String username, int page, int max) throws DataAccessException {
        if (page < 1) {
            page = 1;
        }
        if (max < 1 || max > MAX_PER_PAGE) {
            max = MAX_PER_PAGE;
        }

        return getSession().createCriteria(Order.class)
                .createAlias("user", "u")
                .add(Restrictions.eq("u.username", username))
                .setFirstResult((page-1) * max)
                .setMaxResults(max)
                .list();
    }

    @Override
    public List<Order> getOrders(Calendar from, Calendar to) {
        return  getSession().createCriteria(Order.class)
                .setFetchMode("shipAddress", FetchMode.JOIN)
                .setFetchMode("billAddress", FetchMode.JOIN)
                .setFetchMode("orderLineItems", FetchMode.JOIN)
                .setFetchMode("orderLineItems.item", FetchMode.JOIN)
                .add(Restrictions.between("createTime", from, to))
                .addOrder(org.hibernate.criterion.Order.asc("orderStatus"))
                .list();
    }

    @Override
    public List<OrderLineItem> getOrderLineItemsByOrderId(long orderId) {
          return getSession().createCriteria(OrderLineItem.class)
                .setFetchMode("item", FetchMode.JOIN)
                .add(Restrictions.eq("id.orderId", orderId))
                .list();

    }

    /**
     * Need to fetch the User association per business logic.
     * @param id
     * @return
     * @throws DataAccessException
     */
    @Override
    public Order getOrderById(long id) throws DataAccessException {
        return (Order) getSession().createCriteria(Order.class)
                .setFetchMode("user", FetchMode.JOIN)
                .setFetchMode("shipAddress", FetchMode.JOIN)
                .setFetchMode("billAddress", FetchMode.JOIN)
                .setFetchMode("orderLineItems", FetchMode.JOIN)
                .setFetchMode("orderLineItems.item", FetchMode.JOIN)
                .setFetchMode("orderLineItems.item.product", FetchMode.JOIN)
                .add(Restrictions.eq("id", id))
                .uniqueResult();

    }

    /**
     * We we have to manually maintain the association here
     * since mapped the order.id as one of the fields of composite-key of OrderLineItem,
     *
     * This is one of another reasons that we SHOULD NOT mapping composite key.
     * @param order
     * @throws DataAccessException
     */
    @Override
    public Long insertOrder(Order order) throws DataAccessException {
        getHibernateTemplate().save(order);

        // set the order.id into the composite-key(Id) of OrderLineItem then persist it
        if (order.getOrderLineItems() != null) {
            for (OrderLineItem orderLineItem : order.getOrderLineItems()) {
                orderLineItem.getId().setOrderId(order.getId());

                getHibernateTemplate().save(orderLineItem);
            }
        }

        return order.getId();
    }

    @Override
    public void updateOrder(Order order) throws DataAccessException {
        getHibernateTemplate().update(order);
    }
}

