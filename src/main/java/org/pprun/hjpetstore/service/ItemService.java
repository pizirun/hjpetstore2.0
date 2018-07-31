package org.pprun.hjpetstore.service;

import java.util.List;
import org.pprun.hjpetstore.domain.Item;

/**
 * Service interface for Item process.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface ItemService {

    List<Item> getItemListByProduct(String productNumber);

    Item getItem(String itemName);

    Item getItemLightWeight(String itemName);

    boolean isItemInStock(String itemName);
}
