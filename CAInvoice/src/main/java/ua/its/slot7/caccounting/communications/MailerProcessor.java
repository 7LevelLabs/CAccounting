package ua.its.slot7.caccounting.communications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ua.its.slot7.caccounting.model.invoice.Invoice;
import ua.its.slot7.caccounting.model.person.Person;
import ua.its.slot7.caccounting.model.user.User;
import ua.its.slot7.caccounting.system.BSystemSettingsAvatar;
import ua.its.slot7.camailtask.model.MailTask;

import javax.jms.JMSException;
import java.util.List;

/**
 * @author Alex Velichko
 *         22.04.14 : 16:05
 */
public class MailerProcessor implements IMailerProcessor {

	@Value("${system.url.base}")
	private String urlApplication;

	@Value("${system.url.arph1}")
	private String urlARPh1;

	@Value("${system.email.subj.overdue.invoices.reminder.person}")
	private String emailSubjPersonOverdueInvoicesReminder;

	@Autowired
	private BSystemSettingsAvatar bSystemSettings;

	@Autowired
	private MailerWorkerAvatar mailerWorker;

	@Autowired
	private IMailBodyProcessor mailBodyProcessor;

	/**
	 * Send Welcome Aboard
	 *
	 * @param nick
	 * @param email
	 */
	@Override
	public void sendEmailWelcomeAboard(final String nick, final String email) throws JMSException {

		String mf, mfn, ms, mb;

		mf = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_EMAIL");
		mfn = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_NAME");

		ms = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_UR_WELCOME_SUBJ");

		mb = mailBodyProcessor.userWelcomeAboard(nick, email);

		MailTask mailTask = new MailTask(mf,
			mfn,
			email,
			nick,
			ms,
			mb,
			true);
		this.processMailTask(mailTask);
	}

	/**
	 * Send Access Recovery Ph1
	 *
	 * @param nick
	 * @param email
	 * @param code
	 * @param codeExpTime
	 */
	@Override
	public void sendAccessRecoveryPh1(final String nick,
					      final String email,
					      final String code,
					      final String codeExpTime) throws JMSException {
		String mf, mfn, ms, mb;

		mf = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_EMAIL");
		mfn = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_NAME");

		ms = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_AR_CODE_SUBJ");

		mb = mailBodyProcessor.userAccessRecoverPh1(nick,
			email,
			code,
			this.getUrlApplication(),
			this.getUrlApplication() + "/" + this.getUrlARPh1(),
			codeExpTime);

		MailTask mailTask = new MailTask(mf,
			mfn,
			email,
			nick,
			ms,
			mb,
			true);
		this.processMailTask(mailTask);

	}

	/**
	 * Send Access Recovery Ph2
	 *
	 * @param nick
	 * @param email
	 */
	@Override
	public void sendAccessRecoveryPh2(String nick, String email) throws JMSException {
		String mf, mfn, ms, mb;

		mf = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_EMAIL");
		mfn = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_NAME");

		ms = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_AR_CODE_SUBJ");

		mb = mailBodyProcessor.userAccessRecoverPh2(nick,
			email,
			this.getUrlApplication());

		MailTask mailTask = new MailTask(mf,
			mfn,
			email,
			nick,
			ms,
			mb,
			true);
		this.processMailTask(mailTask);

	}

	/**
	 * Send Invoice Overdue reminder Person
	 *
	 * @param person
	 * @param invoiceList
	 */
	@Override
	public void sendPersonOverdueInvoicesReminder(final User user, final Person person, final List<Invoice> invoiceList) throws JMSException {
		String mf, mfn, ms, mb;

		mf = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_EMAIL");
		mfn = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_NAME");

		ms = emailSubjPersonOverdueInvoicesReminder;

		mb = mailBodyProcessor.personOverdueInvoicesReminder(user, person, invoiceList);

		MailTask mailTask = new MailTask(mf,
			mfn,
			person.getEmail(),
			person.getName(),
			ms,
			mb,
			true);
		this.processMailTask(mailTask);
	}

	private void processMailTask(MailTask mailTask) throws JMSException {
		mailerWorker.sendOutboundMailTaskQMessage(mailTask);
	}

	public String getUrlApplication() {
		return urlApplication;
	}

	public String getUrlARPh1() {
		return urlARPh1;
	}
}
