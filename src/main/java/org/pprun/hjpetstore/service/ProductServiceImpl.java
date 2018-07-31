package org.pprun.hjpetstore.service;

import java.util.List;
import org.pprun.hjpetstore.dao.ProductDao;
import org.pprun.hjpetstore.domain.Product;
import org.springframework.beans.factory.annotation.Required;

/**
 * ProductService implementation.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;

    @Required
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> getProductListByCategory(String categoryName, int page, int max) {
        return this.productDao.getProductListByCategory(categoryName, page, max);
    }

    @Override
    public List<Product> searchProductList(String keywords, int page, int max) {
        return this.productDao.searchProductList(keywords, page, max);
    }

    @Override
    public Product getProduct(String productNumber) {
        return this.productDao.getProduct(productNumber);
    }
}
