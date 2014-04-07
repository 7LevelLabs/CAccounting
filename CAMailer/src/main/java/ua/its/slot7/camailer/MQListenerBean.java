package ua.its.slot7.camailer;

import org.apache.log4j.Logger;
import ua.its.slot7.camailtask.model.MailTask;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * CAccounting
 * 27.08.13 : 19:51
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@MessageDriven(name = "MQListenerEJB", mappedName = "jms/CAMailerQ", activationConfig =  {
	@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
	@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MQListenerBean implements MessageListener {

	/**
	 * Logger
	 *
	 */
	private final Logger LOGGER = Logger.getLogger(MQListenerBean.class);

	@Resource
	private MessageDrivenContext mdc;

	@EJB
	private EMailSenderBeanAvatarLocal emailSender;

	@PostConstruct
	void init () {
		LOGGER.info("MQListenerBean.init");

	}

	/**
	 * Receive and process Message
	 *
	 */
	@Override
	public void onMessage(Message message) {

		ObjectMessage objectMessage = (ObjectMessage) message;

		try {
			MailTask mailTask = (MailTask) objectMessage.getObject();
			LOGGER.info("MailTask received : "+mailTask.toString());

			emailSender.sendEmail(mailTask);

			LOGGER.info("Successfully sent.");

		} catch (JMSException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	public MQListenerBean() {

	}

}
