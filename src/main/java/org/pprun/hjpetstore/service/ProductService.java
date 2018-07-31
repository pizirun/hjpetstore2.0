package org.pprun.hjpetstore.service;

import java.util.List;
import org.pprun.hjpetstore.domain.Product;

/**
 * service interface for product process.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface ProductService {

    List<Product> getProductListByCategory(String categoryName, int page, int max);

    List<Product> searchProductList(String keywords, int page, int max);

    Product getProduct(String productNumber);
}
