package org.pprun.hjpetstore.service.aop;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.common.util.EmailSender;
import org.pprun.hjpetstore.domain.Order;
import org.pprun.hjpetstore.domain.User;
import org.pprun.hjpetstore.service.UserService;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Required;

/**
 * AOP advice that sends confirmation email after order has been submitted.
 * We use {@literal AfterReTurn} here because we expect any failure in email sending should not bother the normal
 * order transaction.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SendOrderConfirmationEmailAdvice implements AfterReturningAdvice {

    private static final Log log = LogFactory.getLog(SendOrderConfirmationEmailAdvice.class);
    private static final String DEFAULT_SUBJECT = "Thank you for your order at Hjpetstore!";
    private String subject = DEFAULT_SUBJECT;
    private EmailSender mailSender;
    private UserService userService;

    @Required
    public void setMailSender(EmailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Required
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void afterReturning(Object returnValue, Method m, Object[] args, Object target) throws Throwable {
        Order order = (Order) args[0];

        // call business service
        User user = userService.getUser(order.getUser().getUsername());

        // don't do anything if email address is not set
        if (user.getEmail() == null || user.getEmail().length() == 0) {
            return;
        }

        StringBuilder text = new StringBuilder();
        text.append("Dear ").append(user.getFirstname()).
                append(' ').append(user.getLastname());
        text.append(", thank your for your order from PetStore. Please note that your order number is ");
        text.append(order.getId());

        mailSender.setMailTo(user.getEmail());
        mailSender.setSubject(subject);
        mailSender.setMailText(text.toString());
        mailSender.send();
    }
}
