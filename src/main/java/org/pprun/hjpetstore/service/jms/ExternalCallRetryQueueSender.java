/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pprun.hjpetstore.service.jms;

import javax.jms.Queue;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;

/**
 * A based sender class for retry logic: when external call failed, call {@link sendRetry} again.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public abstract class ExternalCallRetryQueueSender<T> {
    private JmsTemplate jmsTemplate;
    private Queue retryQueue;

    @Required
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    @Required
    public void setRetryQueue(Queue retryQueue) {
        this.retryQueue = retryQueue;
    }

    public Queue getRetryQueue() {
        return retryQueue;
    }
    
    public abstract void sendRetry(final T retryDto);
}
