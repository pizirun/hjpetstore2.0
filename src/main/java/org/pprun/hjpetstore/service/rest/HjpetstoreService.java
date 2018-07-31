package org.pprun.hjpetstore.service.rest;

import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.persistence.jaxb.Products;
import org.pprun.hjpetstore.service.ServiceException;

/**
 * Separate order service to define remote service interface.
 *
 * <p>
 * It should not depend on other common xxxService interface because
 * remote service such as web service is usually have a different transaction model and strategy.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface HjpetstoreService {

    Order getOrderById(long id) throws ServiceException;

    Products searchProductList(String keywords, int page, int max) throws ServiceException;
}
