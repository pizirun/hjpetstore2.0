/*
 * Order.java
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.domain;

import org.pprun.hjpetstore.persistence.CartItem;
import org.pprun.hjpetstore.persistence.Cart;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import org.pprun.common.util.CalendarUtil;
import org.pprun.hjpetstore.persistence.DomainObject;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class Order extends DomainObject {

    public enum OrderStatus {
        PENDING("PENDING"),
        PAYED("PAYED"),
        CHARGE_FAILED("CHARGE_FAILED"),
        IN_TRANSIT("IN_TRANSIT"),
        CANCELLED("CANCELLED"),
        COMPLETE("COMPLETE");
        private final String status;

        private OrderStatus(String status) {
            this.status = status;
        }

        public String value() {
            return status;
        }

        @Override
        public String toString() {
            return status;
        }
    }
    private User user;
    private Address shipAddress;
    private Address billAddress;
    private String courier;
    private BigDecimal totalPrice;
    private String billToFirstname;
    private String billToLastname;
    private String shipToFirstname;
    private String shipToLastname;
    private String cardNumber;
    private String cardType;
    private Calendar expireDate;
    private OrderStatus orderStatus = OrderStatus.PENDING;
    private Locale locale;
    private PaymentPartner paymentPartner;
    private Set<OrderLineItem> orderLineItems = new HashSet<OrderLineItem>();

    /**
     * Creates a new instance of Order
     */
    public Order() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(Address shipAddress) {
        this.shipAddress = shipAddress;
    }

    public Address getBillAddress() {
        return billAddress;
    }

    public void setBillAddress(Address billAddress) {
        this.billAddress = billAddress;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public String getBillToFirstname() {
        return billToFirstname;
    }

    public void setBillToFirstname(String billToFirstname) {
        this.billToFirstname = billToFirstname;
    }

    public String getBillToLastname() {
        return billToLastname;
    }

    public void setBillToLastname(String billToLastname) {
        this.billToLastname = billToLastname;
    }

    public String getShipToFirstname() {
        return shipToFirstname;
    }

    public void setShipToFirstname(String shipToFirstname) {
        this.shipToFirstname = shipToFirstname;
    }

    public String getShipToLastname() {
        return shipToLastname;
    }

    public void setShipToLastname(String shipToLastname) {
        this.shipToLastname = shipToLastname;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Calendar getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Calendar expireDate) {
        this.expireDate = expireDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setPaymentPartner(PaymentPartner paymentPartner) {
        this.paymentPartner = paymentPartner;
    }

    public PaymentPartner getPaymentPartner() {
        return paymentPartner;
    }

    private void setOrderLineItems(Set<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    public Set<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    public void addLineItemFromCartItem(CartItem cartItem) {
        // because Order only know OrderLineItem, we need create one from CartItem
        OrderLineItem orderLineItem = new OrderLineItem().initOrderLineItem(getOrderLineItems().size() + 1, this, cartItem);
        addLineItem(orderLineItem);
    }

    /**
     * scaffloding code
     */
    public final void addLineItem(OrderLineItem orderLineItem) {
        if (orderLineItem == null) {
            throw new IllegalArgumentException("Can't add a null lineItem.");
        }
        // the constraints have been set up when constructing OrderLineItem
        getOrderLineItems().add(orderLineItem);
    }

    // -- Business methods
    public void initOrder(User user, Cart cart) {
        this.user = user;
        createTime = Calendar.getInstance();

        // user.addresses should not be null
        Address addr = user.getAddresses().iterator().next();

        // need the copy constructure here,
        // otherwise because of the field by reference, change in billingAddress will be updated in
        // shipAddress, vice versa. We don't hope this take place
        //
        // the same inforamtion for both for now, except the id is null,
        // the id will be auto assign during persist
        shipAddress = new Address(addr);
        billAddress = new Address(addr);

        shipToFirstname = user.getFirstname();
        shipToLastname = user.getLastname();
        billToFirstname = user.getFirstname();
        billToLastname = user.getLastname();

        totalPrice = cart.getSubTotal();

        setLocale(Locale.CHINA);
        orderStatus = OrderStatus.PENDING;

        // we need firstly persist the order, as its ID will be one of the composite key of OrderLineItem
        Iterator i = cart.getAllCartItems();
        while (i.hasNext()) {
            CartItem cartItem = (CartItem) i.next();
            addLineItemFromCartItem(cartItem);
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.getClass().getSimpleName());
        s.append("[");
        s.append("id=").append(id);
        s.append(", ");
        s.append("version=").append(version);
        s.append(", ");

        //  it is not a good idea to include the association because they maight be a proxy
        //  LazyInitializationException will happen
        // s.append("shipAddr=").append(shipAddress);
        // s.append(", ");

        //  it is not a good idea to include the association because they maight be a proxy
        //  LazyInitializationException will happen
        //  s.append("billAddr=").append(billAddress);
        s.append(", ");
        s.append("shipToFirstname=").append(shipToFirstname);
        s.append(", ");
        s.append("shipToLasttname=").append(shipToLastname);
        s.append(", ");
        s.append("billToFirstname=").append(billToFirstname);
        s.append(", ");
        s.append("billToLastname=").append(billToLastname);
        s.append(", ");
        s.append("courier=").append(courier);
        s.append(", ");
        s.append("cardNumber=").append(cardNumber);
        s.append(", ");
        s.append("cardType=").append(cardType);
        s.append(", ");
        s.append("orderStatus=").append(orderStatus);
        s.append(", ");
        s.append("totalPrice=").append(totalPrice);
        s.append(", ");
        s.append("locale=").append(locale);
        s.append(", ");
        s.append("expireDate=");
        s.append(CalendarUtil.getDateString(expireDate, CalendarUtil.SIMPLE_DATE_FORMAT));
        s.append("]");

        return s.toString();
    }
}
