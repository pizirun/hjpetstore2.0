/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.dto;

import java.io.Serializable;
import javax.jms.ObjectMessage;
import org.pprun.hjpetstore.domain.Order;

/**
 * A {@literal DTO} passed between service layer and jms layer as {@link ObjectMessage}.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class OrderRetryDto implements Serializable {

    private Order order;
    private String plainCardNumber;

    public OrderRetryDto() {
    }

    public OrderRetryDto(Order order, String plainCardNumber) {
        this.order = order;
        this.plainCardNumber = plainCardNumber;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public String getPlainCardNumber() {
        return plainCardNumber;
    }

    public void setPlainCardNumber(String plainCardNumber) {
        this.plainCardNumber = plainCardNumber;
    }

    @Override
    public String toString() {
        return "OrderRetryDto{" + "order=" + order + "plainCardNumber=" + plainCardNumber + '}';
    }

}
