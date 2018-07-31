package org.pprun.hjpetstore.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.domain.Address;
import org.pprun.hjpetstore.domain.Product;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.pprun.hjpetstore.domain.User;
import org.pprun.hjpetstore.service.CategoryService;
import org.pprun.hjpetstore.service.ProductService;
import org.pprun.hjpetstore.service.UserService;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

/**
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@SuppressWarnings("deprecation")
public class UserFormController extends SimpleFormController {

    private static final Log log = LogFactory.getLog(UserFormController.class);
    public static final String[] LANGUAGES = {"english", "japanese", "汉语"};
    private UserService userService;
    private CategoryService categoryService;
    private ProductService productService;
    private int port;

    public void setPort(int port) {
        this.port = port;
    }

    // kaptcha
//    private Producer captchaProducer;
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public UserFormController() {
        setSessionForm(true);
        setValidateOnBinding(false);
        setCommandName("userForm");
        setFormView("EditUserForm");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        if (userSession != null) {
            User user = this.userService.getUserAndFetch(userSession.getUser().getUsername());
            // clear the password
            user.setPassword(null);
            return new UserForm(user);
        } else {
            return new UserForm();
        }
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors)
            throws Exception {

        UserForm userForm = (UserForm) command;
        validateCaptcha(request, errors);

        User user = userForm.getUser();

        if (request.getParameter("user.displayMylist") == null) {
            user.setDisplayMylist(false);
        }
        if (request.getParameter("user.displayBanner") == null) {
            user.setDisplayBanner(false);
        }
        if (request.getParameter("generateKey") == null) {
            userForm.setGenerateKey(false);
        }

        errors.setNestedPath("user");
        getValidator().validate(user, errors);
        errors.setNestedPath("");

        if (userForm.isNewUser()) {
            //user.setActiveStatus();
            ValidationUtils.rejectIfEmpty(errors, "user.username", "USER_NAME_REQUIRED", "Username is required.");
            if (user.getPassword() == null || user.getPassword().length() < 1
                    || !user.getPassword().equals(userForm.getRepeatedPassword())) {
                errors.reject("password.required", null);
            }
        } else if (user.getPassword() != null && user.getPassword().length() > 0) {
            if (!user.getPassword().equals(userForm.getRepeatedPassword())) {
                errors.reject("password.mismatch", null);
            }

            // the selected address id
            String addressId = request.getParameter("status");
            if (StringUtils.hasText(addressId)) {

                long activeAddressId = Long.parseLong(request.getParameter("status"));
                for (Address address : user.getAddresses()) {

                    // switch the active status
                    if (activeAddressId == address.getId()) {
                        address.setActiveStatus();
                    } else {
                        address.setNonActiveStatus();
                    }
                }
            }
        }
    }

    @Override
    protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("languages", LANGUAGES);
        model.put("categories", this.categoryService.getCategoryList());

        return model;
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

        UserForm userForm = (UserForm) command;

        try {
            if (userForm.isNewUser()) {
                this.userService.insertUser(userForm.getUser(), userForm.isGenerateKey());
            } else {
                this.userService.updateUser(userForm.getUser());
            }
        } catch (DataIntegrityViolationException ex) {
            log.error("insert or update User failed", ex);

            errors.rejectValue("user.username", "USER_NAME_ALREADY_EXISTS",
                    "Username already exists: choose a different UserName.");
            return showForm(request, response, errors);
        }

        UserSession userSession = new UserSession(this.userService.getUserAndFetch(userForm.getUser().getUsername()));
        int max = 100;
        PagedListHolder<Product> favoriteProductList = new PagedListHolder<Product>(
                this.productService.getProductListByCategory(userForm.getUser().getFavCategory().getCategoryName(), 1, max));
        favoriteProductList.setPageSize(4);
        userSession.setFavoriteProductList(favoriteProductList);
        request.getSession().setAttribute("userSession", userSession);

        // we need to go back plain http scheme after ssl
        StringBuilder url = new StringBuilder();
        url.append("http://").append(request.getServerName()).append(":").append(port).append(request.getContextPath()).append("/shop/index.html");

        response.sendRedirect(url.toString());

        return null;
    }

    private void validateCaptcha(HttpServletRequest request, BindException errors) {
        String kaptchaExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //String kaptchaReceived = userForm.getKaptcha();
        String kaptchaReceived = request.getParameter("kaptcha");

        if (log.isDebugEnabled()) {
            log.debug("Received kaptcha: '" + kaptchaReceived + "' is comparing with Expected kaptcha: '" + kaptchaExpected + "'...");
        }

        if (kaptchaReceived == null || !kaptchaReceived.equalsIgnoreCase(kaptchaExpected)) {
            log.error("Received kaptcha: '" + kaptchaReceived + "' is comparing with Expected kaptcha: '" + kaptchaExpected + "'...");
            errors.rejectValue(null, "error.invalidKaptcha", "invalidKaptcha");

        }
    }
}
