package org.pprun.hjpetstore.web;

import java.io.Serializable;
import org.pprun.hjpetstore.domain.Product;

import org.springframework.beans.support.PagedListHolder;
import org.pprun.hjpetstore.domain.User;

/**
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class UserSession implements Serializable {

    private User user;
    private PagedListHolder<Product> favoriteProductList;

    public UserSession(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setFavoriteProductList(PagedListHolder<Product> favoriteProductList) {
        this.favoriteProductList = favoriteProductList;
    }

    public PagedListHolder<Product> getFavoriteProductList() {
        return favoriteProductList;
    }
}
