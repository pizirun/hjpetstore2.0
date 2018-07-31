/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pprun.hjpetstore.web.rest.partner.client;

import java.math.BigDecimal;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentChargeResponse;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentValidationResponse;

/**
 * A Payment REST client construct POST request to mocked payment RESTful service, which in fact should be
 * supplied by payment partner.
 * <p>
 * All external method call will be through retry policy set in applicationContext, which is spring-batch based.
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface PaymentRestClient {

    PaymentChargeResponse charge(String cardNumber, BigDecimal amount, String paymentPartnerName);

    PaymentValidationResponse validateCardNumber(String cardNumber, String paymentPartnerName);

}
