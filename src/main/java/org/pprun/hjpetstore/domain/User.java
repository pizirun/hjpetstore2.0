/*
 * User.java
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.domain;

import java.util.HashSet;
import java.util.Set;
import org.pprun.hjpetstore.domain.Address.AddressStatus;
import org.pprun.hjpetstore.persistence.DomainObject;

/**
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class User extends DomainObject {

    public enum UserStatus {

        /** pending to confirm */
        PENDING("PENDING"),
        ACTIVE("ACTIVE"),
        DELETED("DELETED");
        private final String status;

        private UserStatus(String status) {
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
    private String username;
    private String password;
    // for public web service call
    private String apiKey;
    private String secretKey;
    private String firstname;
    private String lastname;
    private String email;
    private UserStatus status = UserStatus.ACTIVE;
    private String phone;
    private String langPreference;
    private Category favCategory;
    // whether or not Include My Favorite Product List in cart pages
    private boolean displayMylist;
    private boolean displayBanner;
    private Set<Role> roles = new HashSet<Role>();
    // please don't use List to avoid the duplicated records retruned when eaglier fetch
    private Set<Address> addresses = new HashSet<Address>();
    private Set<Order> orders = new HashSet<Order>();

    /**
     * Creates a new instance of User
     */
    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLangPreference() {
        return langPreference;
    }

    public void setLangPreference(String langPreference) {
        this.langPreference = langPreference;
    }

    public Category getFavCategory() {
        return favCategory;
    }

    public void setFavCategory(Category favCategory) {
        this.favCategory = favCategory;
    }

    public boolean isDisplayMylist() {
        return displayMylist;
    }

    public void setDisplayMylist(boolean displayMylist) {
        this.displayMylist = displayMylist;
    }

    public boolean isDisplayBanner() {
        return displayBanner;
    }

    public void setDisplayBanner(boolean displayBanner) {
        this.displayBanner = displayBanner;
    }

    /**
     * @return the roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Scaffolding code to set up association.
     * @param role
     */
    public final void addRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Can't add null Role");
        }
        role.getUsers().add(this);
        this.getRoles().add(role);
    }

    public Address getActiveAddress() {
        if (addresses != null) {
            for (Address address : addresses) {
                if (AddressStatus.ACTIVE.equals(address.getStatus())) {
                    return address;
                }
            }
        }

        return null;
    }

    private void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    // scaffolding code for collection field addresses
    public final void addAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Can't add a null address.");
        }

        address.setActiveStatus();
        address.setUser(this);
        this.getAddresses().add(address);
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    // scaffolding code for collection field
    public final void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Can't add a null Order.");
        }

        order.setUser(this);
        this.getOrders().add(order);
    }

    public void setActiveStatus() {
        this.status = UserStatus.ACTIVE;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.getClass().getSimpleName());
        s.append("[");
        s.append("id=").append(id);
        s.append(", ");
        s.append("version=").append(version);
        s.append(", ");
        s.append("username=").append(username);
        s.append(", ");
        s.append("key=").append(apiKey);
        s.append("]");

        return s.toString();
    }

    // the following two methods is being used to demonstrate the conception of
    // object equals, identity and database identity:
    // username is unique in hjpetstore, so it is the business key
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof User)) {
            return false;
        }
        final User that = (User) other;
        return this.username.equals(that.getUsername());
    }

    @Override
    public int hashCode() {

        return username.hashCode();
    }
}

