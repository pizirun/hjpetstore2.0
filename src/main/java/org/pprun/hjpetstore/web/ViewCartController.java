package org.pprun.hjpetstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pprun.hjpetstore.persistence.Cart;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class ViewCartController implements Controller {

    private String successView;

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        Cart cart = (Cart) WebUtils.getOrCreateSessionAttribute(request.getSession(), "sessionCart", Cart.class);
        String page = request.getParameter("page");
        if (userSession != null) {
            if ("next".equals(page)) {
                userSession.getFavoriteProductList().nextPage();
            } else if ("previous".equals(page)) {
                userSession.getFavoriteProductList().previousPage();
            }
        }

        if ("nextCart".equals(page)) {
            cart.getCartItemList().nextPage();
        } else if ("previousCart".equals(page)) {
            cart.getCartItemList().previousPage();
        }
        return new ModelAndView(this.successView, "cart", cart);
    }
}
