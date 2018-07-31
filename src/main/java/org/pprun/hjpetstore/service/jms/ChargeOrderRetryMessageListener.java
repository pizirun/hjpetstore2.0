/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.service.OrderService;
import org.pprun.hjpetstore.service.dto.OrderRetryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.support.converter.MessageConversionException;

/**
 * Listen to ChargeOrderRetry message and try to call {@code chargeOrder} again.
 *
 * <p>
 * ChargeOrderRetryMessageListener is specific because it should be thread-safe call into orderService.chargeOrder,
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
public class ChargeOrderRetryMessageListener implements MessageListener {

    private static final Log log = LogFactory.getLog(ChargeOrderRetryMessageListener.class);
    private OrderService orderService;

    @Autowired
    @Required
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage msg = (ObjectMessage) message;
                OrderRetryDto dto = (OrderRetryDto) msg.getObject();

                log.info("---------- ChargeOrderRetryMessageListener -----------");
                log.info("received objectMessage: " + dto);
                log.info("-------------------------------------------------");

                log.info("try to call orderService.chargeOrder again");
                // try to call orderService.chargeOrder again
                orderService.chargeOrder(dto.getOrder(), dto.getPlainCardNumber());

            } else {
                throw new UnsupportedOperationException("Message Type has not supported yet.");
            }
        } catch (JMSException ex) {
            log.error("cannot convert the message", ex);
            throw new MessageConversionException("cannot convert the message", ex);
        }
    }
}
