package ua.its.slot7.camailer;

import org.apache.log4j.Logger;
import ua.its.slot7.camailtask.model.MailTask;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * CAccounting
 * 27.08.13 : 20:42
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Stateless(name = "EMailSenderBeanEJB")
@Local(EMailSenderBeanAvatarLocal.class)
public class EMailSenderBean implements EMailSenderBeanAvatarLocal {
	/**
	 * Logger
	 */
	private final Logger LOGGER = Logger.getLogger(EMailSenderBean.class);

	/**
	 * Lookup string for JavaMail app-server resource
	 */
	private final String EMAIL_SNNAME = "mail/CAEMailResource";

	/**
	 * Resource of the Application server
	 */
	@Resource(name = EMAIL_SNNAME)
	private Session session;

	/**
	 * Post-construct procedure
	 */
	@PostConstruct
	void init() {

	}

	@Override
	public void sendEmail(MailTask mailTask) throws MessagingException, UnsupportedEncodingException {

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

	public EMailSenderBean() {
	}

}
