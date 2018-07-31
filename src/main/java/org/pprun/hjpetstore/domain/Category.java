/*
 * Category.java
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
public class Category extends DomainObject {

    private String categoryName;
    private String categoryDesc;
    private Banner banner;
    private Set<Product> products = new HashSet<Product>();

    /** Creates a new instance of Category */
    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    // scaffolding code for collection field
    public final void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Can't add a null product.");
        }
        product.setCategory(this);
        this.getProducts().add(product);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.getClass().getSimpleName());
        s.append("[");
        s.append("id=").append(id);
        s.append(", ");
        s.append("version=").append(version);
        s.append(", ");
        s.append("categoryName=").append(categoryName);
        s.append(", ");
        s.append("categoryDesc=").append(categoryDesc);
        s.append("]");

        return s.toString();
    }
}
