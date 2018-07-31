package org.pprun.hjpetstore.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.support.PagedListHolder;
import org.pprun.hjpetstore.domain.Category;
import org.pprun.hjpetstore.domain.Product;
import org.pprun.hjpetstore.service.CategoryService;
import org.pprun.hjpetstore.service.ProductService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class ViewCategoryController implements Controller {

    private CategoryService categoryService;
    private ProductService productService;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        String categoryName = request.getParameter("categoryName");
        if (categoryName != null) {
            Category category = this.categoryService.getCategory(categoryName);
            int max = 100;
            PagedListHolder<Product> productList = new PagedListHolder<Product>(this.productService.getProductListByCategory(categoryName, 1, max));
            productList.setPageSize(4);
            request.getSession().setAttribute("ViewProductAction_category", category);
            request.getSession().setAttribute("ViewProductAction_productList", productList);
            model.put("category", category);
            model.put("productList", productList);
        } else {
            // normally we should have a page session variable to identify which page we are going,
            // then use it for next data retriever
            Category category = (Category) request.getSession().getAttribute("ViewProductAction_category");
            @SuppressWarnings("unchecked")
            PagedListHolder<Product> productList = (PagedListHolder<Product>) request.getSession().getAttribute("ViewProductAction_productList");
            if (category == null || productList == null) {
                throw new IllegalStateException("Cannot find pre-loaded category and product list");
            }

            String page = request.getParameter("page");
            if ("next".equals(page)) {
                productList.nextPage();
            } else if ("previous".equals(page)) {
                productList.previousPage();
            }
            model.put("category", category);
            model.put("productList", productList);
        }
        return new ModelAndView("Category", model);
    }
}
