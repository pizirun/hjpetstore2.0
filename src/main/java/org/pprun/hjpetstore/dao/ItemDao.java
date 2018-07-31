package org.pprun.hjpetstore.dao;

import java.util.List;
import org.pprun.hjpetstore.domain.Item;
import org.pprun.hjpetstore.domain.Order;

import org.springframework.dao.DataAccessException;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface ItemDao {

    public void updateQuantity(Order order) throws DataAccessException;

    boolean isItemInStock(String itemName) throws DataAccessException;

    List<Item> getItemListByProduct(String productNumber) throws DataAccessException;

    /**
     * This is a heavyweight method to retrieve Item and associated product.
     * A lightweight method {@link #getItemLightWeight(java.lang.String)} only retrieve item and product association.
     * This method is model of a long business conversation that will need to access all or most of the associations of
     * {@link Item}.
     *
     * @param itemName
     * @return
     * @throws DataAccessException
     */
    Item getItem(String itemName) throws DataAccessException;

    /**
     * This is a lightweight method to retrieve Item and associated product.
     * unlike the heavyweight one {@link #getItem(java.lang.String)}, it pulls out most all associated domain objects
     * for view rendering or a long business conversation.
     * 
     * @param itemName
     * @return
     * @throws DataAccessException
     */
    Item getItemLightWeight(String itemName) throws DataAccessException;
//  int getItemQuantity(String itemName) throws DataAccessException;
}
