/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * The master of the statistics in package {@link org.pprun.common.monitor}.
 * Currently, there's only two methods to return the statistics as either {@literal JSON} or {@literal TEXT}.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
//@Component
public class MonitorImpl implements Monitor {

    public MonitorImpl() {
    }
    private Set<Statistic> statistics;

    /**
     * return the statistic as {@literal JSON}.
     */
    @Override
    public String asJson() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Statistic s : statistics) {
            sb.append(s.asJson());
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * return the statistic as {@literal Plain Text}.
     */
    @Override
    public String asPlainText() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Statistic s : statistics) {
            sb.append(s.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
    
    /**
     * @return the statistics
     */
    public Set<Statistic> getStatistics() {
        return statistics;
    }

    /**
     * @param statistics the statistics to set
     */
    public void setStatistics(Set<Statistic> statistics) {
        this.statistics = statistics;
    }

}
