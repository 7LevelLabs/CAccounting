package ua.its.slot7.caccounting.communications;

import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;

import javax.jms.JMSException;
import java.util.List;

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

	/**
	 * Send Invoice Overdue reminder Person
	 */
	public void sendPersonOverdueInvoicesReminder(final User user, final Person person, final List<Invoice> invoiceList) throws JMSException;


}
