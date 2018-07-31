/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.persistence.jaxb.supplier;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This POJO will be fed for {@link org.springframework.web.servlet.view.xml.MarshallingView} while marshaller
 * in RESTful web service.
 *
 * <b>Please note that, in order to let the jaxb2 automatically bind work, the xml root element and its elements
 * can not be {@literal static} class</b>.
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@XmlRootElement(name = "SupplierOrderStatusRequest")
public class SupplierOrderStatusRequest {

    public enum Status {

        IN_TRANSIT("IN_TRANSIT"),
        CANCELLED("CANCELLED"),
        COMPLETE("COMPLETE");
        private final String status;

        private Status(String status) {
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
    private long orderId;
    private String status;
    private String supplier;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
