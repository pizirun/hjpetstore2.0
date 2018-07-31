/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.persistence.jaxb.partner;

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
@XmlRootElement(name = "PaymentChargeResponse")
public class PaymentChargeResponse {

    public enum ChargeStatus {

        FAILED("FAILED"),
        SUCCESS("SUCCESS");
        private final String status;

        private ChargeStatus(String status) {
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
    
    private ChargeStatus status;
    /** can be null if success */
    private String failureReason;

    public ChargeStatus getStatus() {
        return status;
    }

    public void setStatus(ChargeStatus status) {
        this.status = status;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getFailureReason() {
        return failureReason;
    }
}
