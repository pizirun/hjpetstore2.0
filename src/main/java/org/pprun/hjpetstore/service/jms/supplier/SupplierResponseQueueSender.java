/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.jms.supplier;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.persistence.jaxb.supplier.SupplierOrderStatusRequest;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Send message onto supplierResponseQueue queue to to update order status.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SupplierResponseQueueSender {

    private static final Log log = LogFactory.getLog(SupplierResponseQueueSender.class);
    private JmsTemplate jmsTemplate;
    private Queue supplierResponseQueue;

    @Required
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Required
    public void setSupplierResponseQueue(Queue supplierResponseQueue) {
        this.supplierResponseQueue = supplierResponseQueue;
    }

    public void responseOrderStatus(final SupplierOrderStatusRequest supplierOrderStatusRequest) {
        this.jmsTemplate.send(this.supplierResponseQueue, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();

                long orderId = supplierOrderStatusRequest.getOrderId();
                String deliverStatus = supplierOrderStatusRequest.getStatus();
                String supplier = supplierOrderStatusRequest.getSupplier();

                message.setLong("order.id", orderId);

                message.setString("deliver.status", deliverStatus);

                log.info("SupplierResponseQueueSender is sending jms message: "
                        + "[order.id=" + orderId + ", deliver.status=" + deliverStatus + ", supplier =" + supplier + "]");

                return message;
            }
        });
    }
}
