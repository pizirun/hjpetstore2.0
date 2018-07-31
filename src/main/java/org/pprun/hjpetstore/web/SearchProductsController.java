package org.pprun.hjpetstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.pprun.hjpetstore.domain.Product;
import org.pprun.hjpetstore.service.ProductService;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SearchProductsController implements Controller {

    private ProductService productService;

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String keyword = request.getParameter("keyword");
        if (keyword != null) {
            // security control
            if (!StringUtils.hasLength(keyword)) {
                return new ModelAndView("ValidationError", "message", "Please enter a keyword to search for, then press the search button.");
            }

            int max = 100;
            PagedListHolder<Product> productList = new PagedListHolder<Product>(this.productService.searchProductList(keyword.toLowerCase(), 1, max));
            productList.setPageSize(4);
            request.getSession().setAttribute("SearchProductsController_productList", productList);
            return new ModelAndView("SearchProducts", "productList", productList);
        } else {
            String page = request.getParameter("page");
            @SuppressWarnings("unchecked")
            PagedListHolder<Product> productList = (PagedListHolder<Product>) request.getSession().getAttribute("SearchProductsController_productList");
            if (productList == null) {
                return new ModelAndView("ValidationError", "message", "Your session has timed out. Please start over again.");
            }
            if ("next".equals(page)) {
                productList.nextPage();
            } else if ("previous".equals(page)) {
                productList.previousPage();
            }
            return new ModelAndView("SearchProducts", "productList", productList);
        }
    }
}
