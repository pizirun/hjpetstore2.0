/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

/**
 *
 * @author pi314
 */
public class SampleJmsErrorHandler implements ErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(SampleJmsErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        LOG.warn("In default jms error handler...");
        LOG.error("Error Message : {}", t.getMessage());
    }

}