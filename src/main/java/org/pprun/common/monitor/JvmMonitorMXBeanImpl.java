/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * A simple Bean implementation for monitoring the JVM memory and thread information.
 * It intends to be exposed as JMX Bean.
 * <p>
 * <b>This class is immutable class</b>
 * </p>
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
@ManagedResource(objectName = "org.pprun.common.monitor:name=SimpleJvmMonitor",
description = "A simple JvmMonitor Monitor Bean which retrieves information from Runtime and ThreadGroup")
public final class JvmMonitorMXBeanImpl implements JvmMonitorMXBean {

    private static final int MB = 1024 * 1024;

    @Override
    @ManagedAttribute(description = "the total amount of memory in the Java virtual machine, measured in bytes")
    public long getTotalMemory() {
        return Runtime.getRuntime().totalMemory() / MB;
    }

    @Override
    @ManagedAttribute(description = "the amount of free memory in the Java Virtual Machine, measured in bytes")
    public long getFreeMemory() {
        return Runtime.getRuntime().freeMemory() / MB;
    }

    @Override
    @ManagedAttribute(description = "the maximum amount of memory that the Java virtual machine will attempt to use, measured in bytes")
    public long getMaxMemory() {
        return Runtime.getRuntime().maxMemory() / MB;
    }

    @Override
    @ManagedAttribute(description = "an estimate of the number of active threads in this thread group")
    public int getThreadsCount() {
        ThreadGroup threadRoot = Thread.currentThread().getThreadGroup();
        while (threadRoot.getParent() != null) {
            threadRoot = threadRoot.getParent();
        }

        return threadRoot.activeCount();
    }

    /**
     * In fact, this method should be {@literal synchronized}, but it is immutable.
     * @return
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("JVM=");
        s.append("{");
        s.append("totalMemory=").append(getTotalMemory());
        s.append(", ");
        s.append("freeMemory=").append(getFreeMemory());
        s.append(", ");
        s.append("maxMemory=").append(getMaxMemory());
        s.append(", ");
        s.append("threadsCount=").append(getThreadsCount());
        s.append("}");

        return s.toString();
    }

    /**
     * Marshall as JSON.
     * 
     * @param writer 
     */
    @Override
    public String asJson() throws Exception {
        ObjectMapper om = new ObjectMapper();
        return new StringBuilder("jvmMonitor=").append(om.writeValueAsString(this)).toString();
    }
}
