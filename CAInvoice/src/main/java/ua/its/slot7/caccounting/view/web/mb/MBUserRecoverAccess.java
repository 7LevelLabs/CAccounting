package ua.its.slot7.caccounting.view.web.mb;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.communications.IMailerProcessor;
import ua.its.slot7.caccounting.model.user.User;
import ua.its.slot7.caccounting.model.userartoken.UserARToken;
import ua.its.slot7.caccounting.service.UserARTokenServiceAvatar;
import ua.its.slot7.caccounting.service.UserServiceAvatar;
import ua.its.slot7.caccounting.system.BSystemSettingsAvatar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.jms.JMSException;
import java.util.Date;

/**
 * CAccounting
 * 25.08.13 : 20:22
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
@ManagedBean(name = "MBUserRecoverAccess")
public class MBUserRecoverAccess {
	private final Logger LOGGER = Logger.getLogger("MBUserRecoverAccess");

	@Autowired
	private UserServiceAvatar userService;

	@Autowired
	private UserARTokenServiceAvatar userARTokenService;

	@Autowired
	private BSystemSettingsAvatar bSystemSettings;

	@Autowired
	private IMailerProcessor mailerProcessor;

	private String localUserEmail;

	private String localUserARCode;

	private String localUserPassword;
	private String localUserPasswordConfirm;

	public String actionRecoverAccess() {

		LOGGER.info("actionRecoverAccess : Go!");

		String res = null;
		if (!userService.areThereThisEMail(this.getLocalUserEmail())) {
			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error",
					"This EMail " + this.getLocalUserEmail() + " is not in use. " +
						"Please, use another email."
				)
			);
			this.setLocalUserEmail("");
			return res;
		}

		UserARToken userARToken = new UserARToken(this.getLocalUserEmail());
		userARTokenService.createUserARToken(userARToken);

		User lUser = userService.getUserByEMail(userARToken.getEmail());

		try {
			mailerProcessor.sendAccessRecoveryPh1(lUser.getNick(),
				userARToken.getEmail(),
				userARToken.getTokenCode(),
				userARToken.getPeriodEnd().toString());

		} catch (JMSException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error",
					"Communications error. Please, try later.")
			);
			return res;
		}
		FacesContext.getCurrentInstance().addMessage(
			null,
			new FacesMessage(
				FacesMessage.SEVERITY_INFO,
				"Info",
				"We just send you email. " +
					"Please, read appropriate mailbox."
			)
		);
		return res;
	}

	public String actionRecoveryAccessF2() {
		String res = null;
		if (!userService.areThereThisEMail(this.getLocalUserEmail())) {
			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error",
					"This EMail " + this.getLocalUserEmail() + " is not in use. " +
						"Please, use another email."
				)
			);
			this.setLocalUserEmail("");
			return res;
		}
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

		UserARToken userARToken = userARTokenService.getUserARTokenByEMail(this.getLocalUserEmail());

		if (userARToken == null) {
			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error",
					"Wrong code.")
			);
			this.setLocalUserARCode("");
			return null;
		}

		if (!userARToken.getTokenCode().equals(this.getLocalUserARCode())) {
			FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Error",
					"Wrong code-email.")
			);
			this.setLocalUserARCode("");
			return null;
		}

		if (userARToken.getPeriodEnd().before(new Date())) {
			LOGGER.info("Too late.");
			//delete token
			userARTokenService.deleteUserARToken(userARToken);
			//return
			res = "index?faces-redirect-true";
			return res;
		}

		//get User by email
		User localUser = userService.getUserByEMail(this.getLocalUserEmail());

		//coding password
		String lPassword = null;

		StandardPasswordEncoder encoder = new StandardPasswordEncoder();
		lPassword = encoder.encode(this.getLocalUserPassword());

		//setting password
		localUser.setPass(lPassword);

		//update User
		userService.updateUser(localUser);

		//send email
		try {
			mailerProcessor.sendAccessRecoveryPh2(localUser.getNick(), userARToken.getEmail());

		} catch (JMSException e) {
			e.printStackTrace();
		}

		//delete token
		userARTokenService.deleteUserARToken(userARToken);
		FacesContext.getCurrentInstance().addMessage(
			null,
			new FacesMessage(
				FacesMessage.SEVERITY_INFO,
				"Change password.",
				"Password successfully changed.")
		);
		res = "index";
		return res;
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

	public String getLocalUserARCode() {
		return localUserARCode;
	}

	public void setLocalUserARCode(String localUserARCode) {
		this.localUserARCode = localUserARCode;
	}

	public String getLocalUserEmail() {
		return localUserEmail;
	}

	public void setLocalUserEmail(String localUserEmail) {
		this.localUserEmail = localUserEmail;
	}
}
