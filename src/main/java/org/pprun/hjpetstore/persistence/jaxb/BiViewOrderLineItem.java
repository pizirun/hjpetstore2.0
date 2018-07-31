/*
 * OrderLineItem.java
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.persistence.jaxb;

import java.math.BigDecimal;

/**
 * This is the view of orderLineItem for Business Intelligence (BI).
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class BiViewOrderLineItem {

    private int lineNumber;
    private int quantity;
    private BigDecimal unitPrice;
    private String itemName;

    public BiViewOrderLineItem() {
    }

    public BiViewOrderLineItem(int lineNumber, int quantity, BigDecimal unitPrice, String itemName) {
        this.lineNumber = lineNumber;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.itemName = itemName;
    }

    /**
     * @return the lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * @param lineNumber the lineNumber to set
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the unitPrice
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "BiViewOrderLineItem{" + "lineNumber=" + lineNumber + "quantity=" + quantity + "unitPrice=" + unitPrice + "itemName=" + itemName + '}';
    }
}
