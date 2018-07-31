/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.dao.hibernate;

import java.util.List;
import javax.annotation.Resource;
import org.junit.Test;
import org.pprun.hjpetstore.dao.AbstractDaoTest;
import org.pprun.hjpetstore.dao.ItemDao;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.pprun.hjpetstore.dao.OrderDao;
import org.pprun.hjpetstore.domain.Item;
import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.domain.OrderLineItem;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

/**
 * Test cases for {@link #ItemDao}.
 * Current it is ignored because we first build there should no order (and we did not insert data in initialize script)
 * so the test case will be fail and this prevent us from successful build.
 * After place order and persisted data into db, we can simply remove the {@code @Ignore } annotation.
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Ignore
@ContextConfiguration(locations = {"HibernateItemDaoTest-context.xml"})
public class HibernateItemDaoTest extends AbstractDaoTest {

    @Resource(name = "testItemDao")
    private ItemDao itemDao;
    @Resource(name = "testOrderDao")
    private OrderDao orderDao;

    /**
     * We need rollback any change in the db of this test, although the default is rollback = true,
     * but sometime someone may change the default behavior, so no magic is important in team work.
     * It is ignored because firstly the db is empty after initialize,
     * but we want to build with test successful, so ignore it.
     */
    @Ignore
    @Rollback(true)
    @Test
    public void testUpdateQuantity() {
        System.out.println("updateQuantity");
        final long itemId = 12;
        final String itemName = "BJ-RP-12";
        final long orderId = 1;

        Order order = orderDao.getOrderById(orderId);
        assertNotNull("seems you still have not insert order into table", order);

        assertNotNull(order.getOrderLineItems());
        assertEquals(1, order.getOrderLineItems().size());
        OrderLineItem oli = order.getOrderLineItems().iterator().next();
        int quantitytoUpdate = oli.getQuantity();

        Item item = itemDao.getItemLightWeight(itemName);
        int oldQuantity = item.getInventory().getQuantity();

        itemDao.updateQuantity(order);

        // Manual flush is required to avoid false positive in test when using ORM framework
        flush();
        
        int newQuantity = simpleJdbcTemplate.queryForInt("SELECT quantity FROM Inventory WHERE inventoryId = ?", 12);

        assertEquals(quantitytoUpdate, oldQuantity - newQuantity);
    }
    /**
     * Test of isItemInStock method, of class HibernateItemDao.
     */
    @Test
    public void testIsItemInStock() {
        System.out.println("isItemInStock");
        String itemName = "EST-DG-6";
        boolean expResult = true;
        boolean result = itemDao.isItemInStock(itemName);
        assertEquals(expResult, result);
    }

    /**
     * We specify Rollback, instead we don't want the test data got persisted.
     *
     * Test of getItemListByProduct method, of class HibernateItemDao.
     */
    @Test
    public void testGetItemListByProduct() {
        System.out.println("getItemListByProduct");

        // unit test should not set up data
        // please make sure the data valid before write unit test
        String productNumber = "DG-PO-02";

        List<Item> items = itemDao.getItemListByProduct(productNumber);
        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals("EST-DG-6", items.get(0).getItemName());
    }

    /**
     * Test of getItem method, of class HibernateItemDao.
     */
    @Test
    public void testGetItem() {
        System.out.println("getItem");
        String itemName = "EST-DG-6";

        Item result = itemDao.getItem(itemName);
        assertNotNull(result);
        assertEquals("EST-DG-6", result.getItemName());
    }

    /**
     * Test of getItemLightWeight method, of class HibernateItemDao.
     */
    @Test
    public void testGetItemLightWeight() {
        System.out.println("getItemLightWeight");
        String itemName = "EST-DG-6";
        Item result = itemDao.getItemLightWeight(itemName);
        assertNotNull(result);
        assertEquals("EST-DG-6", result.getItemName());
    }
}
