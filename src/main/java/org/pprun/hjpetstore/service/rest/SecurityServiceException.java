/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.service.rest;

/**
 * A {@link RuntimeException} wraps Security Service layer exception.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SecurityServiceException extends RuntimeException {

    /**
     * Creates a new instance of <code>SecurityServiceException</code> without detail message.
     */
    public SecurityServiceException() {
    }

    /**
     * Constructs an instance of <code>SecurityServiceException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SecurityServiceException(String msg) {
        super(msg);
    }

    public SecurityServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityServiceException(Throwable cause) {
        super(cause);
    }
}
