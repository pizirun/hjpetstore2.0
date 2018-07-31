/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.jms.supplier;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.support.converter.MessageConversionException;

/**
 * This is a mocked listener for supplier 'East Pet Company' that listening on the hjpetstore backOrdered topic.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class EastPetCompanyOrderQueueMessageListener implements MessageListener {

    private static final Log log = LogFactory.getLog(EastPetCompanyOrderQueueMessageListener.class);

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof MapMessage) {
                MapMessage msg = (MapMessage) message;
                Long orderId = msg.getLong("order.id");
                String itemName = msg.getString("item.name");
                int quantity = msg.getInt("quantity");
                String property = msg.getStringProperty("supplier");

                log.info("---------- EastPetCompanyOrderQueueMessageListener ----------");
                log.info("orderId: " + orderId);
                log.info("itemName: " + itemName);
                log.info("quantity: " + quantity);
                log.info("property: " + property);
                log.info("----------------------------------------------");
            } else {
                throw new UnsupportedOperationException("Message Type has not supported yet.");
            }
        } catch (JMSException ex) {
            log.error("cannot convert the message", ex);
            throw new MessageConversionException("cannot convert the message", ex);
        }
    }
}
