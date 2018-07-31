package org.pprun.hjpetstore.service.aop;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pprun.common.util.EmailSender;
import org.pprun.common.util.SimpleTextEmailSender;
import org.pprun.hjpetstore.domain.User;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.annotation.Required;

/**
 * AOP advice that sends user key email after user registered.
 *
 * <p>
 * We use {@literal AfterReTurn} here because we expect any failure in email sending should not bother the normal
 * user registration transaction.
 * TODO:
 * <b>Please note that, for registration flow, it is common to verify the user which is just registered
 * and status is {@link User.UserStatus.PENDING},
 * by supplying a link for user. Once the user click the link and come back, we can confirm it is a valid registration
 * and hereafter update the user status to {@link User.UserStatus.ACTIVE}.
 * </b>
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public class SendUserKeyEmailAdvice implements AfterReturningAdvice {

    private static final Log log = LogFactory.getLog(SendUserKeyEmailAdvice.class);
    // user jes on localhost
    private static final String DEFAULT_SUBJECT = "Thank you for your registration at Hjpetstore!";
    private String subject = DEFAULT_SUBJECT;
    private EmailSender mailSender;

    @Required
    public void setMailSender(EmailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void afterReturning(Object returnValue, Method m, Object[] args, Object target) throws Throwable {
        User user = (User) args[0];
        boolean generateKey = args[1] == null ? false : ((Boolean) args[1]).booleanValue();


        // don't do anything if email address is not set
        if (user.getEmail() == null || user.getEmail().length() == 0) {
            return;
        }

        StringBuilder text = new StringBuilder();
        text.append("Dear ").append(user.getFirstname()).
                append(' ').append(user.getLastname());
        text.append(", thank your for your registration from PetStore.");
        if (generateKey) {
            text.append("\n");

            text.append("You can use the apikey and secretKey assigned to you to access our public web service, please login Petstore to find it:");
            text.append("\n");
            text.append("http://localhost:8080/hjpetstore/");
            text.append("\n");
        }

        mailSender.setMailTo(user.getEmail());
        mailSender.setSubject(subject);
        mailSender.setMailText(text.toString());
        mailSender.send();
    }
}
