/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

import java.util.Set;

/**
 * monitor the general information of the server.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public interface ServerMonitorMXBean extends Statistic {

    String getHostName();

    int getPort();

    String getStartupAt();

    /**
     * The block IPs
     */
    Set<String> getBlackList();
}
