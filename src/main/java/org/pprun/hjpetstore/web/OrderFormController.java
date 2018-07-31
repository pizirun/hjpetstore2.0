package org.pprun.hjpetstore.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.common.databind.CustomCalendarEditor;
import org.pprun.common.util.CalendarUtil;
import org.pprun.hjpetstore.domain.Order;

import org.pprun.hjpetstore.domain.User;
import org.pprun.hjpetstore.persistence.Cart;
import org.pprun.hjpetstore.service.OrderService;
import org.pprun.hjpetstore.service.UserService;
import org.pprun.hjpetstore.service.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.AbstractWizardFormController;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@SuppressWarnings("deprecation")
public class OrderFormController extends AbstractWizardFormController {

    private static Log log = LogFactory.getLog(OrderFormController.class);
    private UserService userService;
    private OrderService orderService;
    private int port;

    @Required
    public void setPort(int port) {
        this.port = port;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Required
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public OrderFormController() {
        setCommandName("orderForm");
        setPages(new String[]{"NewOrderForm", "ShippingForm", "ConfirmOrder"});
    }

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        CustomCalendarEditor ccExpireDateditor = new CustomCalendarEditor(new SimpleDateFormat(CalendarUtil.CREDIT_CARD_DATE_FORMAT), false);
        binder.registerCustomEditor(Calendar.class, ccExpireDateditor);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws ModelAndViewDefiningException {
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        Cart cart = (Cart) request.getSession().getAttribute("sessionCart");
        if (cart != null) {
            // Re-read user from DB at team's request.
            User user = this.userService.getUserAndFetch(userSession.getUser().getUsername());
            OrderForm orderForm = new OrderForm();
            orderForm.getOrder().initOrder(user, cart);
            return orderForm;
        } else {
            ModelAndView modelAndView = new ModelAndView("ValidationError");
            modelAndView.addObject("message", "An order could not be created because a cart could not be found.");
            throw new ModelAndViewDefiningException(modelAndView);
        }
    }

    @Override
    protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors, int page) {
        if (page == 0 && request.getParameter("shippingAddressRequired") == null) {

            // control the page flow, getTargetPage depends on the  orderForm.isShippingAddressRequired())
            OrderForm orderForm = (OrderForm) command;
            orderForm.setShippingAddressRequired(false);

            // we need to make sure the ship address is reflecting any change in billing address, for example,
            // user change any fields in page 0
            Order order = orderForm.getOrder();
            order.setShipAddress(order.getBillAddress());
        }
    }

    @Override
    protected Map<String, List<String>> referenceData(HttpServletRequest request, int page) {
        if (page == 0) {
            // This list can be externalized into properties base file
            List<String> creditCardTypes = new ArrayList<String>();
            creditCardTypes.add("招商银行信用卡");
            creditCardTypes.add("Visa");
            creditCardTypes.add("MasterCard");
            creditCardTypes.add("American Express");
            Map<String, List<String>> model = new HashMap<String, List<String>>();
            model.put("creditCardTypes", creditCardTypes);
            return model;
        }
        return null;
    }

    @Override
    protected int getTargetPage(HttpServletRequest request, Object command, Errors errors, int currentPage) {
        OrderForm orderForm = (OrderForm) command;
        if (currentPage == 0 && orderForm.isShippingAddressRequired()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    protected void validatePage(Object command, Errors errors, int page) {
        OrderForm orderForm = (OrderForm) command;
        OrderValidator orderValidator = (OrderValidator) getValidator();
        errors.setNestedPath("order");
        switch (page) {
            case 0:
                orderValidator.validateCreditCard(orderForm.getOrder(), errors);
                orderValidator.validateBillingAddress(orderForm.getOrder(), errors);
                break;
            case 1:
                orderValidator.validateShippingAddress(orderForm.getOrder(), errors);
        }
        errors.setNestedPath("");
    }

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        OrderForm orderForm = (OrderForm) command;

        // and billingAddress is update, but shipAddress will not,
        // which is set in the hibnerate mapping file
        Long orderId = this.orderService.insertOrder(orderForm.getOrder(), orderForm.isShippingAddressRequired());

        String message = "Thank you, your order has been submitted.";
        request.getSession().removeAttribute("sessionCart");

        // we need to go back plain http scheme after ssl
        StringBuilder url = new StringBuilder();
        url.append("http://")
                .append(request.getServerName())
                .append(":")
                .append(port)
                .append(request.getContextPath())
                .append("/shop/viewOrder.html")
                .append("?orderId=").append(orderId)
                .append("&message=")
                .append(message);

        response.sendRedirect(url.toString());
        return null;
    }
}
