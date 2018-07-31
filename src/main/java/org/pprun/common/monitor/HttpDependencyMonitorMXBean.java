/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * This monitor checks the status of http server by simply and practicly make an {@code HTTP HEAD Method} connection.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public class HttpDependencyMonitorMXBean implements DependencyMonitorMXBean {

    private static final Logger log = LoggerFactory.getLogger(HttpDependencyMonitorMXBean.class);
    private String url;
    private int timeout; // in ms
    private String name;
    
    @Override
    public boolean isLiving() {
        boolean result = true;
        log.debug("Starting to ping to {}", url);
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                log.debug("return HTTP response code: {}", responseCode);
                result = false;
            }

        } catch (IOException ex) {
            log.info("ping {} failed.", url, ex);
            result = false;
        }

        return result;
    }

    @Override
    public String asJson() throws Exception {
        ObjectMapper om = new ObjectMapper();
        return new StringBuilder("dependency.").
                append(name).
                append(": ").
                append(url).
                append("=").
                append(om.writeValueAsString(this)).toString();
    }

    @Override
    public String toString() {
        return new StringBuilder("dependency.").
                append(name).
                append(": ").
                append(url).
                append(".isLiving=").
                append(isLiving()).toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Required
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @Required
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @JsonIgnore
    @Required
    public void setUrl(String url) {
        this.url = url;
    }
}
