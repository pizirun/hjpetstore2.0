/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

import java.util.Map;

/**
 * An interface to be implemented by JMX Bean to store and pull method invocation information.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface MethodInvocationMonitorMXBean {
    String print();
    void addStatistic(String name, long executionTime, boolean isSuccess);

    Map<String, MethodInvocation> getStatistics();
}
