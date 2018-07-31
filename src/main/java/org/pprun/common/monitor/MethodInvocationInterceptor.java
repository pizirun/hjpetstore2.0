/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An interceptor acts on method invocation and collect statistics information about the method call.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 *
 */
public class MethodInvocationInterceptor implements MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(MethodInvocationInterceptor.class);

    private final MethodInvocationMonitorMXBean methodInvocationMonitor;

    public MethodInvocationInterceptor(MethodInvocationMonitorMXBean methodInvocationMonitor) {
        this.methodInvocationMonitor = methodInvocationMonitor;
    }

    /**
     * Actual execution of the method.
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();

        boolean isSuccess = true;
        Object returnValue = null;

        try {
            returnValue = invocation.proceed();
        } catch (Throwable t) {
            isSuccess = false;

            log.error("MethodInvocationInterceptor: method invoked failed", t);
            throw t;
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;

            String name = getStatisticName(invocation);
            
            methodInvocationMonitor.addStatistic(name, executionTime, isSuccess);
        }
        
        return returnValue;
    }

    /**
     * build a statistic name from invocation
     */
    private String getStatisticName(MethodInvocation invocation) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(invocation.getThis().getClass().getSimpleName())
                .append(".")
                .append(invocation.getMethod().getName())
                .append("(").append(invocation.getArguments().length).append(")");
        return buffer.toString();
    }
}
