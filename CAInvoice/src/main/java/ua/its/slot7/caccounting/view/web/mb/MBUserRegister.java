package ua.its.slot7.caccounting.view.web.mb;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.communications.MailerWorkerAvatar;
import ua.its.slot7.caccounting.helper.UserHelper;
import ua.its.slot7.caccounting.model.user.User;
import ua.its.slot7.caccounting.model.userrole.UserRole;
import ua.its.slot7.caccounting.service.UserServiceAvatar;
import ua.its.slot7.caccounting.system.BSystemSettingsAvatar;
import ua.its.slot7.camailtask.model.MailTask;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.jms.JMSException;
import java.io.Serializable;

/**
 * CAccounting
 * 25.07.13 : 13:35
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Component
@Scope("request")
@RequestScoped
@ManagedBean(name = "MBUserRegister")
public class MBUserRegister implements Serializable {

	private final Logger LOGGER = Logger.getLogger("MBUserRegister");

	@Autowired
	private UserServiceAvatar userService;

	@Autowired
	private MailerWorkerAvatar mailerWorker;

	@Autowired
	private BSystemSettingsAvatar bSystemSettings;

	@Autowired
	private UserHelper userHelper;

	private String localUserEmail;
	private String localUserPassword;
	private String localUserPasswordConfirm;

	private String localUserNick;

	public String actionUserRegister() {
		String res = "index";
		if (!this.getLocalUserPassword().equals(this.getLocalUserPasswordConfirm())) {
			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error",
					"Passwords must match.")
			);
			this.setLocalUserPassword("");
			this.setLocalUserPasswordConfirm("");
			return null;
		}

		if (userService.areThereThisEMail(this.getLocalUserEmail())) {
			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error",
					"This EMail " + this.getLocalUserEmail() + " already in use. " +
						"Please, use another email."
				)
			);
			this.setLocalUserEmail("");
			return null;
		}

		String lPassword = null;

		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
		lPassword = encoder.encode(this.getLocalUserPassword());

		User localUser = userHelper.getNewUser(this.getLocalUserNick(), this.getLocalUserEmail(), lPassword);

		localUser.setUserRole(new UserRole(UserRole.USER_ROLE_USER));

		userService.createUser(localUser);

		//email sending

		String mf, mfn, mt, mtn, ms, mb;

		mf = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_EMAIL");
		mfn = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_NAME");

		mt = localUser.getEmail();
		mtn = localUser.getNick();
		ms = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_UR_WELCOME_SUBJ");
		mb = bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_UR_WELCOME_TEXT");

		MailTask mailTask = new MailTask(mf,
			mfn,
			mt,
			mtn,
			ms,
			mb,
			true);

		try {
			mailerWorker.sendOutboundMailTaskQMessage(mailTask);
		} catch (JMSException e) {
			e.printStackTrace();
		}

		FacesContext.getCurrentInstance().addMessage(
			null,
			new FacesMessage(
				FacesMessage.SEVERITY_INFO,
				"Register successful.",
				"EMail " + localUser.getEmail() + " successfully registered. Check this email.")
		);

		return res;
	}

	public String getLocalUserNick() {
		return localUserNick;
	}

	public void setLocalUserNick(String localUserNick) {
		this.localUserNick = localUserNick;
	}

	public String getLocalUserEmail() {
		return localUserEmail;
	}

	public void setLocalUserEmail(String localUserEmail) {
		this.localUserEmail = localUserEmail;
	}

	public String getLocalUserPassword() {
		return localUserPassword;
	}

	public void setLocalUserPassword(String localUserPassword) {
		this.localUserPassword = localUserPassword;
	}

	public String getLocalUserPasswordConfirm() {
		return localUserPasswordConfirm;
	}

	public void setLocalUserPasswordConfirm(String localUserPasswordConfirm) {
		this.localUserPasswordConfirm = localUserPasswordConfirm;
	}

	public MBUserRegister() {

	}
}
