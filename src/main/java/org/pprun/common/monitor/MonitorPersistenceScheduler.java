/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This job persistent the statistic to log file for audit while server crashed.
 * it is scheduled to run every 5 minutes.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
//@Component
public class MonitorPersistenceScheduler {
    private static final Logger log = LoggerFactory.getLogger(MonitorPersistenceScheduler.class);
    
    private Monitor monitor;
    
    
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void persistStatistics() {
        log.info("Persistent monitor statistic start ...");
        try {
            log.info(monitor.asPlainText());
        } catch (Exception ex) {
            log.error("error in persistent monitor statistic", ex);
        }
        log.info("Persistent monitor statistic done!");
    }

    @Autowired
    @Required
    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }
}
