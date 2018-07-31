package org.pprun.hjpetstore.service;

import java.util.List;
import org.pprun.hjpetstore.dao.ItemDao;
import org.pprun.hjpetstore.domain.Item;
import org.springframework.beans.factory.annotation.Required;

/**
 * ItemService implementation.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class ItemServiceImpl implements ItemService {

    private ItemDao itemDao;

    @Required
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public List<Item> getItemListByProduct(String productNumber) {
        return this.itemDao.getItemListByProduct(productNumber);
    }

    @Override
    public Item getItem(String itemName) {
        return this.itemDao.getItem(itemName);
    }

    @Override
    public Item getItemLightWeight(String itemName) {
        return this.itemDao.getItemLightWeight(itemName);
    }

    @Override
    public boolean isItemInStock(String itemName) {
        return this.itemDao.isItemInStock(itemName);
    }
}
