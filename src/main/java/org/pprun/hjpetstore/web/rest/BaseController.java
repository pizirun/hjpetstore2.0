/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.hjpetstore.web.rest;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.common.util.SignatureException;
import org.pprun.hjpetstore.persistence.jaxb.CommonError;
import org.pprun.hjpetstore.service.ServiceException;
import org.pprun.hjpetstore.service.rest.CodedValidationException;
import org.pprun.hjpetstore.service.rest.SecurityServiceException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 * This is the {@literal abstract} controller to centralized the exception handler for
 * common {@link #Exception} into one place.
 * It would expected all Spring MVC 3.0 controller extends from this class as currently spring
 * {@link #AnnotationMethodHandlerExceptionResolver} lacks of the ability to apply the common exception hander
 * to all controllers.
 * <p>
 * Please note that this way is {@literal controller} centric, if we including {@literal ErrorCode}, such as
 * @ResponseStatus(HttpStatus.NOT_FOUND) here, it seems it can not capture the same errorCode thrown by other servlets
 * in the same application.
 * <br />
 * So we still keep the error page mapping in {@literal web.xml} for application scope purpose.
 * 
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public abstract class BaseController {

    private static final Log log = LogFactory.getLog(BaseController.class);
    private static final String COMMON_ERROR = "CommonError";
    private static final String COMMON_ERROR_ATTRIBUTE = "commonError";

    @ExceptionHandler({NoSuchRequestHandlingMethodException.class})
    public ModelAndView handleNotFound(NoSuchRequestHandlingMethodException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("The page not found: " + path, ex);

        ModelAndView mav = composeModelAndView(path, HttpStatus.BAD_REQUEST, "The page not found: " + path, ex);
        return mav;
    }

    /**
     * 406 (Not Acceptable).
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    public ModelAndView handleNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("The media type is not acceptable when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.NOT_ACCEPTABLE, "The media type is not acceptable when access to: " + path, ex);
        return mav;
    }

    /**
     * 415 (Unsupported Media Type) into one place.
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ModelAndView handleNotAcceptable(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("The media type is upsupported when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.UNSUPPORTED_MEDIA_TYPE,  "The media type is upsupported when access to: " + path, ex);
        return mav;
    }

    /**
     * HttpMessageNotReadableException.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ModelAndView handleMessageReadException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't read from stream when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.BAD_REQUEST, "Can't read from stream when access to: " + path, ex);
        return mav;
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ModelAndView handleRequestParameterMissing(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("The required http parameter is missing when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.BAD_REQUEST, "The required http parameter is missing when access to: " + path, ex);
        return mav;
    }

    /**
     * HttpMessageNotWritableException.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({HttpMessageNotWritableException.class})
    public ModelAndView handleMessageWriteException(HttpMessageNotWritableException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't write to stream when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR, "Can't write to stream when access to: " + path, ex);
        return mav;
    }

    /**
     * Can be take place when using xml binding or no binder was registered for a customized type.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({TypeMismatchException.class})
    public ModelAndView handleTypeMismatchException(TypeMismatchException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Type is mismatch when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR, "Type is mismatch when access to: " + path, ex);
        return mav;
    }

    @ExceptionHandler({ServiceException.class})
    public ModelAndView handleServiceException(ServiceException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't accomplish the request because of service layer error when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR, "Can't accomplish the request because of service layer error when access to: " + path, ex);
        return mav;
    }

    @ExceptionHandler({SecurityServiceException.class})
    public ModelAndView handleSecurityServiceException(SecurityServiceException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't accomplish the request because of SecurityServiceException when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR, "Can't accomplish the request because of SecurityServiceException when access to: "  + path, ex);
        return mav;
    }

    @ExceptionHandler({SignatureException.class})
    public ModelAndView handleSecurityServiceException(SignatureException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't accomplish the request because of SignatureException when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.FORBIDDEN, "Can't accomplish the request because of SignatureException when access to: "  + path, ex);
        return mav;
    }

    @ExceptionHandler({DataAccessException.class})
    public ModelAndView handleDataAccessException(DataAccessException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't accomplish the request because of data layer error when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR, "Can't accomplish the request because of data layer error when access to: " + path, ex);
        return mav;
    }

    @ExceptionHandler({CodedValidationException.class})
    public ModelAndView handleCodedValidationException(CodedValidationException ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn(ex.getErrorCode() + " when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.BAD_REQUEST, ex.getErrorCode() + " when access to: " + path, ex);
        return mav;
    }

    /**
     * This is the fallback for all other UncaughtException.
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ModelAndView handleUncaughtException(Exception ex, HttpServletRequest request) {
        String path = request.getPathInfo();

        log.warn("Can't accomplish the request because an unexpected error when access to: " + path, ex);
        ModelAndView mav = composeModelAndView(path, HttpStatus.INTERNAL_SERVER_ERROR, "Can't accomplish the request because an unexpected error when access to: " + path, ex);
        return mav;
    }

    private ModelAndView composeModelAndView(String path, HttpStatus status, String message, Exception ex) {
        ModelAndView mav = new ModelAndView(COMMON_ERROR);

        CommonError commonError = new CommonError();
        commonError.setPath(path);
        commonError.setStatus(status);
        commonError.setMessage(message);
        // only output exception information when debug
        if (log.isDebugEnabled()) {
            commonError.setException(ex.toString());
        }

        mav.addObject(COMMON_ERROR_ATTRIBUTE, commonError);
        return mav;
    }
}
