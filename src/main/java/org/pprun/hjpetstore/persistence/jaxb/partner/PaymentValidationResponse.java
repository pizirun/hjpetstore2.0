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
@XmlRootElement(name = "PaymentValidationResponse")
public class PaymentValidationResponse {
    private boolean valid;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
