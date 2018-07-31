/*
 * Supplier.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.domain;

import java.util.HashSet;
import java.util.Set;
import org.pprun.hjpetstore.persistence.DomainObject;

/**
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class Supplier extends DomainObject {
    public enum SupplierStatus {

        ACTIVE("ACTIVE"),
        INACTIVE("INACTIVE");
        private final String status;

        private SupplierStatus(String status) {
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

    private String supplierName;
    private SupplierStatus status = SupplierStatus.ACTIVE;
    private User user;

    private Set<Item> items = new HashSet<Item>();

    /** Creates a new instance of Supplier */
    public Supplier() {
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public SupplierStatus getStatus() {
        return status;
    }

    public void setStatus(SupplierStatus status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
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
        item.setSupplier(this);
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
        s.append("supplierName=").append(supplierName);
        s.append("]");

        return s.toString();
    }
}
