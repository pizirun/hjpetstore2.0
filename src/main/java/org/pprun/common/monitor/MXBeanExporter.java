/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * A bean can be used as customized MBean/MXBean exporter if the spring built-in could not work.
 * <b>Currently, it is not used. </b>
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class MXBeanExporter {

    private static final Logger log = LoggerFactory.getLogger(MXBeanExporter.class);
    private MethodInvocationMonitorMXBean methodInvocationMonitorMXBean;

    @Required
    public void setMethodInvocationMonitorMXBean(MethodInvocationMonitorMXBean methodInvocationMonitorMXBean) {
        this.methodInvocationMonitorMXBean = methodInvocationMonitorMXBean;
    }

    public void init() {

        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("monitor:name=methodInvocationMonitorMXBean");
            mbs.registerMBean(methodInvocationMonitorMXBean, name);

        } catch (Throwable e) {
            // catch any Throwable in MBean register, then ignore it and
            // let the Spring container initilaization continue
            log.warn("MXBeanExporter: Error in register methodInvocationMonitorMXBean.");
        }
    }
}
