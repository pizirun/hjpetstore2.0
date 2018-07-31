/*
 * Inventory.java
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.domain;

import org.pprun.hjpetstore.persistence.DomainObject;

/**
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class Inventory extends DomainObject {

    private int quantity;
    private Item item;

    /**
     * Creates a new instance of Inventory
     */
    public Inventory() {
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

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.getClass().getSimpleName());
        s.append("[");
        s.append("id=").append(id);
        s.append(", ");
        s.append("version=").append(version);
        s.append(", ");
        s.append("item=").append(item);
        s.append(", ");
        s.append("quantity=").append(quantity);
        s.append("]");

        return s.toString();
    }
}
