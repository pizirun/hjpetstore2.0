/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.service.dto.OrderRetryDto;
import org.springframework.jms.core.MessageCreator;

/**
 * Send message onto external call retry queue, then the {@link #ChargeOrderRetryMessageListener} will make
 * the external call again.
 * <p>
 * We make use of {@code ObjectMessage} in this implementation because the sender and the listener are implemented in our
 * internal of the system and pure-JAVA.
 * <br />
 * If successful, the process is done, otherwise, the message will be queued again and again until successful.
 * This is expected behavior as charge is the most important for e-business.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class ChargeOrderRetryQueueSender extends ExternalCallRetryQueueSender<OrderRetryDto> {

    private static final Log log = LogFactory.getLog(ChargeOrderRetryQueueSender.class);

    @Override
    public void sendRetry(final OrderRetryDto dto) {
        this.getJmsTemplate().send(this.getRetryQueue(), new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage message = session.createObjectMessage();
                message.setObject(dto);

                log.info("ChargeOrderRetryQueueSender is sending jms objectMessage: " + dto + " for chargeOrder retry request ...");
                return message;
            }
        });
    }
}
