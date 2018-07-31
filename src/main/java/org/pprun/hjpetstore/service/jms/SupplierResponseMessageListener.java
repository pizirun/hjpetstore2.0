/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.support.converter.MessageConversionException;

/**
 * Listen to the response from supplier and update the order status accordingly.
 *
 * <p>
 * SupplierResponseMessageListener is specific because it should be thread-safe call into orderService.update,
 * instead of code the multi-thread/synchronized the service method,
 * we are using spring built-in tool
 * <a href="http://static.springsource.org/spring/docs/2.0.x/reference/aop-api.html#aop-ts-pool">Pooling target sources</a>,
 * set up in bean configuration file.
 * <br />,
 * The only requirement is the dependency {@litera commons-pool}.
 * </p>
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SupplierResponseMessageListener implements MessageListener {

    private static final Log log = LogFactory.getLog(SupplierResponseMessageListener.class);
    private OrderService orderService;

    @Autowired
    @Required
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof MapMessage) {
                MapMessage msg = (MapMessage) message;

                Long orderId = msg.getLong("order.id");
                String status = msg.getString("deliver.status");

                log.info("---------- SupplierResponseMessageListener -----------");
                log.info("orderId: " + orderId);
                log.info("status: " + status);
                log.info("-------------------------------------------------");

                // update the order status
                if (Order.OrderStatus.IN_TRANSIT.toString().equals(status)) {
                    if (log.isDebugEnabled()) {
                        log.debug("update orderStatus to: " + Order.OrderStatus.IN_TRANSIT);
                    }
                    orderService.updateOrderStatus(orderId, Order.OrderStatus.IN_TRANSIT);
                } else if (Order.OrderStatus.COMPLETE.toString().equals(status)) {
                    if (log.isDebugEnabled()) {
                        log.debug("update orderStatus to: " + Order.OrderStatus.COMPLETE);
                    }

                    orderService.updateOrderStatus(orderId, Order.OrderStatus.COMPLETE);
                } else {
                    // log warn and ignore it
                    log.warn("supplier sent back a un-recognized staus of the order");
                }

            } else {
                throw new UnsupportedOperationException("Message Type has not supported yet.");
            }
        } catch (JMSException ex) {
            log.error("cannot convert the message", ex);
            throw new MessageConversionException("cannot convert the message", ex);
        }
    }
}
