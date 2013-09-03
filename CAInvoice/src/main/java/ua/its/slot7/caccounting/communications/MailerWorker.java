package ua.its.slot7.caccounting.communications;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import ua.its.slot7.camailtask.model.MailTask;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * CAccounting
 * 28.08.13 : 15:16
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
public class MailerWorker implements MailerWorkerAvatar {

	private final Logger LOGGER = Logger.getLogger(this.getClass());

	/**
	 *
	 * {@link JmsTemplate} to send JMS
	 * */
	private JmsTemplate template = null;

	private String destinationString = null;

	private MailTask mailTask;

	@Override
	public void sendOutboundMailTaskQMessage(MailTask mailTask) throws JMSException {
		if (mailTask==null) {
			throw new NullPointerException("Arguments can't be null.");
		}
		if (this.destinationString==null) {
			throw new NullPointerException("destinationString can't be null.");
		}
		if (this.destinationString.length()==0) {
			throw new IllegalArgumentException("destinationString can't be empty.");
		}

		this.setMailTask(mailTask);
		this.sendQMessage();
	}

	@Override
	public void setDestination(String destinationString) {
		if (destinationString==null) {
			throw new NullPointerException("Arguments can't be null.");
		}
		if (destinationString.length()==0) {
			throw new IllegalArgumentException("Arguments can't be empty.");
		}
		this.setDestinationString(destinationString);
	}

	private void sendQMessage() {
		MessageCreator messageCreator = new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				ObjectMessage message = session.createObjectMessage(mailTask);
				return message;
			}
		};
		template.send(this.destinationString,messageCreator);
	}

	private void setMailTask(MailTask mailTask) {
		this.mailTask = mailTask;
	}

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public String getDestinationString() {
		return destinationString;
	}

	public void setDestinationString(String destinationString) {
		this.destinationString = destinationString;
	}

}
