package org.pprun.hjpetstore.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pprun.hjpetstore.domain.Item;
import org.pprun.hjpetstore.service.ItemService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class ViewItemController implements Controller {

    private ItemService itemService;

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String itemName = request.getParameter("itemName");
        Item item = this.itemService.getItemLightWeight(itemName);
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("item", item);
        model.put("product", item.getProduct());
        return new ModelAndView("Item", model);
    }
}
