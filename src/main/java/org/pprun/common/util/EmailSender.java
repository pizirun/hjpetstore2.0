/*
 * Pprun's Public Domain.
 */
package org.pprun.common.util;

import org.springframework.mail.MailSender;

/**
 * An email sender independently the concrete sender implementation.
 *
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public interface EmailSender {

    void setMailSender(MailSender mailSender);

    void send() throws Throwable;

    void setMailFrom(String mailFrom);

    void setMailTo(String to);

    void setSubject(String subject);

    void setMailText(String text);
}
