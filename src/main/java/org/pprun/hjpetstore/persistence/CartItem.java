package org.pprun.hjpetstore.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import org.pprun.hjpetstore.domain.Item;

/**
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class CartItem implements Serializable {

    private Item item;
    private int quantity;
    private boolean inStock;

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        if (item != null) {
            return item.getListPrice().multiply(
                    BigDecimal.valueOf(quantity));
        } else {
            return BigDecimal.ZERO;
        }
    }

    public void incrementQuantity() {
        quantity++;
    }
}
