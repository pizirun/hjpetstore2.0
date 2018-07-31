/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.bi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.common.util.CalendarUtil;
import org.pprun.common.util.FileUtils;
import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.domain.OrderLineItem;
import org.pprun.hjpetstore.persistence.jaxb.BiViewOrder;
import org.pprun.hjpetstore.persistence.jaxb.BiViewOrderLineItem;
import org.pprun.hjpetstore.persistence.jaxb.BiViewOrderList;
import org.pprun.hjpetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;

/**
 * This is a nightly run task to export the order income of previous day as xml file.
 *
 * <p>TODO: this file will be sftp to {@literal ERP} or other internal BI system.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class OrderIncomeExport {

    private static final Log log = LogFactory.getLog(OrderIncomeExport.class);
    private Marshaller jaxb2Marshaller;
    private Resource exportFolder;
    private OrderService orderService;

    @Required
    public void setJaxb2Marshaller(Marshaller jaxb2Marshaller) {
        this.jaxb2Marshaller = jaxb2Marshaller;
    }

    @Required
    public void setExportFolder(Resource exportFolder) {
        this.exportFolder = exportFolder;
    }

    @Required
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void exportOrderIncome() throws Exception {
        Calendar from = CalendarUtil.startOfDayYesterday();
        Calendar to = CalendarUtil.truncateDay(Calendar.getInstance());

        log.info("export order income for [" + CalendarUtil.format(from) + ", " + CalendarUtil.format(to) + "]");

        // 1. get orders created between previous day and today
        log.info("getting order list ...");

        List<Order> orders = orderService.getOrders(from, to);

        log.info("Total orders of previous day: " + orders.size());

        if (orders.isEmpty()) {
            log.info("No order in previous day, no export file will be generated.");
        } else {
            // 2. construct the BI view of the order
            log.info("constructing the Bi view of the order ...");

            BiViewOrderList biViewOrderList = constructBiViewOrders(orders);
            log.info("constructing the Bi view of the order done!");

            // 3. construct xml
            log.info("constructing the xml output ...");

            outputXml(biViewOrderList, from);

            log.debug("Output XML done!");
        }
    }

    private BiViewOrderList constructBiViewOrders(List<Order> orders) {
        int index = 0;
        BigDecimal totalAmount = BigDecimal.ZERO;

        List<BiViewOrder> biViewOrders = new ArrayList<BiViewOrder>();
        for (Order order : orders) {
            if (log.isDebugEnabled()) {
                log.debug("processing order #" + ++index);
            }

            BiViewOrder biOrder = new BiViewOrder();
            biOrder.setOrderId(order.getId());
            biOrder.setAddress(order.getShipAddress());
            biOrder.setCardType(order.getCardType());
            biOrder.setCourier(order.getCourier());
            biOrder.setLocale(order.getLocale().getDisplayName());
            biOrder.setOrderStatus(order.getOrderStatus());
            biOrder.setPaymentPartner(order.getPaymentPartner().getName());
            biOrder.setTotalPrice(order.getTotalPrice());

            log.info("retrieving OrderLineItem for order (order.id = " + order.getId() + ")");

            // we have eaglier fetch our the orderLineItems in orderDao,
            // so no need resort for another query.
            //
            //List<OrderLineItem> orderLineItems = orderService.getOrderLineItemsByOrderId(order.getId());

            Set<OrderLineItem> orderLineItems = order.getOrderLineItems();
            int itemIndex = 0;
            if (orderLineItems == null || orderLineItems.isEmpty()) {
                log.info("no orderLineItem in order (order.id = " + order.getId() + ")");
            } else {
                for (OrderLineItem oli : orderLineItems) {
                    if (oli == null) {
                        log.info("AHA, we got Hibernate glitch, why return null?");
                        continue;
                    }

                    if (log.isDebugEnabled()) {
                        log.debug("processing orderLineItem #" + ++itemIndex);
                    }

                    // the biViewOrderLineItem into the list of biOrder
                    BiViewOrderLineItem biViewOrderLineItem = new BiViewOrderLineItem(oli.getId().getLineNumber(),
                            oli.getQuantity(), oli.getTotalPrice(), oli.getItem().getItemName());

                    biOrder.getOrderLineItems().add(biViewOrderLineItem);
                }
            }
            biViewOrders.add(biOrder);

            // aggregate the totalPrice
            totalAmount = totalAmount.add(order.getTotalPrice());
        }

        log.info("Total amount: " + totalAmount);

        BiViewOrderList biViewOrderList = new BiViewOrderList();
        biViewOrderList.setTotalAmount(totalAmount);
        biViewOrderList.getBiViewOrders().addAll(biViewOrders);

        return biViewOrderList;
    }

    private void outputXml(BiViewOrderList biViewOrderList, Calendar from) throws Exception {

        FileOutputStream os = null;
        File file = null;

        try {
            File folder = exportFolder.getFile();
            FileUtils.ensureDirExist(folder.getCanonicalPath());
            String fileName = CalendarUtil.getDateString(from, CalendarUtil.SHORT_DATE_FORMAT);
            String extension = "xml";
            file = FileUtils.getNewFile(folder.getCanonicalPath(), fileName, extension);
            if (log.isDebugEnabled()) {
                log.debug("the export file: " + file.getCanonicalPath());
            }
            os = new FileOutputStream(file);
            this.jaxb2Marshaller.marshal(biViewOrderList, new StreamResult(os));
        } catch (Exception e) {
            log.error("error when output export file:", e);
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ex) {
                    log.warn("Error in closing file: " + file.getAbsolutePath(), ex);
                }
            }
        }
    }
}
