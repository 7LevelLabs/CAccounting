package ua.its.slot7.caccounting.communications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import ua.its.slot7.caccounting.system.BSystemSettingsAvatar;
import ua.its.slot7.camailtask.model.MailTask;

import javax.jms.JMSException;

/**
 * @author Alex Velichko
 *         22.04.14 : 16:05
 */
public class MailerProcessor implements IMailerProcessor {

	@Value("${system.url.base}")
	private String urlApplication;

	@Value("${system.url.arph1}")
	private String urlARPh1;

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
