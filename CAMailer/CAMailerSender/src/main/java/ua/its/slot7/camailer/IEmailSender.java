package ua.its.slot7.camailer;

import ua.its.slot7.camailtask.model.MailTask;

import javax.mail.MessagingException;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;

/**
 * @author Alex Velichko
 *         10.04.14 : 17:05
 */
public interface IEmailSender {

	void sendEMail(@NotNull final MailTask mailTask) throws MessagingException, UnsupportedEncodingException;
}
