package ua.its.slot7.caccounting.communications;

import javax.jms.JMSException;

/**
 * @author Alex Velichko
 *         22.04.14 : 16:02
 */
public interface IMailerProcessor {

	/**
	 * Send Welcome Aboard
	 */
	public void sendEmailWAboard(final String nick, final String email) throws JMSException;
}
