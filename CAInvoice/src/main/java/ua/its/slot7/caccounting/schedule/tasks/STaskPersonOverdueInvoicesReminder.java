package ua.its.slot7.caccounting.schedule.tasks;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import ua.its.slot7.caccounting.communications.IMailerProcessor;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;
import ua.its.slot7.caccounting.service.BLServiceAvatar;
import ua.its.slot7.caccounting.service.PersonServiceAvatar;

import javax.jms.JMSException;
import java.util.List;

/**
 * @author Alex Velichko
 *         06.05.14 : 14:41
 */
public class STaskPersonOverdueInvoicesReminder extends QuartzJobBean {

	@Autowired
	private PersonServiceAvatar personService;

	@Autowired
	private BLServiceAvatar blService;

	@Autowired
	private IMailerProcessor mailerProcessor;

	@Override
	public void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		{
			List<Person> personList = personService.getPersonsAll();

			List<Invoice> invoiceList = null;

			for (Person person : personList) {

				User user = person.getUser();

				invoiceList = blService.personGetInvoicesOverdue(person);

				if (invoiceList != null) {
					try {
						mailerProcessor.sendPersonOverdueInvoicesReminder(user, person, invoiceList);
					} catch (JMSException e) {
						//TODO process
						e.printStackTrace();
					}
				}
			}
		}
	}
}
