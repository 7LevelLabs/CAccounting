package ua.its.slot7.caccounting.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.its.slot7.caccounting.model.setting.Setting;
import ua.its.slot7.caccounting.model.setting.SettingDBManagerAvatar;

import java.util.List;

/**
 * CAccounting
 * 29.08.13 : 17:34
 * Alex Velichko
 * alex.itstudio@gmail.com
 * <p/>
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">
 * <img alt="Creative Commons License" style="border-width:0" src="http://i.creativecommons.org/l/by-sa/3.0/88x31.png" />
 * </a><br />
 * This work is licensed under a
 * <a rel="license" href="http://creativecommons.org/licenses/by-sa/3.0/">Creative Commons Attribution-ShareAlike 3.0 Unported License</a>.
 */
@Service("SettingServiceAvatar")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
public class SettingService implements SettingServiceAvatar {

	private final Logger LOGGER = Logger.getLogger(this.getClass());

	@Autowired
	private SettingDBManagerAvatar settingDBManager;

	@Override
	public void createSetting(Setting setting) {
		settingDBManager.createSetting(setting);
	}

	@Override
	public void createSettingIfCantFind(String scope, String key, String value) {
		if (this.getSettingByScopeAndKey(scope, key)==null) {
			this.createSetting(new Setting(scope,key,value));
		}
	}

	@Override
	public void createSettingIfCantFind(Setting setting) {
		this.createSettingIfCantFind(setting.getSettingscope(),
			setting.getSettingkey(),
			setting.getSettingvalue());
	}

	@Override
	public Setting getSettingById(long id) {
		return settingDBManager.getSettingById(id);
	}

	@Override
	public List<Setting> getSettingsByScope(String scope) {
		return settingDBManager.getSettingsByScope(scope);
	}

	@Override
	public Setting getSettingByScopeAndKey(String scope, String key) {
		return settingDBManager.getSettingByScopeAndKey(scope, key);
	}

	@Override
	public void updateSetting(Setting setting) {
		settingDBManager.updateSetting(setting);
	}

	@Override
	public void deleteSetting(Setting setting) {
		settingDBManager.deleteSetting(setting);
	}
}
