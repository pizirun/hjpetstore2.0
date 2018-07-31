/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

/**
 * The master of the statistics in package {@link org.pprun.common.monitor}.
 * Currently, there's only two methods to return the statistics as either {@literal JSON} or {@literal TEXT}.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public interface Monitor {

    /**
     * return the statistic as {@literal JSON}.
     */
    String asJson() throws Exception;
    
    /**
     * return the statistic as {@literal Plain Text}.
     */
    String asPlainText() throws Exception;
}