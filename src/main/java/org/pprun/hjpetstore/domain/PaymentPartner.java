/*
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.domain;

import javax.xml.bind.annotation.XmlRootElement;
import org.pprun.hjpetstore.persistence.DomainObject;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@XmlRootElement(name="paymentPartner")
public class PaymentPartner extends DomainObject {
    public enum PaymentPartnerStatus {

        ACTIVE("ACTIVE"),
        INACTIVE("INACTIVE");
        private final String status;

        private PaymentPartnerStatus(String status) {
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

    private String name;
    private PaymentPartnerStatus status = PaymentPartnerStatus.ACTIVE;
    private String publicKey;
    private String phone;

    /** Creates a new instance of Supplier */
    public PaymentPartner() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentPartnerStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentPartnerStatus status) {
        this.status = status;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(this.getClass().getSimpleName());
        s.append("[");
        s.append("id=").append(id);
        s.append(", ");
        s.append("version=").append(version);
        s.append(", ");
        s.append("name=").append(name);
        s.append("]");

        return s.toString();
    }
}
