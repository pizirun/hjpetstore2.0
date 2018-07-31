/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.web.rest.supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.service.jms.supplier.SupplierResponseQueueSender;
import org.pprun.hjpetstore.persistence.jaxb.supplier.SupplierOrderStatusRequest;
import org.pprun.hjpetstore.persistence.jaxb.supplier.SupplierOrderStatusResponse;
import org.pprun.hjpetstore.web.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This is mock RESTful service at supplier side to response a jms message
 * response back to hjpetstore to update order status.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Controller
public class SupplierResponseController extends BaseController {

    private static final Log log = LogFactory.getLog(SupplierResponseController.class);
    private SupplierResponseQueueSender supplierResponseQueueSender;

    @Required
    @Autowired
    public void setSupplierResponseQueueSender(SupplierResponseQueueSender supplierResponseQueueSender) {
        this.supplierResponseQueueSender = supplierResponseQueueSender;
    }

    /**
     * RESTful PUT supplier order response status to '/supplier/response .
     *
     * <p>
     * For example: user pprun <br />
     * {@code 
     * curl -H 'Content-Type: application/xml' -H 'Accept: application/xml' -H 'Accept-Charset: UTF-8' -d '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><SupplierOrderStatusRequest><orderId>1</orderId><status>IN_TRANSIT</status><supplier>Beijing Little Pet Supplier</supplier></SupplierOrderStatusRequest>' -X PUT 'http://localhost:8080/hjpetstore/rest/supplier/response'
     * }
     *
     * @param apiKey
     * @param keyword
     * @return
     */
    @RequestMapping(value = "/supplier/response", method = RequestMethod.PUT)
    public ModelAndView responseOrderStatus(
            @RequestBody SupplierOrderStatusRequest supplierOrderStatusRequest) {

        long orderId = supplierOrderStatusRequest.getOrderId();
        String status = supplierOrderStatusRequest.getStatus();
        String supplier = supplierOrderStatusRequest.getSupplier();

        log.info("SupplierResponseController is processing request: orderId =" + orderId
                + ", status= " + status
                + ", supplier= " + supplier);

        SupplierOrderStatusResponse.Result result = SupplierOrderStatusResponse.Result.SUCCESS;

        try {
            supplierResponseQueueSender.responseOrderStatus(supplierOrderStatusRequest);
        } catch (Throwable t) {
            log.error("SupplierResponseController - failed to send responseOrderStatus");
            result = SupplierOrderStatusResponse.Result.FAILED;
        }

        SupplierOrderStatusResponse response = new SupplierOrderStatusResponse();

        response.setResult(result);

        ModelAndView mav = new ModelAndView("supplierResponse");
        mav.addObject(response);
        return mav;
    }
}
