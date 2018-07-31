package org.pprun.hjpetstore.service.rest;

import org.pprun.hjpetstore.dao.OrderDao;
import org.pprun.hjpetstore.dao.ProductDao;
import org.pprun.hjpetstore.dao.UserDao;
import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.persistence.jaxb.Products;
import org.pprun.hjpetstore.service.ServiceException;
import org.springframework.beans.factory.annotation.Required;

/**
 * The RESTful implementation for HjpetstoreService.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class HjpetstoreServiceImpl implements HjpetstoreService {

    private OrderDao orderDao;
    private ProductDao productDao;
    private UserDao userDao;

    @Required
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Required
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Required
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Order getOrderById(long id) throws ServiceException {
        return orderDao.getOrderById(id);
    }

    @Override
    public Products searchProductList(String keywords, int page, int max) throws ServiceException {
//        boolean isUserExistingForKey = userDao.isUserExistingForApiKey(apiKey);
//
//        if (isUserExistingForKey == false) {
//            throw new ServiceException("Your apikey is not valid");
//        }
//
//        // now it is safe to make the call and return the result
        Products products = new Products();
        products.getProductList().addAll(this.productDao.searchProductLightweightList(keywords, page, max));
        return products;
    }
}
