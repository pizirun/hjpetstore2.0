/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pprun.common.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.common.util.CommonUtil;
import org.pprun.common.util.MessageDigestUtil;
import org.pprun.hjpetstore.service.UserService;
import org.pprun.hjpetstore.service.rest.CodedValidationException;
import org.pprun.hjpetstore.service.rest.ErrorCodeConstant;
import org.pprun.common.util.SignatureException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * This filter authenticate the user by calculating the signature of the request.
 * For more detail, see <a href="http://hi.baidu.com/quest2run/blog/item/2e1cd8266c2e997335a80f7d.html">Cloud Security</a>.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class OAuthFilter implements Filter {

    private static Log log = LogFactory.getLog(OAuthFilter.class);
    private UserService userService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        if (log.isInfoEnabled()) {
            log.info("init OAuthFilter ...");
        }

        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        if (log.isDebugEnabled()) {
            log.debug("looking up userService bean ...");
        }
        userService = springContext.getBean("userService", UserService.class);
        if (log.isDebugEnabled()) {
            log.debug("Found userService");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (log.isDebugEnabled()) {
            log.debug("doFilter: incoming request...");
        }

        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            validateSignature((HttpServletRequest) request);

            // successful, go ahead to the request URI
            chain.doFilter(request, response);

        } else {
            chain.doFilter(request, response);
        }
    }

    private void validateSignature(HttpServletRequest httpServletRequest) {
        String apiKey = httpServletRequest.getParameter("apiKey");

        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new CodedValidationException(ErrorCodeConstant.ERROR_CODE_INVALID_API_KEY, "apiKey can not be null");
        }

        if (log.isDebugEnabled()) {
            log.debug("apiKey before decode: " + apiKey);
        }

        // decode back
        try {
            apiKey = URLDecoder.decode(apiKey, CommonUtil.UTF8);
        } catch (UnsupportedEncodingException ex) {
            throw new SignatureException("UnsupportedEncodingException when URLEncoder.decode: " + CommonUtil.UTF8, ex);
        }

        if (log.isDebugEnabled()) {
            log.debug("apiKey after decode: " + apiKey);
        }

        String secretKey = userService.getUserSecretKeyForApiKey(apiKey);
        if (secretKey == null) {
            throw new CodedValidationException(ErrorCodeConstant.ERROR_CODE_INVALID_API_KEY, "no secretKey can be found for the apiKey: " + apiKey);
        }

        String signature = httpServletRequest.getParameter("signature");

        if (signature == null || signature.isEmpty()) {
            throw new CodedValidationException(ErrorCodeConstant.ERROR_CODE_INVALID_SIGNATURE, "signature can not be null");
        }

        if (log.isDebugEnabled()) {
            log.debug("signature before decode: " + signature);
        }
        
        // decode back
        try {
            signature = URLDecoder.decode(signature, CommonUtil.UTF8);
        } catch (UnsupportedEncodingException ex) {
            throw new SignatureException("UnsupportedEncodingException when URLEncoder.decode: " + CommonUtil.UTF8, ex);
        }
        if (log.isDebugEnabled()) {
            log.debug("signature after decode: " + signature);
        }

        String date = httpServletRequest.getHeader("Date");
        if (date == null || date.isEmpty()) {
            throw new CodedValidationException(ErrorCodeConstant.ERROR_CODE_NO_FOUND_DATE_HEADER, "Date header MUST be set in the request");
        }
        if (log.isDebugEnabled()) {
            log.debug("header Date: " + date);
        }

        // String has been Signed = HTTP-Method + "\n" + Date + "\n" + resourcePath + "\n";
        String httpMethod = httpServletRequest.getMethod();
        if (log.isDebugEnabled()) {
            log.debug("httpMethod: " + httpMethod);
        }


        String resourcePath = httpServletRequest.getRequestURI();
        if (log.isDebugEnabled()) {
            log.debug("resourcePath: " + resourcePath);
        }
        String calculatedSignature = MessageDigestUtil.calculateSignature(httpMethod, date, resourcePath, secretKey);

        if (log.isDebugEnabled()) {
            log.debug("calculateSignature = " + calculatedSignature);
        }

        if (calculatedSignature.equals(signature) == false) {
            throw new SignatureException("Invalid signature: the calculated signature (" + calculatedSignature +
                    ") does not match with the signature passed in (" + signature + ").");
        }
    }

    @Override
    public void destroy() {
        if (log.isInfoEnabled()) {
            log.info("destroy OAuthFilter ...");
        }
    }
}
