package org.pprun.hjpetstore.web;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.service.OrderService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class ViewOrderController implements Controller {

    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Long orderId  = (Long) WebUtils.getRequiredSessionAttribute(request, "orderId");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = this.orderService.getOrderById(orderId);

        UserSession userSession = (UserSession) WebUtils.getRequiredSessionAttribute(request, "userSession");

        if (userSession.getUser().getUsername().equals(order.getUser().getUsername())) {
            String message = request.getParameter("message");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("order", order);

            if (message != null) {
                // after the new order, there's thanks message
                model.put("message", message);
            }
            return new ModelAndView("ViewOrder", model);
        } else {
            return new ModelAndView("ValidationError", "message", "You may only view your own orders.");
        }
    }
}
