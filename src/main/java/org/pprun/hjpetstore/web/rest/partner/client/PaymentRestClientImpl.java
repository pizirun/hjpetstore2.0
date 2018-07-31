/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.web.rest.partner.client;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.common.util.CalendarUtil;
import org.pprun.common.util.CommonUtil;
import org.pprun.common.util.MessageDigestUtil;
import org.pprun.hjpetstore.domain.PaymentPartner;
import org.pprun.hjpetstore.persistence.jaxb.EncryptCardNumber;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentChargeRequest;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentChargeResponse;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentValidationRequest;
import org.pprun.hjpetstore.persistence.jaxb.partner.PaymentValidationResponse;
import org.pprun.hjpetstore.service.ServiceException;
import org.pprun.hjpetstore.service.UserService;
import org.pprun.hjpetstore.service.rest.SecurityServiceException;
import org.pprun.hjpetstore.web.rest.client.SecurityServiceRestClient;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

/**
 * A Payment REST client construct POST request to mocked payment RESTful service, which in fact should be 
 * supplied by payment partner.
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class PaymentRestClientImpl implements PaymentRestClient {
    private static Log log = LogFactory.getLog(PaymentRestClientImpl.class);
    
    private RestTemplate restTemplate;
    private SecurityServiceRestClient securityServiceRestClient;
//    private String username;
//    private String password;
    private String apiKey;
    private String validateUrl;
    private String chargeUrl;
    private UserService userService;

    @Required
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Required
    public void setSecurityServiceRestClient(SecurityServiceRestClient securityServiceRestClient) {
        this.securityServiceRestClient = securityServiceRestClient;
    }

    @Required
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


//    @Required
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Required
//    public void setUsername(String username) {
//        this.username = username;
//    }


    @Required
    public void setChargeUrl(String chargeUrl) {
        this.chargeUrl = chargeUrl;
    }

    @Required
    public void setvalidateUrl(String validateUrl) {
        this.validateUrl = validateUrl;
    }

    @Override
    public PaymentValidationResponse validateCardNumber(String cardNumber, String paymentPartnerName) {
        // 1. create the encrypt card number
        // get payment partner
        PaymentPartner paymentPartner = securityServiceRestClient.getPaymentPartner(paymentPartnerName);

        // encrypt the card number for partner
        EncryptCardNumber encryptCardNumber = securityServiceRestClient.encryptCardNumberForPartner(paymentPartner.getName(), cardNumber);

        // 2. the merchantName
        String merchantName = PaymentValidationRequest.getMerchantNameByPaymentPartner(paymentPartner.getName());

        // 3. hash
        String cardNumberHash = null;
        try {
            cardNumberHash = MessageDigestUtil.sha512(cardNumber.getBytes(Charset.forName(CommonUtil.UTF8)));
        } catch (Exception ex) {
            log.error("SHA-512 card number error", ex);
            throw new SecurityServiceException("SHA-512 card number error ", ex);
        }

        // create validation request
        PaymentValidationRequest paymentValidationRequest = new PaymentValidationRequest();
        paymentValidationRequest.setCardNumber(encryptCardNumber.getCardNumber());
        paymentValidationRequest.setMerchantName(merchantName);
        paymentValidationRequest.setHash(cardNumberHash);

        HttpHeaders requestHeaders = createHttpHeader();
        HttpEntity<String> entity = new HttpEntity(paymentValidationRequest, requestHeaders);

        String url = buildURL(HttpMethod.POST, requestHeaders, validateUrl);

        PaymentValidationResponse paymentValidationResponse = restTemplate.postForObject(url, entity, PaymentValidationResponse.class);
        return paymentValidationResponse;

    }

    @Override
    public PaymentChargeResponse charge(String cardNumber, BigDecimal amount, String paymentPartnerName) {
        // 1. create the encrypt card number
        // get payment partner
        PaymentPartner paymentPartner = securityServiceRestClient.getPaymentPartner(paymentPartnerName);

        // encrypt the card number for partner
        EncryptCardNumber encryptCardNumber = securityServiceRestClient.encryptCardNumberForPartner(paymentPartner.getName(), cardNumber);

        // 2. the merchantName
        String merchantName = PaymentValidationRequest.getMerchantNameByPaymentPartner(paymentPartner.getName());

        // 3. hash
        String cardNumberHash = null;
        try {
            cardNumberHash = MessageDigestUtil.sha512(cardNumber.getBytes(Charset.forName(CommonUtil.UTF8)));
        } catch (Exception ex) {
            log.error("SHA-512 card number error", ex);
            throw new SecurityServiceException("SHA-512 card number error ", ex);
        }

        // create Charge request
        PaymentChargeRequest paymentChargeRequest = new PaymentChargeRequest();
        paymentChargeRequest.setCardNumber(encryptCardNumber.getCardNumber());
        paymentChargeRequest.setMerchantName(merchantName);
        paymentChargeRequest.setHash(cardNumberHash);
        paymentChargeRequest.setAmount(amount);

        HttpHeaders requestHeaders = createHttpHeader();
        HttpEntity<String> entity = new HttpEntity(paymentChargeRequest, requestHeaders);

        String url = buildURL(HttpMethod.POST, requestHeaders, chargeUrl);

        PaymentChargeResponse paymentChargeResponse = restTemplate.postForObject(url, entity, PaymentChargeResponse.class);
        
        //if (true) {
        //    throw new RestClientException("mocked RestClientException for retry test");
        //}
        return paymentChargeResponse;
    }

    private String buildURL(HttpMethod httpVerb, HttpHeaders requestHeaders, String path, Object... urlVariables) throws ServiceException {
        // construct the url
        String originPath = path; // make a copy because the process still need parametered path outside this method,
        if (urlVariables != null) {
            // if the path contains variable, expand it
            // http://localhost:8080/hjpetstore/rest/paymentpartner/{name}

            UriTemplate uriTemplate = new UriTemplate(path);
            URI expanded = uriTemplate.expand(urlVariables);
            path = expanded.getRawPath();
            if (log.isDebugEnabled()) {
                log.debug("expanded path = " + path);
            }
        }
        
        String httpMethod = httpVerb.toString();
        String date = requestHeaders.getFirst("Date");
        String secretKey = userService.getUserSecretKeyForApiKey(apiKey);
        String signature = MessageDigestUtil.calculateSignature(httpMethod, date, path, secretKey);
        try {
            StringBuilder s = new StringBuilder();
            s.append(originPath);
            s.append("?apiKey=");
            s.append(URLEncoder.encode(apiKey, CommonUtil.UTF8));
            s.append("&signature=");
            s.append(URLEncoder.encode(signature, CommonUtil.UTF8));
            if (log.isDebugEnabled()) {
                log.debug(s.toString());
            }

            return s.toString();
        } catch (UnsupportedEncodingException ex) {
            throw new ServiceException("UnsupportedEncodingException when URLEncoder.encode: " + CommonUtil.UTF8, ex);
        }
    }

    /**
     * help method create HTTP headers.
     * @return
     */
    private HttpHeaders createHttpHeader() {
        HttpHeaders requestHeaders = new HttpHeaders();
        // Accept
        requestHeaders.set("Accept", "application/xml");
        requestHeaders.set("Accept-Charset", CommonUtil.UTF8);

        // Date
        String date = CalendarUtil.getDateStringWithZone(Calendar.getInstance(), CalendarUtil.ZONE_DATE_FORMAT_WITH_WEEK, TimeZone.getTimeZone(CommonUtil.TIME_ZONE_UTC), Locale.US);
        requestHeaders.set("Date", date);

        return requestHeaders;
    }
// now use OAuth, instead of Basic Authentication
//    /**
//     * help method create HTTP headers.
//     * @return
//     */
//    private HttpHeaders createHttpHeader() {
//        HttpHeaders requestHeaders = new HttpHeaders();
//        // Accept
//        requestHeaders.set("Accept", "application/xml");
//        requestHeaders.set("Accept-Charset", CommonUtil.UTF8);
//
//        // Authorization
//        // The use of non-ASCII characters in HTTP head elements (such
//        // as headers or a request line) is a violation of the HTTP specification.
//        // You can explicitly override the standard charset with a non-standard one
//        // such as UTF-8 or GBK by setting the 'http.protocol.element-charset'
//        // parameter, but I do not think HttpClient should attempt to 'guess' the
//        // charset being used.
//        String userPassword = username + ":" + password;
//        String encoding = new String(Base64.encodeBase64(userPassword.getBytes()), Charset.forName("us-ascii"));
//        requestHeaders.set("Authorization", "Basic " + encoding);
//
//        return requestHeaders;
//    }
}
