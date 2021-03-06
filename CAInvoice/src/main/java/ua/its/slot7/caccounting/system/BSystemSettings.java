package ua.its.slot7.caccounting.system;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.its.slot7.caccounting.model.setting.Setting;
import ua.its.slot7.caccounting.service.SettingServiceAvatar;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * CAccounting
 * 29.08.13 : 17:43
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Component("BSystemSettingsAvatar")
public class BSystemSettings implements BSystemSettingsAvatar {

	private final Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private SettingServiceAvatar settingService;

	@Value("${system.scope}")
	public String SETTINGS_SYSTEM_SCOPE;

	//Without trailing slash!
	@Value("${system.url.base}")
	private String SETTINGS_SYSTEM_BASE_URL;

	@Value("${system.email.from.email}")
	private String SETTINGS_SYSTEM_EMAIL_FROM_EMAIL;

	@Value("${system.email.from.name}")
	private String SETTINGS_SYSTEM_EMAIL_FROM_NAME;

	@Value("${system.ur.welcome.subj}")
	private String SETTINGS_SYSTEM_UR_WELCOME_SUBJ;

	@Value("${system.ur.welcome.text}")
	private String SETTINGS_SYSTEM_UR_WELCOME_TEXT = " ";

	@Value("${system.ar.code.subj}")
	private String SETTINGS_SYSTEM_AR_CODE_SUBJ;

	@Value("${system.ar.code.text}")
	private String SETTINGS_SYSTEM_AR_CODE_TEXT = " ";

	@Value("${system.ar.code.done.subj}")
	private String SETTINGS_SYSTEM_AR_CODE_DONE_SUBJ;

	@Value("${system.ar.code.done.text}")
	private String SETTINGS_SYSTEM_AR_CODE_DONE_TEXT = " ";

	//mail templates
	//invoice

	@Value("${system.ebt.invoice}")
	private String SETTINGS_SYSTEM_EBT_INVOICE = " ";

	private HashMap<String, String> systemSettings;

	@PostConstruct
	@Override
	public void initSystemSettings() {
		systemSettings = new HashMap<String, String>();
		List<Setting> settingsList;
		settingsList = settingService.getSettingsByScope(SETTINGS_SYSTEM_SCOPE);

		if (settingsList != null) {
			Iterator<Setting> iterator = settingsList.iterator();
			Setting s;
			while (iterator.hasNext()) {
				s = iterator.next();
				systemSettings.put(s.getSettingkey(), s.getSettingvalue());
			}
		}
		initSetDefault();
	}

	/**
	 * Set default values of system-wide setting
	 */
	public void initSetDefault() {

		//SETTINGS_SYSTEM_BASE_URL
		this.setSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_BASE_URL",
			SETTINGS_SYSTEM_BASE_URL);

		//SETTINGS_SYSTEM_EMAIL_FROM_EMAIL
		this.setSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_EMAIL_FROM_EMAIL",
			SETTINGS_SYSTEM_EMAIL_FROM_EMAIL);

		//SETTINGS_SYSTEM_EMAIL_FROM_NAME
		this.setSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_EMAIL_FROM_NAME",
			SETTINGS_SYSTEM_EMAIL_FROM_NAME);


		//SETTINGS_SYSTEM_UR_WELCOME_SUBJ
		this.setSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_UR_WELCOME_SUBJ",
			SETTINGS_SYSTEM_UR_WELCOME_SUBJ);

		//SETTINGS_SYSTEM_UR_WELCOME_TEXT
		this.setSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_UR_WELCOME_TEXT",
			SETTINGS_SYSTEM_UR_WELCOME_TEXT);

		//SETTINGS_SYSTEM_AR_CODE_SUBJ
		this.setSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_AR_CODE_SUBJ",
			SETTINGS_SYSTEM_AR_CODE_SUBJ);

		//SETTINGS_SYSTEM_AR_CODE_TEXT
		this.setSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_AR_CODE_TEXT",
			SETTINGS_SYSTEM_AR_CODE_TEXT);

		//SETTINGS_SYSTEM_AR_CODE_DONE_SUBJ
		this.setSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_AR_CODE_DONE_SUBJ",
			SETTINGS_SYSTEM_AR_CODE_DONE_SUBJ);

		//SETTINGS_SYSTEM_AR_CODE_DONE_TEXT
		this.setSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_AR_CODE_DONE_TEXT",
			SETTINGS_SYSTEM_AR_CODE_DONE_TEXT);

		//SETTINGS_SYSTEM_EBT_INVOICE
		this.setSystemSettingByKeyAndValue(
			"SETTINGS_SYSTEM_EBT_INVOICE",
			SETTINGS_SYSTEM_EBT_INVOICE);

	}

	@Override
	public String getSettingStringByKey(String key) {
		return systemSettings.get(key);
	}

	@Override
	public void setSystemSettingByKeyAndValue(String key, String value) {
		Setting setting = settingService.getSettingByScopeAndKey(SETTINGS_SYSTEM_SCOPE, key);
		if (setting == null) {
			setting = new Setting(SETTINGS_SYSTEM_SCOPE, key, value);
			//local
			systemSettings.put(setting.getSettingkey(), setting.getSettingvalue());
			//remote
			settingService.createSetting(setting);
		}
	}

	@Override
	public void updateSystemSettingByKeyAndValue(String key, String value) {
		Setting setting = settingService.getSettingByScopeAndKey(SETTINGS_SYSTEM_SCOPE, key);
		if (setting != null) {
			setting.setSettingvalue(value);
			//local
			systemSettings.remove(setting.getSettingkey());
			systemSettings.put(setting.getSettingkey(), setting.getSettingvalue());
			//remote
			settingService.updateSetting(setting);
		}
	}

	public BSystemSettings() {

	}
}
