/*
 * Product.java
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.domain;

import java.util.HashSet;
import java.util.Set;
import org.pprun.hjpetstore.persistence.DomainObject;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class Product extends DomainObject {

    private String productNumber;
    private String productName;
    private String productDesc;
    private String image;
    private Category category;
    private Set<Item> items = new HashSet<Item>();

    /** Creates a new instance of Product */
    public Product() {
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public Set getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    // scaffold code for collection field
    public final void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't add a null Item.");
        }
        item.setProduct(this);
        this.getItems().add(item);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.getClass().getSimpleName());
        s.append("[");
        s.append("id=").append(id);
        s.append(", ");
        s.append("version=").append(version);
        s.append(", ");
        s.append("productName=").append(productName);
        s.append(", ");
        s.append("productDesc=").append(productDesc);
        s.append(", ");
        s.append("image=").append(image);
        s.append("]");

        return s.toString();
    }
}
