/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.web.rest.partner;

import java.io.File;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.common.util.CommonUtil;
import org.pprun.common.util.FileUtils;
import org.pprun.common.util.MessageDigestUtil;
import org.pprun.common.util.RSAUtil;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentChargeRequest;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentChargeResponse;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentValidationRequest;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentValidationResponse;
import org.pprun.hjpetstore.service.rest.SecurityServiceException;
import org.pprun.hjpetstore.web.rest.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * This is RESTful controller mocking the partner web service interface that hjpetstore will integrating with.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@Controller
public class PaymentController extends BaseController {

    private static final Log log = LogFactory.getLog(PaymentController.class);
    private Resource privateKeyFile;

    @Required
    @Autowired
    public void setPrivateKeyFile(Resource privateKeyFile) {
        this.privateKeyFile = privateKeyFile;
    }

    /**
     * Restful POST the cardNumber to validate by payment partner.
     * RequestBody will use the registered messageConverter - MarshallingHttpMessageConverter, which is set up in AnnotationMethodHandlerAdapter.
     * <p>
     * For example: user pprun for card number: 999 9999 9999 9999<br />
     * {@code
     * curl -H 'Content-Type: application/xml' -H 'Accept: application/xml' -H 'Accept-Charset: UTF-8' -d '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><PaymentValidationRequest><merchant>hjpetstore_cmb</merchant><cardNumber>QTwTJZPjv3klTqFEb3Th7mLPVuCCaDhRCidMPTvI1RwZ0FwNj+scUxSD+D1wG/586Lp6WFxqh7y0VWKYj2/YPmg3ZK3mVpI0FKVTcyUDmw6eUQ1207pGBI0aw1WLgBPLGpBcI40bcDLkbJbL7LAMvrOxbmndc/eT4ZA2uvT0Rag=</cardNumber><hash>4836e18fa1d07a8f69df17012f923b461f0ac56022044e15f3af67715d7999059c1bd04c3619511c2bc543edec5cf98cab78fab91ae84eb7bae867c6ed628017</hash></PaymentValidationRequest>' 'http://localhost:8080/hjpetstore/rest/payment/validate'
     * }
     *
     * @param cardNumber the plain/decrypt card number
     */
    @RequestMapping(value = "/payment/validate", method = RequestMethod.POST)
    public ModelAndView validateCardNumber(@RequestBody PaymentValidationRequest paymentValidationRequest) {
        // based on merchantName, the payment partner will retrieve the private key on their side
        // we simply hard code here

        String merchantName = paymentValidationRequest.getMerchantName();
        // validate the merchantName is valid as need ...

        if (log.isDebugEnabled()) {
            log.debug("processing validateCardNumber request for merchant:" + merchantName);
        }

        // get private key for the partner
        PrivateKey privateKey = null;
        try {
            File file = privateKeyFile.getFile();
            String privateKeyString = FileUtils.readFileAsString(file);
            privateKey = RSAUtil.getPrivateKeyFromString(privateKeyString);

        } catch (Exception ex) {
            log.error("Reading private key file error", ex);
            throw new SecurityServiceException("Reading private key file error ", ex);
        }

        // decypt the cardNumber
        String cardNumber = null;
        try {
            String encryptCardNumber = paymentValidationRequest.getCardNumber();

            // here we can not call our internal security service 
            // because this cardNumber was encrypted by payment partner public key
            // this is a moack for external system integration
            cardNumber = RSAUtil.decrypt(encryptCardNumber, privateKey);
        } catch (Exception ex) {
            log.error("decypt card number error", ex);
            throw new SecurityServiceException("decypt card number error ", ex);
        }

        // compare the generated hash with the hash passed to ensure the information
        // was not intercepted and tampered in the middle way
        String cardNumberHash = null;
        try {
            cardNumberHash = MessageDigestUtil.sha512(cardNumber.getBytes(Charset.forName(CommonUtil.UTF8)));
        } catch (Exception ex) {
            log.error("SHA-512 card number error", ex);
            throw new SecurityServiceException("SHA-512 card number error ", ex);
        }

        String hash = paymentValidationRequest.getHash();

        boolean valid = true;
        if (cardNumberHash.equals(hash) == false) {
            // return a response code is more helpful
            log.error("The card is not a valid in china-cmb system");
            valid = false;
        }

        ModelAndView mav = new ModelAndView("validateCardNumber");
        PaymentValidationResponse response = new PaymentValidationResponse();
        response.setValid(valid);
        mav.addObject(response);

        return mav;
    }

