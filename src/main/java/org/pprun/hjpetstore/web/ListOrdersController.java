package org.pprun.hjpetstore.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.pprun.hjpetstore.domain.Order;

import org.pprun.hjpetstore.service.OrderService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.util.WebUtils;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class ListOrdersController implements Controller {

    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UserSession userSession = (UserSession) WebUtils.getRequiredSessionAttribute(request, "userSession");
        String username = userSession.getUser().getUsername();
        Map<String, List<Order>> model = new HashMap<String, List<Order>>();
        int max = 100;
        model.put("orderList", this.orderService.getOrdersByUsername(username, 1, max));
        return new ModelAndView("ListOrders", model);
    }
}
