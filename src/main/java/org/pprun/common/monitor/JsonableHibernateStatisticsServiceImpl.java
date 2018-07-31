/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.jmx.StatisticsService;

/**
 * print out the hibernate statistic.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 * @see StatisticsService
 */
public class JsonableHibernateStatisticsServiceImpl extends StatisticsService implements Statistic {

    /**
     * Marshall as JSON.
     * 
     * @param writer 
     */
    @Override
    public String asJson() throws Exception {
        ObjectMapper om = new ObjectMapper();
        return new StringBuilder("hibernateMonitor=").append(om.writeValueAsString(this)).toString();
    }

    @Override
    public String toString() {
        // @TODO figure out the easy way to print out the plain text of the statistic.
        // otherwise, the json format may also suitable.
        ObjectMapper om = new ObjectMapper();
        
        StringBuilder s = new StringBuilder("hibernateStatistic=");
        s.append("{");
        try {
            s.append(om.writeValueAsString(this));
        } catch (Exception e) {
            throw new RuntimeException("Error in marshalling JsonableStatisticsService", e);
        }
        s.append("}");

        return s.toString();

    }
}
