package ua.its.slot7.caccounting.communications;

import org.springframework.beans.factory.annotation.Autowired;
import ua.its.slot7.caccounting.system.BSystemSettingsAvatar;
import ua.its.slot7.camailtask.model.MailTask;

import javax.jms.JMSException;

/**
 * @author Alex Velichko
 *         22.04.14 : 16:05
 */
public class MailerProcessor implements IMailerProcessor {

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
	public void sendEmailWAboard(String nick, String email) throws JMSException {

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

	private void processMailTask(MailTask mailTask) throws JMSException {
		mailerWorker.sendOutboundMailTaskQMessage(mailTask);
	}

}
