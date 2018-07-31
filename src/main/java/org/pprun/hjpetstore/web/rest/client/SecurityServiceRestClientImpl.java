/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.web.rest.client;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.common.util.CalendarUtil;
import org.pprun.common.util.CommonUtil;
import org.pprun.common.util.MessageDigestUtil;
import org.pprun.hjpetstore.domain.PaymentPartner;
import org.pprun.hjpetstore.domain.RSAKey;
import org.pprun.hjpetstore.persistence.jaxb.DecryptCardNumber;
import org.pprun.hjpetstore.persistence.jaxb.EncryptCardNumber;
import org.pprun.hjpetstore.persistence.jaxb.PartnerDecyptCardNumber;
import org.pprun.hjpetstore.service.ServiceException;
import org.pprun.hjpetstore.service.UserService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

/**
 * A SecurityService REST client construct GET, POST request to call internal integration component - SecurityService.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SecurityServiceRestClientImpl implements SecurityServiceRestClient {

    private static Log log = LogFactory.getLog(SecurityServiceRestClientImpl.class);
    private RestTemplate restTemplate;
//    private String username;
//    private String password;
    private String apiKey;
    private String paymentPartnerUrl;
    private String rsaKeyUrl;
    private String encryptUrl;
    private String decryptUrl;
    private String encryptForPartnerUrl;
    private UserService userService;

    @Required
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Required
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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
    public void setRsaKeyUrl(String rsaKeyUrl) {
        this.rsaKeyUrl = rsaKeyUrl;
    }

    @Required
    public void setPaymentPartnerUrl(String paymentPartnerUrl) {
        this.paymentPartnerUrl = paymentPartnerUrl;
    }

    @Required
    public void setEncryptUrl(String encryptUrl) {
        this.encryptUrl = encryptUrl;
    }

    @Required
    public void setDecryptUrl(String decryptUrl) {
        this.decryptUrl = decryptUrl;
    }

    public void setEncryptForPartnerUrl(String encryptForPartnerUrl) {
        this.encryptForPartnerUrl = encryptForPartnerUrl;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public PaymentPartner getPaymentPartner(String name) {
        HttpHeaders requestHeaders = createHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity(requestHeaders);

        String url = buildURL(HttpMethod.GET, requestHeaders, paymentPartnerUrl, name);

        HttpEntity<PaymentPartner> response = restTemplate.exchange(url,
                HttpMethod.GET,
                requestEntity,
                PaymentPartner.class,
                name);

        PaymentPartner paymentPartner = response.getBody();

        return paymentPartner;
    }

    @Override
    public RSAKey getEnabledRSAKey() {
        HttpHeaders requestHeaders = createHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity(requestHeaders);

        String url = buildURL(HttpMethod.GET, requestHeaders, rsaKeyUrl);

        HttpEntity<RSAKey> response = restTemplate.exchange(url,
                HttpMethod.GET,
                requestEntity,
                RSAKey.class);

        RSAKey rsaKey = response.getBody();

        return rsaKey;
    }

    @Override
    public EncryptCardNumber encryptCardNumber(String cardNumber) {
        HttpHeaders requestHeaders = createHttpHeader();
        DecryptCardNumber decryptCardNumber = new DecryptCardNumber();
        decryptCardNumber.setCardNumber(cardNumber);
        HttpEntity<String> entity = new HttpEntity(decryptCardNumber, requestHeaders);

        String url = buildURL(HttpMethod.POST, requestHeaders, encryptUrl);

        EncryptCardNumber encryptCardNumber = restTemplate.postForObject(url, entity, EncryptCardNumber.class);
        return encryptCardNumber;

//        HttpHeaders requestHeaders = createHttpHeader();
//        DecryptCardNumber decryptCardNumber = new DecryptCardNumber();
//        decryptCardNumber.setCardNumber(cardNumber);
//        HttpEntity<String> entity = new HttpEntity(decryptCardNumber, requestHeaders);
//
//        HttpEntity<EncryptCardNumber> response = restTemplate.exchange(encryptUrl,
//                HttpMethod.POST,
//                entity,
//                EncryptCardNumber.class);
//
//        EncryptCardNumber encryptCardNumber = response.getBody();
//        return encryptCardNumber;
    }

    @Override
    public DecryptCardNumber decryptCardNumber(String cardNumber) {
        EncryptCardNumber encryptCardNumber = new EncryptCardNumber();
        encryptCardNumber.setCardNumber(cardNumber);
        HttpHeaders requestHeaders = createHttpHeader();
        HttpEntity<String> entity = new HttpEntity(encryptCardNumber, requestHeaders);

        String url = buildURL(HttpMethod.POST, requestHeaders, decryptUrl);

        DecryptCardNumber decryptCardNumber = restTemplate.postForObject(url, entity, DecryptCardNumber.class);
        return decryptCardNumber;

//        EncryptCardNumber encryptCardNumber = new EncryptCardNumber();
//        encryptCardNumber.setCardNumber(cardNumber);
//        HttpHeaders requestHeaders = createHttpHeader();
//        HttpEntity<String> entity = new HttpEntity(encryptCardNumber, requestHeaders);
//
//        HttpEntity<DecryptCardNumber> response = restTemplate.exchange(decryptUrl,
//                HttpMethod.POST,
//                entity,
//                DecryptCardNumber.class);
//
//        DecryptCardNumber decryptCardNumber = response.getBody();
//        return decryptCardNumber;
    }

    @Override
    public EncryptCardNumber encryptCardNumberForPartner(String partnerName, String cardNumber) {
        PartnerDecyptCardNumber partnerDecyptCardNumber = new PartnerDecyptCardNumber();
        partnerDecyptCardNumber.setPartnerName(partnerName);
        partnerDecyptCardNumber.setCardNumber(cardNumber);

        HttpHeaders requestHeaders = createHttpHeader();
        HttpEntity<String> entity = new HttpEntity(partnerDecyptCardNumber, requestHeaders);

        String url = buildURL(HttpMethod.POST, requestHeaders, encryptForPartnerUrl);

        EncryptCardNumber encryptCardNumber = restTemplate.postForObject(url, entity, EncryptCardNumber.class);
        return encryptCardNumber;
    }

    private String buildURL(HttpMethod httpVerb, HttpHeaders requestHeaders, String path, Object... urlVariables) throws ServiceException {
        // construct the url
        String originPath = path; // make a copy because the process still need parametered path outside this method,

        if (log.isDebugEnabled()) {
            log.debug("originPath path = " + path);
        }
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
