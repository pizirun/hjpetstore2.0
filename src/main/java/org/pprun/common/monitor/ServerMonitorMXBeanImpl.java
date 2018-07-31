/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;
import org.codehaus.jackson.map.ObjectMapper;
import org.pprun.common.security.ThrottleFilter;
import org.pprun.common.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

/**
 * monitor the general information of the server.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public class ServerMonitorMXBeanImpl implements ServerMonitorMXBean {
    private static final String UNKNOWN_HOST = "unknownHost";

    private static final String STARTUP_AT;

    // we set at the request time
    private static int port;
    
    static {
        STARTUP_AT = CalendarUtil.getDateString(Calendar.getInstance(), CalendarUtil.SIMPLE_DATE_FORMAT);
    }

    private ThrottleFilter throttleFilter;

    @Override
    public String getHostName() {
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return UNKNOWN_HOST;
        }
        return new StringBuilder(ia.getHostName()).append(":").append(ia.getHostAddress()).toString();
    }

    @Override
    public int getPort() {
        return port;
    }
    
    public static void setPort(int requestPort) {
        port = requestPort;
    }

    @Override
    public String getStartupAt() {
        return STARTUP_AT;
    }
    
    @Override
    public Set<String> getBlackList() {
        return throttleFilter.getBlackList();
    }

    @Override
    public String asJson() throws Exception {
        ObjectMapper om = new ObjectMapper();
        return new StringBuilder("serverMonitor=").append(om.writeValueAsString(this)).toString();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("serverMonitor=");
        s.append("{");
        s.append("hostName=").append(getHostName());
        s.append(", ");
        s.append("port=").append(getPort());
        s.append(", ");
        s.append("startupAt=").append(getStartupAt());
        s.append(", ");
        s.append("blackList=").append(getBlackList() == null ? "" : Arrays.toString(getBlackList().toArray(new String[0])));
        s.append("}");

        return s.toString();
    }
    
    @Required
    @Autowired
    public void setThrottleFilter(ThrottleFilter throttleFilter) {
        this.throttleFilter = throttleFilter;
    }
}
