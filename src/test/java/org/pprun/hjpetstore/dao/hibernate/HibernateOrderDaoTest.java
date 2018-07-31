/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.dao.hibernate;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.junit.Ignore;
import org.junit.Test;
import org.pprun.common.util.CalendarUtil;
import static org.junit.Assert.*;
import org.pprun.hjpetstore.dao.AbstractDaoTest;
import org.pprun.hjpetstore.dao.OrderDao;
import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.domain.OrderLineItem;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test cases for {@link #OrderDao}
 * Current it is ignored because we first build there should no order (and we did not insert data in initialize script)
 * so the test case will be fail and this prevent us from successful build.
 * After place order and persisted data into db, we can simply remove the {@code @Ignore } annotation.
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Ignore
@ContextConfiguration(locations = {"HibernateOrderDaoTest-context.xml"})
public class HibernateOrderDaoTest extends AbstractDaoTest {

    @Resource(name = "testOrderDao")
    OrderDao orderDao;

    /**
     * Test of getOrdersByUsername method, of class HibernateOrderDao.
     */
    @Test
    public void testGetOrdersByUsername() {
        System.out.println("getOrdersByUsername");
        String username = "j2ee";
        int page = 0;
        int max = 0;

        List<Order> result = orderDao.getOrdersByUsername(username, page, max);
        assertNotNull(result);
        assertEquals(username, result.get(0).getUser().getUsername());
    }

    /**
     * Test of getOrders method, of class HibernateOrderDao.
     */
    @Test
    public void testGetOrders() {
        System.out.println("getOrders");
        Calendar from = CalendarUtil.startOfDayYesterday();
        Calendar to = CalendarUtil.truncateDay(Calendar.getInstance());

        List<Order> result = orderDao.getOrders(from, to);
        assertNotNull(result);

        for (Order order : result) {
            // assert the date should be < startOfToday
            int cmp = order.getCreateTime().compareTo(CalendarUtil.truncateDay(Calendar.getInstance()));
            assertTrue("the createDate of orders should be before today ", cmp < 0);

            Set<OrderLineItem> orderLineItems = order.getOrderLineItems();

            // @TODO XXX: Add programmatic support for ending transactions with the
            // TestContext Framework.
            // it has been supported in 2.5 but gone in 3.0, Oooooooooooooops!
            //
            // Check lazy loading, by ending the transaction:
            // endTransaction();
            //
            // Now orderLineItems are "disconnected" from the session / data store.
            // We might need to touch this collection because it was mapped lazy loading
            // in mapping files
            // without this checking, if the implementation in Dao did not pull out
            // the association by earlier fetch, when passing domain object 'Order'
            // to page render or marshaller of remote call,
            // while trying to access this collection 'orderLineItems',
            // the famous 'LazyInitializationException' will be thrown out
            // Aha :)

            assertNotNull(orderLineItems);
            for (OrderLineItem orderLineItem : orderLineItems) {
                assertEquals(order.getId(), orderLineItem.getOrder().getId());
            }

        }
    }
    
    
// TODO: implements the following test case when got time
//
//    /**
//     * Test of getOrderLineItemsByOrderId method, of class HibernateOrderDao.
//     */
//    @Test
//    public void testGetOrderLineItemsByOrderId() {
//        System.out.println("getOrderLineItemsByOrderId");
//        long orderId = 0L;
//        HibernateOrderDao instance = new HibernateOrderDao();
//        List expResult = null;
//        List result = instance.getOrderLineItemsByOrderId(orderId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getOrderById method, of class HibernateOrderDao.
//     */
//    @Test
//    public void testGetOrderById() {
//        System.out.println("getOrderById");
//        long id = 0L;
//        HibernateOrderDao instance = new HibernateOrderDao();
//        Order expResult = null;
//        Order result = instance.getOrderById(id);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of insertOrder method, of class HibernateOrderDao.
//     */
//    @Test
//    public void testInsertOrder() {
//        System.out.println("insertOrder");
//        Order order = null;
//        HibernateOrderDao instance = new HibernateOrderDao();
//        Long expResult = null;
//        Long result = instance.insertOrder(order);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of updateOrder method, of class HibernateOrderDao.
//     */
//    @Test
//    public void testUpdateOrder() {
//        System.out.println("updateOrder");
//        Order order = null;
//        HibernateOrderDao instance = new HibernateOrderDao();
//        instance.updateOrder(order);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
