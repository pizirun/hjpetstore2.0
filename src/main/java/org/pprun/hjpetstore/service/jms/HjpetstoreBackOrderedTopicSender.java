/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.domain.Inventory;
import org.pprun.hjpetstore.domain.Item;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
//import org.springframework.stereotype.Component;

/**
 * Public message onto topic to let the listening supplier get noticed.
 * We don't depend on the original Supplier because we the response quickly and introduce Competition.
 * Then the supplier call our public RESTful api to update the inventory (@TODO).
 *
 * <p>
 * note that we can make use of annotation based configuration by open the commented line
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
//@Component
public class HjpetstoreBackOrderedTopicSender {

    private static final Log log = LogFactory.getLog(HjpetstoreBackOrderedTopicSender.class);
    private JmsTemplate jmsTemplate;
    private Topic hjpetstoreBackOrderedTopic;

    //@Autowired
    @Required
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    //@Autowired
    @Required
    public void setHjpetstoreBackOrderedTopic(Topic hjpetstoreBackOrderedTopic) {
        this.hjpetstoreBackOrderedTopic = hjpetstoreBackOrderedTopic;
    }

    public void sendBackOrderedTopic(final Item item) {
        this.jmsTemplate.send(this.hjpetstoreBackOrderedTopic, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {

                Inventory inventory = item.getInventory();
                int quantity = inventory.getQuantity();
                assert quantity <= 0 : "inventory.quantity should be <= 0 for back ordered case";

                String itemName = item.getItemName();
                int lackedCount = (-quantity) + 1;
                MapMessage message = session.createMapMessage();
                message.setString("item.name", itemName);
                message.setInt("count", lackedCount);

                log.info("HjpetstoreBackOrderedTopicSender is sending jms message: "
                        + "[item.name=" + itemName
                        + ", lackedCount=" + lackedCount
                        + "]");

                return message;
            }
        });
    }
}
