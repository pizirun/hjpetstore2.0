/*
 * Pprun's Public Domain.
 */
package org.pprun.common.web.rest.client;

import javax.annotation.Resource;
import org.pprun.hjpetstore.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * This is a wrapper of {@literal Spring} {@link RestTemplate} has been injected
 * a {@code commonRestTemplate} and supplying {@literal GET}, {@literal POST} HTTP Method for client integration.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
@Component
public class SpringRestClient {
    private static final Logger log = LoggerFactory.getLogger(SpringRestClient.class);
    
    private RestTemplate restTemplate;

    @Resource(name = "commonRestTemplate")
    @Required
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T doGet(Class<T> responseType, String acceptMediaType, String acceptCharset, String restUri, Object... uriVariables) {
        HttpHeaders requestHeaders = createHttpHeader(acceptMediaType, acceptCharset);
        HttpEntity<T> response = null;
        try {
            HttpEntity<T> requestEntity = new HttpEntity(requestHeaders);
            response = restTemplate.exchange(restUri,
                    HttpMethod.GET,
                    requestEntity,
                    responseType,
                    uriVariables);
        } catch (Exception e) {
            log.error("An error when call to {}", restUri, e);
            throw new ServiceException("An error when call to " + restUri, e);
        }
        
        return response.getBody();
    }

    public <T> T doPost(Class<T> responseType, String acceptMediaType, String acceptCharset, String restUri, Object... uriVariables) {
        HttpHeaders requestHeaders = createHttpHeader(acceptMediaType, acceptCharset);

        HttpEntity<T> requestEntity = new HttpEntity(requestHeaders);
        HttpEntity<T> response = restTemplate.exchange(restUri,
                HttpMethod.POST,
                requestEntity,
                responseType,
                uriVariables);

        return response.getBody();
    }

    public <T> T doPut(Class<T> responseType, String acceptMediaType, String acceptCharset, String restUri, Object... uriVariables) {
        HttpHeaders requestHeaders = createHttpHeader(acceptMediaType, acceptCharset);

        HttpEntity<T> requestEntity = new HttpEntity(requestHeaders);
        HttpEntity<T> response = restTemplate.exchange(restUri,
                HttpMethod.PUT,
                requestEntity,
                responseType,
                uriVariables);

        return response.getBody();
    }
    
    public <T> T doDelete(Class<T> responseType, String acceptMediaType, String acceptCharset, String restUri, Object... uriVariables) {
        HttpHeaders requestHeaders = createHttpHeader(acceptMediaType, acceptCharset);

        HttpEntity<T> requestEntity = new HttpEntity(requestHeaders);
        HttpEntity<T> response = restTemplate.exchange(restUri,
                HttpMethod.DELETE,
                requestEntity,
                responseType,
                uriVariables);

        return response.getBody();
    }

    /**
     * help method create HTTP headers.
     * @return
     */
    private HttpHeaders createHttpHeader(String acceptMediaType, String acceptCharset) {
        HttpHeaders requestHeaders = new HttpHeaders();
        // Accept
        requestHeaders.set("Accept", acceptMediaType);
        requestHeaders.set("Accept-Charset", acceptCharset);

        return requestHeaders;
    }
}
