/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.domain.OrderLineItem;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Send message onto queue to notify the listening supplier an order is initiated.
 * Then the supplier (and only one supplier, as this is queue message domain) will confirm back a message onto another queue.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class HjpetstoreOrderQueueSender {

    private static final Log log = LogFactory.getLog(HjpetstoreOrderQueueSender.class);
    private JmsTemplate jmsTemplate;
    private Queue hjpetstoreOrderQueue;

    @Required
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Required
    public void setHjpetstoreOrderQueue(Queue hjpetstoreOrderQueue) {
        this.hjpetstoreOrderQueue = hjpetstoreOrderQueue;
    }

    public void notifySuppler(final OrderLineItem orderLineItem) {
        this.jmsTemplate.send(this.hjpetstoreOrderQueue, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();

                long orderId = orderLineItem.getOrder().getId();
                String itemName = orderLineItem.getItem().getItemName();
                int quantity = orderLineItem.getQuantity();
                String supplierName = orderLineItem.getItem().getSupplier().getSupplierName();

                message.setLong("order.id", orderId);
                message.setString("item.name", itemName);
                message.setInt("quantity", quantity);

                // the supplier should use massge selector to filter the message
                message.setStringProperty("supplier", supplierName);

                log.info("HjpetstoreOrderQueueSender is sending jms message: "
                        + "[order.id=" + orderId
                        + ", item.name=" + itemName
                        + ", quantity=" + quantity
                        + ", supplierName=" + supplierName
                        + "]");

                return message;
            }
        });
    }
}
