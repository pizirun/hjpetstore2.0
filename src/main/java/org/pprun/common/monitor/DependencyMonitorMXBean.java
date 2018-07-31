/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

/**
 * check the status of the depenent component.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public interface DependencyMonitorMXBean extends Statistic {
    String getName();
    boolean isLiving();
}
