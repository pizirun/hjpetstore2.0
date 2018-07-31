/*
 * Pprun's Public Domain.
 */
package org.pprun.common.security;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * A filter to throttle only a limited number of requests from the same ip per second.
 * <p>
 * Two parameters need to inject:
 * <ol>
 * <li>common.throttle.maxConcurrentRequests=10</li>
 * <li>common.throttle.period=1000</li>
 * </ol>
 * <br />
 * If you use {@literal Spring} for {@lit DI}, it can be done as below in application {@literal web.xml}:
 * <br />
 * {@code 
 *      <filter>  
 *          <description>A filter to throttle only a limited number of requests from the same ip per second.</description>  
 *          <filter-name>throttleFilter</filter-name>  
 *          <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>  
 *      </filter>
 * }
 * <br />
 * And a bean with {@code id="throttleFilter"} should be defined in {@literal application.xml}.
 * </p>
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
//@Component
public class ThrottleFilter implements Filter, ThrottleFilterMXBean {

    private static Logger log = LoggerFactory.getLogger(ThrottleFilter.class);
    // map(ip, requestCount)
    private Map<String, Integer> ip2countCache = new HashMap<String, Integer>();
    private Set<String> blackList = new HashSet<String>();
    private int maxConcurrentRequests;
    private static final long PERIOD = 1L; // second
    private boolean enable = false;

    @Override
    public void init(FilterConfig config) throws ServletException {
        // nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain nextFilter) throws ServletException, IOException {
        if (enable) {
            final String ip = request.getRemoteAddr();
            boolean isOverflow;

            synchronized (this) {
                Integer count = ip2countCache.get(ip);

                if (count == null || count.intValue() == 0) {
                    count = 0;
                }

                if (count < maxConcurrentRequests) {
                    isOverflow = false;
                    ip2countCache.put(ip, count + 1);
                } else {
                    isOverflow = true;
                    blackList.add(ip);
                }
            }

            if (isOverflow) {
                // block it
                log.info(" ip {} has reached the threshold {} in {} second, block it!", new Object[]{ip, maxConcurrentRequests, PERIOD});

                if (response instanceof HttpServletResponse) {
                    ((HttpServletResponse) response).sendError(503, ip + " has too many concurrent requests per " + PERIOD + " second");
                }
                return;
            }
        } // else go ahead below

        // every thing is okay, go ahead
        nextFilter.doFilter(request, response);
    }

    // every 1 second
    @Scheduled(fixedRate = PERIOD * 1000)
    public void throttlingJob() {
        if (enable) {
            log.debug("Throttle Filter clean up job is running");
            synchronized (ThrottleFilter.this) {
                for (Map.Entry<String, Integer> ip2count : ip2countCache.entrySet()) {
                    Integer count = ip2count.getValue();
                    String ip = ip2count.getKey();

                    if (count == null || count <= 1) {
                        ip2countCache.remove(ip);
                    } else {
                        if (count == maxConcurrentRequests) {
                            // remove from blacklist
                            log.info("Throttle Filter: removing {} from black list", ip);
                            blackList.remove(ip);
                        }

                        ip2countCache.put(ip, count - 1);
                    }
                }
            }
            log.debug("Throttle Filter clean up job is done");
        }
    }

    /**
     * Any cleanup for the filter.
     */
    @Override
    public void destroy() {
        log.warn("destorying Throttle Filter");
    }

    /**
     * Sets the maximum number of concurrent requests for a single IP.
     */
    @Required
    public void setMaxConcurrentRequests(int max) {
        maxConcurrentRequests = max;
    }

    @ManagedAttribute(description = "is the throttle filter enable or not")
    @Override
    public boolean isEnable() {
        return enable;
    }

    @Required
    @Override
    public void setEnable(boolean enable) {
        log.info("set enable to {}", enable);
        if (enable == false) {
            log.info("Throttle filter will be disabled");
        }

        this.enable = enable;
    }

    public Set<String> getBlackList() {
        // for exactly, it might need synchronized, but no hurt for snapshoot in one or severl seconds
        return Collections.unmodifiableSet(blackList);
    }
}
