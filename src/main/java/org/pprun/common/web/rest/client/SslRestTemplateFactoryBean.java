/*
 * Pprun's Public Domain.
 */
package org.pprun.common.web.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

/**
 * This {@link FactoryBean} can be used to create Spring RestTemplate, which talks to any https with SSL certificates.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public class SslRestTemplateFactoryBean  implements FactoryBean<RestTemplate>{

    public static final String HTTPS = "https";
    private static final Logger log = LoggerFactory.getLogger(SslRestTemplateFactoryBean.class);
    // the keystore file 
    private Resource keyStoreFile;
    private String keyPassword;
    // truststore file, which contains all imported/trusted certificates
    private Resource trustStoreFile;
    private String trustStorePassword;
    // will be initialized in bean's init-method(initSslContext) and only once
    private RestTemplate restTemplate;
    private SSLContext sslContext;


    /**
     * The factory init method set up in bean container.
     * 
     */
    public void initSslContext() throws Exception {
        InputStream keyStoreStream = null;
        InputStream trustStoreStream = null;
        try {
            // It is expected that each call creates a <i>fresh</i> stream
            keyStoreStream = keyStoreFile.getInputStream();
            trustStoreStream = trustStoreFile.getInputStream();

            // 1. KeyManagerFactory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            KeyStore keyStore = KeyStore.getInstance("JKS");

            // load the keystore 
            keyStore.load(keyStoreStream, keyPassword.toCharArray());
            kmf.init(keyStore, keyPassword.toCharArray());

            // 2. TrustManagerFactory
            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(trustStoreStream, trustStorePassword.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            // init the trust manager factory by read certificates
            tmf.init(trustStore);

            // 3. init the SSLContext using kmf and tmf above
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            
            SSLContext.setDefault(sslContext);
        } catch (Exception ex) {
            log.error("Error in client config for SSH/HTTPS", ex);
            throw new IllegalStateException("Error in client config for SSH/HTTPS", ex);
        } finally {

            if (keyStoreStream != null) {
                try {
                    keyStoreStream.close();
                } catch (IOException ex) {
                    log.error("Exception in closing key store stream {}", keyStoreFile, ex);
                }
            }

            if (trustStoreStream != null) {
                try {
                    trustStoreStream.close();
                } catch (IOException ex) {
                    log.error("Exception in closing key store stream {}", trustStoreFile, ex);
                }
            }
        }
    }

    @Override
    public RestTemplate getObject() {
        return restTemplate;
    }

    @Override
    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }

    /**
     * Hope it is a singleTon to init sslContext only once for one app.
     * 
     * @return 
     */
    @Override
    public boolean isSingleton() {
        // hope it is singleton
        return true;
    }

    public void destory() {
        sslContext = null;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public void setKeyStoreFile(Resource keyStoreFile) {
        this.keyStoreFile = keyStoreFile;
    }

    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    public void setTrustStoreFile(Resource trustStoreFile) {
        this.trustStoreFile = trustStoreFile;
    }

    @Required
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
