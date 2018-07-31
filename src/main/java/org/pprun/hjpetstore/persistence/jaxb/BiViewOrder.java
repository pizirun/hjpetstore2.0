/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.persistence.jaxb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import org.pprun.hjpetstore.domain.Address;
import org.pprun.hjpetstore.domain.Order;

/**
 *  This is the view of order for Business Intelligence (BI).
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class BiViewOrder {

    private long orderId;
    private Address address;
    private String courier;
    private BigDecimal totalPrice;
    private String cardType;
    private Order.OrderStatus orderStatus;
    private String locale;
    private String paymentPartner;
    private List<BiViewOrderLineItem> orderLineItems;

    /**
     * @return the orderId
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the address
     */
    @XmlElement(name="shipAddress")
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the courier
     */
    public String getCourier() {
        return courier;
    }

    /**
     * @param courier the courier to set
     */
    public void setCourier(String courier) {
        this.courier = courier;
    }

    /**
     * @return the totalPrice
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * @param totalPrice the totalPrice to set
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return the cardType
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * @param cardType the cardType to set
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * @return the orderStatus
     */
    public Order.OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(Order.OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return the locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * @return the paymentPartner
     */
    public String getPaymentPartner() {
        return paymentPartner;
    }

    /**
     * @param paymentPartner the paymentPartner to set
     */
    public void setPaymentPartner(String paymentPartner) {
        this.paymentPartner = paymentPartner;
    }

    @XmlElement(name = "orderLineItems")
    public List<BiViewOrderLineItem> getOrderLineItems() {
        if (orderLineItems == null) {
            orderLineItems = new ArrayList<BiViewOrderLineItem>();
        }
        return orderLineItems;
    }

    @Override
    public String toString() {
        return "BiviewOrder{" + "orderId=" + orderId + "address=" + address + "courier=" + courier +
                "totalPrice=" + totalPrice + "cardType=" + cardType + "orderStatus=" + orderStatus +
                "locale=" + locale + "paymentPartner=" + paymentPartner + "orderLineItems=" + orderLineItems + '}';
    }
}
