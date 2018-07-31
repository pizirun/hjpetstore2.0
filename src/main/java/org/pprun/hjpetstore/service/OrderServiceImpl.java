package org.pprun.hjpetstore.service;

import java.util.Calendar;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.dao.ItemDao;
import org.pprun.hjpetstore.dao.OrderDao;
import org.pprun.hjpetstore.domain.Item;
import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.domain.OrderLineItem;
import org.pprun.hjpetstore.domain.PaymentPartner;
import org.pprun.hjpetstore.persistence.jaxb.DecryptCardNumber;
import org.pprun.hjpetstore.persistence.jaxb.EncryptCardNumber;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentChargeResponse;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentValidationResponse;
import org.pprun.hjpetstore.service.dto.OrderRetryDto;
import org.pprun.hjpetstore.service.jms.ChargeOrderRetryQueueSender;
import org.pprun.hjpetstore.service.jms.HjpetstoreBackOrderedTopicSender;
import org.pprun.hjpetstore.service.jms.HjpetstoreOrderQueueSender;
import org.pprun.hjpetstore.web.rest.client.SecurityServiceRestClient;
import org.pprun.hjpetstore.web.rest.partner.client.PaymentRestClient;
import org.springframework.beans.factory.annotation.Required;

/**
 * OrderService implementation.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class OrderServiceImpl implements OrderService {

    private static final Log log = LogFactory.getLog(OrderServiceImpl.class);
    private SecurityServiceRestClient securityServiceRestClient;
    private PaymentRestClient paymentRestClient;
    private UserService userService;
    private HjpetstoreOrderQueueSender hjpetstoreOrderQueueSender;
    private HjpetstoreBackOrderedTopicSender hjpetstoreBackOrderedTopicSender;
    private ChargeOrderRetryQueueSender chargeOrderRetryQueueSender;
    private OrderDao orderDao;
    private ItemDao itemDao;

    @Required
    public void setSecurityServiceRestClient(SecurityServiceRestClient securityServiceRestClient) {
        this.securityServiceRestClient = securityServiceRestClient;
    }

    @Required
    public void setPaymentRestClient(PaymentRestClient paymentRestClient) {
        this.paymentRestClient = paymentRestClient;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Required
    public void setChargeOrderRetryQueueSender(ChargeOrderRetryQueueSender chargeOrderRetryQueueSender) {
        this.chargeOrderRetryQueueSender = chargeOrderRetryQueueSender;
    }

    @Required
    public void setHjpetstoreBackOrderedTopicSender(HjpetstoreBackOrderedTopicSender hjpetstoreBackOrderedTopicSender) {
        this.hjpetstoreBackOrderedTopicSender = hjpetstoreBackOrderedTopicSender;
    }

    @Required
    public void setHjpetstoreOrderQueueSender(HjpetstoreOrderQueueSender hjpetstoreOrderQueueSender) {
        this.hjpetstoreOrderQueueSender = hjpetstoreOrderQueueSender;
    }

    @Required
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Required
    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @Override
    public Long insertOrder(Order order, boolean isShippingAddressRequired) {
        // we need to persist the new ShippingAddress if orderForm.isShippingAddressRequired() == true
        if (isShippingAddressRequired) {
            Long shipAddressId = this.userService.insertAddress(order.getShipAddress());
            if (log.isDebugEnabled()) {
                log.debug("The created shipping address is: " + shipAddressId);
            }
        }

        // take down the plain card number for charge,
        // because after preProcessOrder, the number is encrypt by our private key
        // otherwise will have to decrypt the cardNumber again in charge and its retry processing
        String plainCardNumber = order.getCardNumber();

        // set other fields
        preProcessOrder(order);

        if (log.isDebugEnabled()) {
            log.debug("insertOrder: " + order.getId() + " ...");
        }

        Long orderId = this.orderDao.insertOrder(order);

        if (log.isDebugEnabled()) {
            log.debug("insertOrder done");
        }

        // charge the order
        chargeOrder(order, plainCardNumber);

        return orderId;
    }

    private void preProcessOrder(Order order) {
        String courier = "顺丰快递"; // this should be externalized
        order.setCourier(courier);

        PaymentPartner paymentPartner = securityServiceRestClient.getPaymentPartner("招商银行");
        order.setPaymentPartner(paymentPartner);

        // validate the cardNumber by calling (mocked) web service supplied by the card partner
        PaymentValidationResponse paymentValidationResponse =
                paymentRestClient.validateCardNumber(order.getCardNumber(), order.getPaymentPartner().getName());

        // validate won't retry automatically per business

        if (paymentValidationResponse.isValid() == false) {
            throw new ServiceException("The card number is not valid");
        }

        // encrypt the card number by REST service
        EncryptCardNumber encryptCardNumber = securityServiceRestClient.encryptCardNumber(order.getCardNumber());
        order.setCardNumber(encryptCardNumber.getCardNumber());
    }

    @Override
    public void chargeOrder(Order order, String plainCardNumber) {
        log.info("charge the user for the order: " + order.getId() + " ...");

        PaymentChargeResponse paymentChargeResponse = null;
        try {
            paymentChargeResponse = paymentRestClient.charge(plainCardNumber, order.getTotalPrice(), order.getPaymentPartner().getName());
        } catch (Throwable t) {
            // put it on retry queue
            putOnRetryQueue(order, t, plainCardNumber);
        }

        if (paymentChargeResponse != null) {
            if (PaymentChargeResponse.ChargeStatus.FAILED.equals(paymentChargeResponse.getStatus())) {
                putOnRetryQueue(order, null, plainCardNumber);
            } else {
                log.info("charge order successful");
                order.setOrderStatus(Order.OrderStatus.PAYED);
                postProcessOrder(order);
            }
        } // else return normally with the order status updated to CHARGE_FAILED, and we will retry it
    }

    private void putOnRetryQueue(Order order, Throwable t, String plainCardNumber) {
        log.error("charge order failed, orderId: " + order.getId(), t);
        order.setOrderStatus(Order.OrderStatus.CHARGE_FAILED);
        chargeOrderRetryQueueSender.sendRetry(new OrderRetryDto(order, plainCardNumber));
        // ignore the exception and let the logic goes into retry processing
    }

    private void postProcessOrder(Order order) {
        if (log.isDebugEnabled()) {
            log.debug("updateQuantity start ...");
        }
        this.itemDao.updateQuantity(order);

        if (log.isDebugEnabled()) {
            log.debug("updateQuantity done");
        }

        // log info for external call anyway
        log.info("send the order message to supplier");

        for (OrderLineItem orderLineItem : order.getOrderLineItems()) {
            hjpetstoreOrderQueueSender.notifySuppler(orderLineItem);

            // if order backed - the inventory.quantity < 0, we need to public a topic
            Item item = orderLineItem.getItem();
            int quantity = item.getInventory().getQuantity();
            if (quantity <= 0) {
                log.info("sending order backed topic to suppliers ...");

                hjpetstoreBackOrderedTopicSender.sendBackOrderedTopic(item);
            }
        }
    }

    @Override
    public Order getOrderById(long id) {
        Order order = this.orderDao.getOrderById(id);

        // decrypt the cardNumber by call REST
        DecryptCardNumber decryptCardNumber = securityServiceRestClient.decryptCardNumber(order.getCardNumber());
        order.setCardNumber(decryptCardNumber.getCardNumber());
        return order;
    }

    @Override
    public List<Order> getOrdersByUsername(String username, int page, int max) {
        List<Order> orders = this.orderDao.getOrdersByUsername(username, page, max);
        for (Order order : orders) {
            // decrypt the cardNumber by call REST
            DecryptCardNumber decryptCardNumber = securityServiceRestClient.decryptCardNumber(order.getCardNumber());
            order.setCardNumber(decryptCardNumber.getCardNumber());
        }

        return orders;
    }

    @Override
    public List<Order> getOrders(Calendar from, Calendar to) {
        return this.orderDao.getOrders(from, to);
    }

    @Override
    public List<OrderLineItem> getOrderLineItemsByOrderId(long orderId) {
        return this.orderDao.getOrderLineItemsByOrderId(orderId);
    }

    @Override
    public void updateOrderStatus(long orderId, Order.OrderStatus status) {
        Order order = orderDao.getOrderById(orderId);
        order.setOrderStatus(status);

        orderDao.updateOrder(order);
    }
}
