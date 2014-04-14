package ua.its.slot7.camailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ua.its.slot7.camailtask.model.MailTask;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @author Alex Velichko
 *         12.04.14 : 16:44
 */
public class MQListener implements MessageListener {

	@Qualifier("emailSender")
	@Autowired
	private IEmailSender emailSender;

	@Override
	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			try {
				MailTask mailTask = (MailTask) ((ObjectMessage) message).getObject();
				emailSender.sendEMail(mailTask);

			} catch (JMSException e) {
				//TODO process JMSException
				e.printStackTrace();
			} catch (MessagingException e) {
				//TODO process JMSException
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				//TODO process JMSException
				e.printStackTrace();
			}
		}
	}

	public IEmailSender getEmailSender() {
		return emailSender;
	}
}
