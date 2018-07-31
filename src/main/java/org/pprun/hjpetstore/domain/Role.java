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
import org.pprun.hjpetstore.persistence.DomainObject;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class Role extends DomainObject {

    private String roleName;
    private String roleDescription;
    private Set<User> users = new HashSet<User>();

    public Role() {
    }

    /**
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @return the roleDescription
     */
    public String getRoleDescription() {
        return roleDescription;
    }

    /**
     * @param roleDescription the roleDescription to set
     */
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    /**
     * @return the users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Scaffolding code for collection field to set up association.
     * 
     * @param user
     */
    public final void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Can't add null User.");
        }

        user.getRoles().add(this);
        this.getUsers().add(user);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.getClass().getSimpleName());
        s.append("[");
        s.append("id=").append(id);
        s.append(", ");
        s.append("version=").append(version);
        s.append(", ");
        s.append("username=").append(roleName);
        s.append(", ");
        s.append("roleDescription=").append(roleDescription);
        s.append("]");

        return s.toString();
    }
}
