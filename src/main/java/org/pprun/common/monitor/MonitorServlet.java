/*
 * Pprun's Public Domain.
 */
package org.pprun.common.monitor;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet print out the {@literal Plain Text} format of the statistics to response.
 * 
 * @author <a href="mailto:pizhigang@letv.com">pizhigang</a>
 */
public class MonitorServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(MonitorServlet.class);

    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        Writer writer = null;
        try {

            // set port
            ServerMonitorMXBeanImpl.setPort(request.getServerPort());

            response.setContentType("text/plain");

            ServletContext servletContext = this.getServletContext();
            WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

            Monitor monitor = (MonitorImpl) wac.getBean("monitorImpl");

            // print out the statistics to writer of response
            writer = response.getWriter();
            writer.write(monitor.asPlainText());

            writer.flush();

        } catch (Throwable t) {
            log.error("Exception in printing out statistic", t);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, t.getMessage());
            } catch (Throwable th) {
                log.error("Failed to sends an error response to the client", th);
            }
        } finally {
            if (writer != null) {
                writer.close();
            }

            long duration = System.currentTimeMillis() - startTime;
            log.info("Printing out statistic data takes {} (ms)", duration);
        }

    }
}