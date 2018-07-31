package org.pprun.hjpetstore.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author Juergen Hoeller
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SignoffController implements Controller {
    private String successView;

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().removeAttribute("userSession");
        request.getSession().invalidate();
        return new ModelAndView(this.successView);
    }
}
