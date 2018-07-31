/*
 * Pprun's Public Domain.
 */
package org.pprun.common.web.rest.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

/**
 * This {@link FactoryBean} create the <a href="http://jersey.java.net/">Jersey Client</a>, which can be used to talks 
 * to any https with SSL certificates.
 * 
 * <p>
 * Jersey is the open source, production quality, 
 * JAX-RS (<a href="http://jsr311.dev.java.net/nonav/releases/1.1/index.html">JSR 311</a>) Reference Implementation for building RESTful Web services.
 * </p>
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public class JerseySslRestClientFactoryBean implements FactoryBean<Client> {

    private static final Logger log = LoggerFactory.getLogger(JerseySslRestClientFactoryBean.class);
    // the keystore file 
    private Resource keyStoreFile;
    private String keyPassword;
    // truststore file, which contains all imported/trusted certificates
    private Resource trustStoreFile;
    private String trustStorePassword;
    // will be initialized in bean's init-method(initSslContext) and only once
    private ClientConfig clientConfig;
    private SSLContext sslContext;
    private int connectTimeout;
    private int readTimeout;

    /**
     * The factory init method set up in bean container.
     * 
     */
    public void initSslContext() {

        InputStream keyStoreStream = null;
        InputStream trustStoreStream = null;
        try {
//            System.setProperty("javax.net.ssl.keyStore", keyStoreFile.getFile().getAbsolutePath());
//            System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
//            System.setProperty("javax.net.ssl.trustStore", trustStoreFile.getFile().getAbsolutePath());

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
            sslContext.init(null, tmf.getTrustManagers(), new SecureRandom());
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
    public Client getObject() {
        if (clientConfig == null) {
            clientConfig = new DefaultClientConfig();
            clientConfig.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(null, sslContext));
        }
        Client client = Client.create(clientConfig);
        client.setConnectTimeout(connectTimeout);
        client.setReadTimeout(readTimeout);
        return client;
    }

    @Override
    public Class<Client> getObjectType() {
        return Client.class;
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

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}