    /**
     * Restful POST card number information to charge by payment partner.
     * RequestBody will use the registered messageConverter - MarshallingHttpMessageConverter, which is set up in AnnotationMethodHandlerAdapter.
     * <p>
     * For example: user pprun for card number: 999 9999 9999 9999<br />
     * {@code
     * curl -H 'Content-Type: application/xml' -H 'Accept: application/xml' -H 'Accept-Charset: UTF-8' -d '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><PaymentChargeRequest><merchant>hjpetstore_cmb</merchant><cardNumber>QTwTJZPjv3klTqFEb3Th7mLPVuCCaDhRCidMPTvI1RwZ0FwNj+scUxSD+D1wG/586Lp6WFxqh7y0VWKYj2/YPmg3ZK3mVpI0FKVTcyUDmw6eUQ1207pGBI0aw1WLgBPLGpBcI40bcDLkbJbL7LAMvrOxbmndc/eT4ZA2uvT0Rag=</cardNumber><hash>4836e18fa1d07a8f69df17012f923b461f0ac56022044e15f3af67715d7999059c1bd04c3619511c2bc543edec5cf98cab78fab91ae84eb7bae867c6ed628017</hash><amount>100.00</amount></PaymentChargeRequest>' 'http://localhost:8080/hjpetstore/rest/payment/charge'
     * }
     *
     * @param cardNumber the plain/decrypt card number
     */
    @RequestMapping(value = "/payment/charge", method = RequestMethod.POST)
    public ModelAndView charge(@RequestBody PaymentChargeRequest paymentChargeRequest) {
        // based on merchantName, the payment partner will retrieve the private key on their side
        // we simply hard code here

        String merchantName = paymentChargeRequest.getMerchantName();
        // validate the merchantNamee is valid as need ...

        if (log.isDebugEnabled()) {
            log.debug("processing charge request for merchant:" + merchantName);
        }

        // get private key of the partner
        PrivateKey privateKey = null;
        try {
            File file = privateKeyFile.getFile();
            String privateKeyString = FileUtils.readFileAsString(file);
            privateKey = RSAUtil.getPrivateKeyFromString(privateKeyString);
        } catch (Exception ex) {
            log.error("Reading private key file error", ex);
            throw new SecurityServiceException("Reading private key file error ", ex);
        }

        // decypt the cardNumber
        String cardNumber = null;
        try {
            String encryptCardNumber = paymentChargeRequest.getCardNumber();

            // here we can not call our internal security service
            // because this cardNumber was encrypted by payment partner public key
            // this is a mock for external system integration
            cardNumber = RSAUtil.decrypt(encryptCardNumber, privateKey);
        } catch (Exception ex) {
            log.error("decypt card number error", ex);
            throw new SecurityServiceException("decypt card number error ", ex);
        }

        // compare the generated hash with the hash passed to ensure the information
        // was not intercepted and tampered in the middle way
        String cardNumberHash = null;
        try {
            cardNumberHash = MessageDigestUtil.sha512(cardNumber.getBytes(Charset.forName(CommonUtil.UTF8)));
        } catch (Exception ex) {
            log.error("SHA-512 card number error", ex);
            throw new SecurityServiceException("SHA-512 card number error ", ex);
        }

        String hash = paymentChargeRequest.getHash();

        PaymentChargeResponse.ChargeStatus status = null;
        String failureReason = null;

        if (cardNumberHash.equals(hash) == false) {
            // not a valid card
            // return a response code is more helpful
            String msg = "The card is not a valid in china-cmb system";
            log.error(msg);
            status = PaymentChargeResponse.ChargeStatus.FAILED;
            failureReason = msg;
        } else {
            // compared the cardNumber with system internal records...
            //
            // try to charge it:
            // success if everything goes fine, otherwise failed with failureReason
            // we just mock it randomly
            //boolean success = new Random().nextBoolean();
            boolean success = true;
            if (success) {
                if (log.isDebugEnabled()) {
                    log.debug("moacked charge result = " + PaymentChargeResponse.ChargeStatus.SUCCESS.toString());
                }
                status = PaymentChargeResponse.ChargeStatus.SUCCESS;
                // failureReason is null
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("moacked charge result = " + PaymentChargeResponse.ChargeStatus.FAILED.toString());
                }

                String msg = "Charged failed, may be the balance is too less";
                log.error(msg);
                status = PaymentChargeResponse.ChargeStatus.FAILED;
                failureReason = msg;
            }
        }

        PaymentChargeResponse response = new PaymentChargeResponse();
        response.setStatus(status);
        response.setFailureReason(failureReason);

        ModelAndView mav = new ModelAndView("charge");
        mav.addObject(response);
        return mav;
    }
}
