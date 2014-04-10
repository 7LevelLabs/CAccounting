package ua.its.slot7.camailer;

import org.springframework.stereotype.Component;
import ua.its.slot7.camailtask.model.MailTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author Alex Velichko
 *         10.04.14 : 16:41
 */
@Component
public class EMailSender implements IEmailSender {

	private Session session;

	@Override
	public void sendEMail(@NotNull final MailTask mailTask) throws MessagingException, UnsupportedEncodingException {

		Message msg = new MimeMessage(session);
		msg.setSentDate(new Date());

		msg.setFrom(
			new InternetAddress(
				mailTask.getFrom(),
				mailTask.getFromName())
		);

		msg.setRecipient(
			MimeMessage.RecipientType.TO,
			new InternetAddress(
				mailTask.getTo(),
				mailTask.getToName())
		);

		msg.setSubject(mailTask.getSubject());

		if (mailTask.getIsHTMLMessage()) {
			msg.setContent(mailTask.getMessageBody(), "text/html");
		} else {
			msg.setText(mailTask.getMessageBody());
		}

		Transport.send(msg);
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Session getSession() {
		return session;
	}
}
