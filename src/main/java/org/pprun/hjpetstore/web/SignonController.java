package org.pprun.hjpetstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.domain.Product;

import org.springframework.beans.support.PagedListHolder;
import org.pprun.hjpetstore.domain.User;
import org.pprun.hjpetstore.service.ProductService;
import org.pprun.hjpetstore.service.UserService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SignonController implements Controller {

    private static final Log log = LogFactory.getLog(SignonController.class);
    private UserService userService;
    private ProductService productService;
    private String successView;
    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean result = validateCaptcha(request);
        if (result == false) {
            return new ModelAndView("ValidationError", "message", "Invalid Kaptcha.");
        }

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = this.userService.getUser(username, password);

        if (user == null) {
            return new ModelAndView("ValidationError", "message", "User does not exist or invalid username/password.  Signon failed.");
        } else {
            UserSession userSession = new UserSession(user);
            int max = 100;
            PagedListHolder<Product> favoriteProductList = new PagedListHolder<Product>(
                    this.productService.getProductListByCategory(user.getFavCategory().getCategoryName(), 1, max));
            favoriteProductList.setPageSize(4);
            userSession.setFavoriteProductList(favoriteProductList);
            request.getSession().setAttribute("userSession", userSession);

            // we need to go back plain http scheme after ssl
            StringBuilder url = new StringBuilder();
            url.append("http://").append(request.getServerName()).append(":").append(port);

            String forwardAction = request.getParameter("forwardAction");
            if (forwardAction != null) {
                url.append(forwardAction);
                //response.sendRedirect(forwardAction);
            } else {
                url.append(request.getContextPath()).append(this.successView);
                //return new ModelAndView(this.successView);
            }

            response.sendRedirect(url.toString());
            return null;
        }
    }

    private boolean validateCaptcha(HttpServletRequest request) {
        String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //String kaptchaReceived = userForm.getKaptcha();
        String kaptchaReceived = request.getParameter("kaptcha");

        if (log.isDebugEnabled()) {
            log.debug("Received kaptcha: '" + kaptchaReceived + "' is comparing with Expected kaptcha: '" + kaptchaExpected + "'...");
        }

        if (kaptchaReceived == null || !kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
            log.error("Received kaptcha: '" + kaptchaReceived + "' is comparing with Expected kaptcha: '" + kaptchaExpected + "'...");
            return false;
        }

        return true;
    }
}
