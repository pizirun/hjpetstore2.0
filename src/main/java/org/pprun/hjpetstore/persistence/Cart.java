package org.pprun.hjpetstore.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.pprun.hjpetstore.domain.Item;

import org.springframework.beans.support.PagedListHolder;

/**
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class Cart implements Serializable {

    private final Map<String, CartItem> itemMap = Collections.synchronizedMap(new HashMap<String, CartItem>());
    private final PagedListHolder<CartItem> itemList = new PagedListHolder<CartItem>();

    public Cart() {
        this.itemList.setPageSize(4);
    }

    public Iterator<CartItem> getAllCartItems() {
        return itemList.getSource().iterator();
    }

    public PagedListHolder<CartItem> getCartItemList() {
        return itemList;
    }

    public int getNumberOfItems() {
        return itemList.getSource().size();
    }

    public boolean containsItemName(String itemName) {
        return itemMap.containsKey(itemName);
    }

    public void addItem(Item item, boolean isInStock) {
        CartItem cartItem = itemMap.get(item.getItemName());
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(0);
            cartItem.setInStock(isInStock);
            itemMap.put(item.getItemName(), cartItem);
            itemList.getSource().add(cartItem);
        }
        cartItem.incrementQuantity();
    }

    public Item removeItemByName(String itemName) {
        CartItem cartItem = itemMap.remove(itemName);
        if (cartItem == null) {
            return null;
        } else {
            itemList.getSource().remove(cartItem);
            return cartItem.getItem();
        }
    }

    public void incrementQuantityByItemName(String itemName) {
        CartItem cartItem = itemMap.get(itemName);
        cartItem.incrementQuantity();
    }

    public void setQuantityByItemName(String itemName, int quantity) {
        CartItem cartItem = itemMap.get(itemName);
        cartItem.setQuantity(quantity);
    }

    public BigDecimal getSubTotal() {
        BigDecimal subTotal = BigDecimal.ZERO;
        Iterator<CartItem> items = getAllCartItems();
        while (items.hasNext()) {
            CartItem cartItem = items.next();
            Item item = cartItem.getItem();
            BigDecimal listPrice = item.getListPrice();
            int quantity = cartItem.getQuantity();
            subTotal = subTotal.add(
                    listPrice.multiply(BigDecimal.valueOf((long) quantity)));
        }

        return subTotal;
    }
}
