package ua.its.slot7.caccounting.view.web.mb;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.system.BSystemSettingsAvatar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * CAccounting
 * 31.08.13 : 15:37
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
@ManagedBean(name = "MBSSettings")
@Scope("request")
@RequestScoped
public class MBSSettings implements Serializable {
	private final Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private BSystemSettingsAvatar bSystemSettings;

	private String settings_system_base_url;

	private String s_system_email_from_email;
	private String s_system_email_from_name;

	private String settings_system_ur_welcome_subj;
	private String settings_system_ur_welcome_text;

	private String settings_system_ar_code_subj;
	private String settings_system_ar_code_text;

	private String settings_system_ar_code_done_subj;
	private String settings_system_ar_code_done_text;

	private String settings_system_ebt_invoice;

	public void refreshSettingsListSystem() {
		this.setS_system_email_from_email(
			bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_EMAIL"));
		this.setS_system_email_from_name(
			bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EMAIL_FROM_NAME")
		);

		//SETTINGS_SYSTEM_BASE_URL
		this.setSettings_system_base_url(
			bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_BASE_URL")
		);

		//SETTINGS_SYSTEM_UR_WELCOME_SUBJ
		this.setSettings_system_ur_welcome_subj(
			bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_UR_WELCOME_SUBJ")
		);

		//SETTINGS_SYSTEM_UR_WELCOME_TEXT
		this.setSettings_system_ur_welcome_text(
			bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_UR_WELCOME_TEXT")
		);

		//SETTINGS_SYSTEM_AR_CODE_SUBJ
		this.setSettings_system_ar_code_subj(
			bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_AR_CODE_SUBJ")
		);

		//SETTINGS_SYSTEM_AR_CODE_TEXT
		this.setSettings_system_ar_code_text(
			bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_AR_CODE_TEXT")
		);

		//SETTINGS_SYSTEM_AR_CODE_DONE_SUBJ
		this.setSettings_system_ar_code_done_subj(
			bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_AR_CODE_DONE_SUBJ")
		);

		//SETTINGS_SYSTEM_AR_CODE_DONE_TEXT
		this.setSettings_system_ar_code_done_text(
			bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_AR_CODE_DONE_TEXT")
		);

		//SETTINGS_SYSTEM_EBT_INVOICE
		this.setSettings_system_ebt_invoice(
			bSystemSettings.getSettingStringByKey("SETTINGS_SYSTEM_EBT_INVOICE")
		);

	}

	public void saveSettingsListSystem () {
		bSystemSettings.updateSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_EMAIL_FROM_EMAIL",
			this.getS_system_email_from_email()
		);
		bSystemSettings.updateSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_EMAIL_FROM_NAME",
			this.getS_system_email_from_name()
		);

		//SETTINGS_SYSTEM_BASE_URL
		bSystemSettings.updateSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_BASE_URL",
			this.getSettings_system_base_url()
		);

		//SETTINGS_SYSTEM_UR_WELCOME_SUBJ
		bSystemSettings.updateSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_UR_WELCOME_SUBJ",
			this.getSettings_system_ur_welcome_subj()
		);

		//SETTINGS_SYSTEM_UR_WELCOME_TEXT
		bSystemSettings.updateSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_UR_WELCOME_TEXT",
			this.getSettings_system_ur_welcome_text()
		);

		//SETTINGS_SYSTEM_AR_CODE_SUBJ
		bSystemSettings.updateSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_AR_CODE_SUBJ",
			this.getSettings_system_ar_code_subj()
		);

		//SETTINGS_SYSTEM_AR_CODE_TEXT
		bSystemSettings.updateSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_AR_CODE_TEXT",
			this.getSettings_system_ar_code_text()
		);

		//SETTINGS_SYSTEM_AR_CODE_DONE_SUBJ
		bSystemSettings.updateSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_AR_CODE_DONE_SUBJ",
			this.getSettings_system_ar_code_done_subj()
		);

		//SETTINGS_SYSTEM_AR_CODE_DONE_TEXT
		bSystemSettings.updateSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_AR_CODE_DONE_TEXT",
			this.getSettings_system_ar_code_done_text()
		);

		//SETTINGS_SYSTEM_EBT_INVOICE
		bSystemSettings.updateSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_EBT_INVOICE",
			this.getSettings_system_ebt_invoice()
		);

		FacesContext.getCurrentInstance().addMessage(
			null,
			new FacesMessage(
				FacesMessage.SEVERITY_INFO,
				"Info",
				"System settings successfully saved."));
	}

	public String getSettings_system_ebt_invoice() {
		return settings_system_ebt_invoice;
	}

	public void setSettings_system_ebt_invoice(String settings_system_ebt_invoice) {
		this.settings_system_ebt_invoice = settings_system_ebt_invoice;
	}

	public String getSettings_system_base_url() {
		return settings_system_base_url;
	}

	public void setSettings_system_base_url(String settings_system_base_url) {
		this.settings_system_base_url = settings_system_base_url;
	}

	public String getSettings_system_ur_welcome_subj() {
		return settings_system_ur_welcome_subj;
	}

	public void setSettings_system_ur_welcome_subj(String settings_system_ur_welcome_subj) {
		this.settings_system_ur_welcome_subj = settings_system_ur_welcome_subj;
	}

	public String getSettings_system_ur_welcome_text() {
		return settings_system_ur_welcome_text;
	}

	public void setSettings_system_ur_welcome_text(String settings_system_ur_welcome_text) {
		this.settings_system_ur_welcome_text = settings_system_ur_welcome_text;
	}

	public String getSettings_system_ar_code_subj() {
		return settings_system_ar_code_subj;
	}

	public void setSettings_system_ar_code_subj(String settings_system_ar_code_subj) {
		this.settings_system_ar_code_subj = settings_system_ar_code_subj;
	}

	public String getSettings_system_ar_code_text() {
		return settings_system_ar_code_text;
	}

	public void setSettings_system_ar_code_text(String settings_system_ar_code_text) {
		this.settings_system_ar_code_text = settings_system_ar_code_text;
	}

	public String getSettings_system_ar_code_done_subj() {
		return settings_system_ar_code_done_subj;
	}

	public void setSettings_system_ar_code_done_subj(String settings_system_ar_code_done_subj) {
		this.settings_system_ar_code_done_subj = settings_system_ar_code_done_subj;
	}

	public String getSettings_system_ar_code_done_text() {
		return settings_system_ar_code_done_text;
	}

	public void setSettings_system_ar_code_done_text(String settings_system_ar_code_done_text) {
		this.settings_system_ar_code_done_text = settings_system_ar_code_done_text;
	}

	public String getS_system_email_from_email() {
		return s_system_email_from_email;
	}

	public void setS_system_email_from_email(String s_system_email_from_email) {
		this.s_system_email_from_email = s_system_email_from_email;
	}

	public String getS_system_email_from_name() {
		return s_system_email_from_name;
	}

	public void setS_system_email_from_name(String s_system_email_from_name) {
		this.s_system_email_from_name = s_system_email_from_name;
	}

	public MBSSettings () {

	}
}
