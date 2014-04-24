package ua.its.slot7.caccounting.communications;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex Velichko
 *         23.04.14 : 12:46
 */
public class MailBodyProcessor implements IMailBodyProcessor {

	@Value("${welcome.aboard}")
	private String templateWelcomeAboard;

	@Value("${access.recover.1}")
	private String templateAccessRecoverPh1;

	@Value("${access.recover.2}")
	private String templateAccessRecoverPh2;

	@Value("${email.encoding}")
	private String emailEncoding;

	private VelocityEngine velocityEngine;

	/**
	 * Welcome aboard email to the new user
	 *
	 * @param nick
	 * @param email
	 */
	@Override
	public String userWelcomeAboard(final String nick, final String email) {
		String mailBody;

		Map model = new HashMap();

		model.put("nick", nick);
		model.put("email", email);

		mailBody = VelocityEngineUtils.mergeTemplateIntoString(
			velocityEngine, templateWelcomeAboard, emailEncoding, model);

		return mailBody;
	}

	/**
	 * Acces recover email, ph. 1
	 *
	 * @param nick
	 * @param email
	 * @param code
	 * @param urlApplication
	 * @param urlPh2
	 * @param codeExpTime
	 */
	@Override
	public String userAccessRecoverPh1(final String nick,
						final String email,
						final String code,
						final String urlApplication,
						final String urlPh2,
						final String codeExpTime) {
		String mailBody;

		Map model = new HashMap();

		model.put("nick", nick);
		model.put("email", email);
		model.put("url_application", urlApplication);
		model.put("code", code);
		model.put("url_ph2", urlPh2);
		model.put("code_exp_time", codeExpTime);

		mailBody = VelocityEngineUtils.mergeTemplateIntoString(
			velocityEngine, templateAccessRecoverPh1, emailEncoding, model);

		return mailBody;
	}

	/**
	 * Access recover email, ph. 2
	 *
	 * @param nick
	 * @param email
	 * @param urlApplication
	 */
	@Override
	public String userAccessRecoverPh2(String nick, String email, String urlApplication) {
		String mailBody;

		Map model = new HashMap();

		model.put("nick", nick);
		model.put("email", email);
		model.put("url_application", urlApplication);

		mailBody = VelocityEngineUtils.mergeTemplateIntoString(
			velocityEngine, templateAccessRecoverPh2, emailEncoding, model);

		return mailBody;
	}


	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public String getEmailEncoding() {
		return emailEncoding;
	}

}
