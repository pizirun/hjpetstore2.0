package org.pprun.hjpetstore.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.pprun.hjpetstore.domain.Item;

import org.springframework.beans.support.PagedListHolder;
import org.pprun.hjpetstore.domain.Product;
import org.pprun.hjpetstore.service.ItemService;
import org.pprun.hjpetstore.service.ProductService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class ViewProductController implements Controller {

    private ItemService itemService;
    private ProductService productService;

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        String productNumber = request.getParameter("productNumber");

        if (productNumber != null) {
            PagedListHolder<Item> itemList = new PagedListHolder<Item>(this.itemService.getItemListByProduct(productNumber));
            itemList.setPageSize(4);
            Product product = this.productService.getProduct(productNumber);
            request.getSession().setAttribute("ViewProductAction_itemList", itemList);
            request.getSession().setAttribute("ViewProductAction_product", product);
            model.put("itemList", itemList);
            model.put("product", product);
        } else {
            @SuppressWarnings("unchecked")
            PagedListHolder<Item> itemList = (PagedListHolder<Item>) request.getSession().getAttribute("ViewProductAction_itemList");
            Product product = (Product) request.getSession().getAttribute("ViewProductAction_product");
            String page = request.getParameter("page");
            if ("next".equals(page)) {
                itemList.nextPage();
            } else if ("previous".equals(page)) {
                itemList.previousPage();
            }
            model.put("itemList", itemList);
            model.put("product", product);
        }
        return new ModelAndView("Product", model);
    }
}
