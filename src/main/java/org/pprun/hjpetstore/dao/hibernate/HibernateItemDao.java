package org.pprun.hjpetstore.dao.hibernate;

import java.util.List;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.pprun.hjpetstore.dao.ItemDao;
import org.pprun.hjpetstore.domain.Inventory;
import org.pprun.hjpetstore.domain.Item;
import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.domain.OrderLineItem;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * {@literal DAO} annotated {@code @Repository}is eligible for Spring DataAccessException translation.
 * @see Repository
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Repository
public class HibernateItemDao extends HibernateDaoSupport implements ItemDao {

    @Override
    public void updateQuantity(Order order) throws DataAccessException {
        for (OrderLineItem lineItem : order.getOrderLineItems()) {
            Item item = lineItem.getItem();
            Integer increment = lineItem.getQuantity();

            Inventory inventory = (Inventory) getSession().
                    createCriteria(Inventory.class).
                    createAlias("item", "i").
                    add(Restrictions.eq("i.itemName", item.getItemName())).
                    uniqueResult();

            if (inventory != null) {
                inventory.setQuantity(inventory.getQuantity() - increment);
                getHibernateTemplate().update(inventory);
            }
        }
    }

    @Override
    public boolean isItemInStock(String itemName) throws DataAccessException {
        Integer quantity = (Integer) getSession().
                createCriteria(Item.class).
                createAlias("inventory", "i").
                add(Restrictions.eq("itemName", itemName)).
                setProjection(Projections.property("i.quantity")).
                uniqueResult();

        return quantity != null && quantity.intValue() > 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Item> getItemListByProduct(String productNumber) throws DataAccessException {
        return getSession().
                createCriteria(Item.class).
                createAlias("product", "p").
                add(Restrictions.eq("p.productNumber", productNumber)).
                list();
    }

    /**
     * This is a heavyweight method to retrieve Item and associated product.
     * A lightweight method {@link #getItemLightWeight(java.lang.String)} only retrieve item and product association.
     * This method is model of a long business conversation that will need to access all or most of the associations of
     * {@link Item}.
     * <p>
     * Earlier fetch the association to avoid to utilize {@literal open session view} pattern.
     * @param itemName
     * @return
     * @throws DataAccessException
     */
    @Override
    @SuppressWarnings("unchecked")
    public Item getItem(String itemName) throws DataAccessException {
        return (Item) getSession().
                createCriteria(Item.class).
                setFetchMode("product", FetchMode.JOIN).
                setFetchMode("supplier", FetchMode.JOIN).
                setFetchMode("inventory", FetchMode.JOIN).
                add(Restrictions.eq("itemName", itemName)).
                uniqueResult();
    }

    /**
     * This is a lightweight method to retrieve Item and associated product and supplier.
     * unlike the heavyweight one {@link #getItem(java.lang.String)}, it pulls out most all associated domain objects
     * for view rendering or a long business conversation.
     *
     * @param itemName
     * @return
     * @throws DataAccessException
     */
    @Override
    @SuppressWarnings("unchecked")
    public Item getItemLightWeight(String itemName) throws DataAccessException {
        return (Item) getSession().
                createCriteria(Item.class).
                setFetchMode("product", FetchMode.JOIN).
                setFetchMode("inventory", FetchMode.JOIN).
                add(Restrictions.eq("itemName", itemName)).
                uniqueResult();
    }
//    @Override
//    public int getItemQuantity(String itemName) throws DataAccessException {
//         Integer quantity = (Integer)getSession().createCriteria(Item.class)
//                .createAlias("inventory", "i")
//                .add(Restrictions.eq("itemName", itemName))
//                .setProjection(Projections.property("i.quantity"))
//                .uniqueResult();
//
//         return quantity == null ? 0 : quantity.intValue();
//    }
}
