/*
 * Item.java
 *

 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.domain;

import java.math.BigDecimal;
import org.pprun.hjpetstore.persistence.DomainObject;

/**
 * product item contains the price and other attribute for a product, it is 1:1 associated to inventory.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class Item extends DomainObject {
    public enum ItemStatus {

        ONSTOCK("ONSTOCK"),
        OUTOFSTOCK("OUTOFSTOCK");
        private final String status;

        private ItemStatus(String status) {
            this.status = status;
        }

        public String value() {
            return status;
        }

        @Override
        public String toString() {
            return status;
        }
    }

    private String itemName;
    private BigDecimal listPrice;
    private BigDecimal unitCost;
    private ItemStatus status = ItemStatus.ONSTOCK;
    private String attr1;
    private String attr2;
    private String attr3;
    private String attr4;
    private String attr5;
    private Product product;
    private Inventory inventory;
    private Supplier supplier;

    /** Creates a new instance of Item */
    public Item() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    /**
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * @param inventory the inventory to set
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.getClass().getSimpleName());
        s.append("[");
        s.append("id=").append(id);
        s.append(", ");
        s.append("version=").append(version);
        s.append(", ");
        s.append("itemName=").append(itemName);
        s.append(", ");
        s.append("product=").append(product);
        s.append(", ");
        s.append("attr1=").append(attr1);
        s.append(", ");
        s.append("attr2=").append(attr2);
        s.append(", ");
        s.append("attr3=").append(attr3);
        s.append(", ");
        s.append("attr4=").append(attr4);
        s.append(", ");
        s.append("attr5=").append(attr5);
        s.append(", ");
        s.append("status=").append(status);
        s.append(", ");
        s.append("unitCost=").append(unitCost);
        s.append(", ");
        s.append("listPrice=").append(listPrice);
        s.append("]");

        return s.toString();
    }
}
