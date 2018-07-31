/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.web.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.hjpetstore.domain.PaymentPartner;
import org.pprun.hjpetstore.domain.RSAKey;
import org.pprun.hjpetstore.persistence.jaxb.DecryptCardNumber;
import org.pprun.hjpetstore.persistence.jaxb.EncryptCardNumber;
import org.pprun.hjpetstore.persistence.jaxb.PartnerDecyptCardNumber;
import org.pprun.hjpetstore.service.rest.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This is RESTful controller expose the security service to internal components.
 * In fact, this service should be deployed into a separated box for security consideration, such as PCI.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Controller
public class SecurityController extends BaseController {
    private static final Log log = LogFactory.getLog(SecurityController.class);
    private SecurityService securityService;

    @Required
    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * Restful GET a PaymentPartner by name.
     *
     * <p>
     * For example: user pprun <br />
     * {@code
     * curl -H 'Content-Type: application/xml' -H 'Accept: application/xml' 'http://localhost:8080/hjpetstore/rest/paymentpartner/招商银行'
     * }
     *
     * @return
     */
    @RequestMapping(value = "/paymentpartner/{name}", method = RequestMethod.GET)
    public ModelAndView getPaymentPartner(@PathVariable("name") String name) {
        log.info("get paymentPartner for partner" + name);

        PaymentPartner paymentPartner = securityService.getPaymentPartner(name);

        ModelAndView mav = new ModelAndView("paymentPartner");
        mav.addObject(paymentPartner);
        return mav;
    }

    /**
     * Restful GET the enabled RSAKeyPair used in system.
     *
     * <p>
     * For example: user pprun <br />
     * {@code
     * curl -H 'Content-Type: application/xml' -H 'Accept: application/xml' 'http://localhost:8080/hjpetstore/rest/rsakey'
     * }
     *
     */
    @RequestMapping(value = "/rsakey", method = RequestMethod.GET)
    public ModelAndView getEnabledRSAKey() {
        log.info("get Enabled RSA key");

        RSAKey rsaKey = securityService.getEnabledRSAKey();

        ModelAndView mav = new ModelAndView("rsaKey");
        mav.addObject(rsaKey);
        return mav;
    }

    /**
     * Restful POST cardNumber to encrypt.
     * RequestBody will use the registered messageConverter - MarshallingHttpMessageConverter, which is set up in AnnotationMethodHandlerAdapter.
     * <p>
     * For example: user pprun <br />
     * {@code
     * curl -H 'Content-Type: application/xml' -H 'Accept: application/xml' -H 'Accept-Charset: UTF-8' -d '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><DecryptCardNumber><cardNumber>0987654321</cardNumber></DecryptCardNumber>' 'http://localhost:8080/hjpetstore/rest/encrypt'
     * }
     *
     * @param cardNumber the plain/decrypt card number
     */
    @RequestMapping(value = "/encrypt", method = RequestMethod.POST)
    public ModelAndView encryptCardNumber(@RequestBody DecryptCardNumber cardNumber) {
        log.info("encrypt card number: " + cardNumber.getCardNumber());

        String encryptCardNumber = securityService.encryptCardNumber(cardNumber.getCardNumber());

        EncryptCardNumber ecn = new EncryptCardNumber();
        ecn.setCardNumber(encryptCardNumber);

        ModelAndView mav = new ModelAndView("encryptCardNumber");
        mav.addObject(ecn);

        return mav;
    }

    /**
     * Restful POST cardNumber to decrypt.
     *
     * <p>
     * For example: user pprun <br />
     * {@code
     * curl -H 'Content-Type: application/xml' -H 'Accept: application/xml' -H 'Accept-Charset: UTF-8' -d '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><EncryptCardNumber><cardNumber>FWlHkYFEy9yAa4Zwq9hOBcoMbRlLCvLA8FDD//RyEDORVQAAxDeZcBdeRTxDtd0agpvNHEYd70jYLkPpSmG1v/yr+n3wYpY4LR+5AVu3dRcKDM4ZtfrjGG408skSOF505zcOp5Z0B9rtrWaSE0fkGptSQd3iTRY4E6Mkf00f5ms=</cardNumber></EncryptCardNumber>' 'http://localhost:8080/hjpetstore/rest/decrypt'
     * }
     *
     * @param cardNumber the encrypt card number
     */
    @RequestMapping(value = "/decrypt", method = RequestMethod.POST)
    public ModelAndView decryptCardNumber(@RequestBody EncryptCardNumber cardNumber) {
        log.info("decrypt cardNumber: " + cardNumber.getCardNumber());

        String decryptCardNumber = securityService.decryptCardNumber(cardNumber.getCardNumber());

        DecryptCardNumber dcn = new DecryptCardNumber();
        dcn.setCardNumber(decryptCardNumber);

        ModelAndView mav = new ModelAndView("decryptCardNumber");
        mav.addObject(dcn);

        return mav;
    }

    /**
     * Restful POST cardNumber to encrypt by partner public key.
     * RequestBody will use the registered messageConverter - MarshallingHttpMessageConverter, which is set up in AnnotationMethodHandlerAdapter.
     * <p>
     * For example: user pprun <br />
     * {@code
     * curl -H 'Content-Type: application/xml' -H 'Accept: application/xml' -H 'Accept-Charset: UTF-8' -d '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><PartnerDecyptCardNumber><cardNumber>0987654321</cardNumber><partnerName>招商银行</partnerName></PartnerDecyptCardNumber>' 'http://localhost:8080/hjpetstore/rest/encrypt/partner'
     * }
     *
     * @param partnerCardNumber the plain/decrypt card number
     */
    @RequestMapping(value = "/encrypt/partner", method = RequestMethod.POST)
    public ModelAndView encryptCardNumberForPartner(@RequestBody PartnerDecyptCardNumber partnerCardNumber) {
        log.info("encrypt cardNumber '" + partnerCardNumber.getCardNumber() + "' for partner: " + partnerCardNumber.getPartnerName());

        String encryptCardNumber = securityService.encryptCardNumberForPartner(
                partnerCardNumber.getPartnerName(), partnerCardNumber.getCardNumber());

        EncryptCardNumber ecn = new EncryptCardNumber();
        ecn.setCardNumber(encryptCardNumber);

        ModelAndView mav = new ModelAndView("encryptCardNumber");
        mav.addObject(ecn);

        return mav;
    }
}
