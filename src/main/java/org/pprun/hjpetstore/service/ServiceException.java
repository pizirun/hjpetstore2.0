/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service;

/**
 * A {@link RuntimeException} wraps service layer exception.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class ServiceException extends RuntimeException {

    /**
     * Creates a new instance of <code>ServiceException</code> without detail message.
     */
    public ServiceException() {
    }

    /**
     * Constructs an instance of <code>ServiceException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
