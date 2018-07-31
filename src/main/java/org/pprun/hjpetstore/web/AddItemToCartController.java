package org.pprun.hjpetstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pprun.hjpetstore.persistence.Cart;
import org.pprun.hjpetstore.domain.Item;
import org.pprun.hjpetstore.service.ItemService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * The controller to add item to cart
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class AddItemToCartController implements Controller {

    private ItemService itemService;

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Cart cart = (Cart) WebUtils.getOrCreateSessionAttribute(request.getSession(), "sessionCart", Cart.class);
        String workingItemName = request.getParameter("workingItemName");
        if (cart.containsItemName(workingItemName)) {
            cart.incrementQuantityByItemName(workingItemName);
        } else {
            // isInStock is a "real-time" property that must be updated
            // every time an item is added to the cart, even if other
            // item details are cached.
            boolean isInStock = this.itemService.isItemInStock(workingItemName);
            Item item = this.itemService.getItem(workingItemName);
            cart.addItem(item, isInStock);
        }
        return new ModelAndView("Cart", "cart", cart);
    }
}
