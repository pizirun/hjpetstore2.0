package org.pprun.hjpetstore.persistence.jaxb.partner;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO represents a partner's card number to be validated by payment partner.
 * This POJO will be fed for {@link org.springframework.web.servlet.view.xml.MarshallingView}
 * in RESTful web service.
 *
 * <b>Please note that, in order to let the jaxb2 automatically bind work, the xml root element and its elements
 * can not be {@literal static} class</b>.
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@XmlRootElement(name = "PaymentValidationRequest")
public class PaymentValidationRequest {

    private String merchantName;
    private String cardNumber;
    private String hash;

    public void setMerchantName(String merchant) {
        this.merchantName = merchant;
    }

    public String getMerchantName() {
        return merchantName;
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

    /**
     * we will potentially have several payment partner and for each
     * we will be assigned a unique merchantName to identify hjpetstore in system ofpayment parter.
     * @param paymentPartner
     */
    public static String getMerchantNameByPaymentPartner(String paymentPartnerName) {

        return PaymentPartNameToMerchantName.get(paymentPartnerName);
    }
    private static final Map<String, String> PaymentPartNameToMerchantName = new HashMap<String, String>();
    private static final String CHINA_CMB = "招商银行";
    private static final String CHINA_ICBC = "工商银行";
    private static final String HJPETSTORE_CMB = "hjpetstore_cmb";
    private static final String HJPETSTORE_ICBC = "hjpetstore_icbc";

    static {
        PaymentPartNameToMerchantName.put(CHINA_CMB, HJPETSTORE_CMB);
        PaymentPartNameToMerchantName.put(CHINA_ICBC, HJPETSTORE_ICBC);
    }
}
