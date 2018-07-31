package org.pprun.hjpetstore.persistence.jaxb.partner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO represents a partner's card number to be charged by payment partner.
 * This POJO will be fed for {@link org.springframework.web.servlet.view.xml.MarshallingView}
 * in RESTful web service.
 *
 * <b>Please note that, in order to let the jaxb2 automatically bind work, the xml root element and its elements
 * can not be {@literal static} class</b>.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@XmlRootElement(name = "PaymentChargeRequest")
public class PaymentChargeRequest {

    private String merchant;
    private String cardNumber;
    private String hash;
    private BigDecimal amount;

    public void setMerchantName(String merchant) {
        this.merchant = merchant;
    }

    public String getMerchantName() {
        return merchant;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        // we set scale to 2
        amount.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
