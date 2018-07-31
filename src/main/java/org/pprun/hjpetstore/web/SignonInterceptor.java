package org.pprun.hjpetstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

/**
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SignonInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        if (userSession == null) {
            String url = request.getServletPath();
            String query = request.getQueryString();
            ModelAndView modelAndView = new ModelAndView("SignonForm");

            if (query != null) {
                modelAndView.addObject("signonForwardAction", url + "?" + query);
            } else {
                modelAndView.addObject("signonForwardAction", url);
            }

            throw new ModelAndViewDefiningException(modelAndView);
        } else {
            return true;
        }
    }
}
