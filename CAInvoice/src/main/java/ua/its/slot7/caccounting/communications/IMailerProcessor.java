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
	public void sendEmailWelcomeAboard(final String nick, final String email) throws JMSException;

	/**
	 * Send Access Recovery Ph1
	 */
	public void sendAccessRecoveryPh1(final String nick, final String email, final String code, final String codeExpTime) throws JMSException;

	/**
	 * Send Access Recovery Ph2
	 */
	public void sendAccessRecoveryPh2(final String nick, final String email) throws JMSException;


}
