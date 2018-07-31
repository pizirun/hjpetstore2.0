/*
 * Pprun's Public Domain.
 */
package org.pprun.common.util;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate Adapter to set {@link HttpClient} {@link Credentials}. And can add SSL support as need.
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class RestCredentialsTemplate {

    private final RestTemplate restTemplate;
    private Credentials credentials;

    public RestCredentialsTemplate(RestTemplate restTemplate, Credentials credentials) {
        this.restTemplate = restTemplate;
        this.credentials = credentials;
        CommonsClientHttpRequestFactory factory = (CommonsClientHttpRequestFactory) restTemplate.getRequestFactory();
        factory.getHttpClient().getState().setCredentials(AuthScope.ANY, credentials);
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
