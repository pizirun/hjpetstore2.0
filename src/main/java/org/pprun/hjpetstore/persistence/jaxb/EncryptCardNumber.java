package org.pprun.hjpetstore.persistence.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO represents a encrypt card number to be fed for {@link org.springframework.web.servlet.view.xml.MarshallingView}
 * in RESTful web service.
 *
 * <b>Please note that, in order to let the jaxb2 automatically bind work, the xml root element and its elements
 * can not be {@literal static} class</b>.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@XmlRootElement(name="EncryptCardNumber")
public class EncryptCardNumber {

    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
}
